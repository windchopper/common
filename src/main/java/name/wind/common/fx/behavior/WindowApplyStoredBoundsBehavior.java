package name.wind.common.fx.behavior;

import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import name.wind.common.fx.ExtraProperties;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger("name.wind.common.fx.behavior");

    private final Consumer<Window> noStoredBoundsHandler;

    public WindowApplyStoredBoundsBehavior(Consumer<Window> noStoredBoundsHandler) {
        this.noStoredBoundsHandler = noStoredBoundsHandler;
    }

    @Override public void apply(Window window) {
        window.sceneProperty().addListener((sceneProperty, oldScene, newScene) -> {
            String identifier = (String) window.getProperties().get(ExtraProperties.PROPERTY__WINDOW_IDENTIFIER);
            Rectangle2D bounds = null;

            if (identifier == null) {
                logger.log(Level.WARNING, bundle.getString("common.fx.behavior.WindowPreferencedBounds.missingIdentifier"));
            } else {
                RectanglePreferencesEntry preferencesEntry = new RectanglePreferencesEntry(WindowApplyStoredBoundsBehavior.class, identifier + "Bounds");
                bounds = preferencesEntry.get();

                window.addEventHandler(WindowEvent.WINDOW_SHOWING, new EventHandler<WindowEvent>() {
                    @Override public void handle(WindowEvent event) {
                        window.removeEventHandler(WindowEvent.WINDOW_SHOWING, this);

                        window.xProperty().addListener((property, oldX, newX) -> preferencesEntry.accept(
                            new Rectangle2D(
                                newX.doubleValue(),
                                window.getY(),
                                window.getWidth(),
                                window.getHeight())));

                        window.yProperty().addListener((property, oldY, newY) -> preferencesEntry.accept(
                            new Rectangle2D(
                                window.getX(),
                                newY.doubleValue(),
                                window.getWidth(),
                                window.getHeight())));

                        window.widthProperty().addListener((property, oldWidth, newWidth) -> preferencesEntry.accept(
                            new Rectangle2D(
                                window.getX(),
                                window.getY(),
                                newWidth.doubleValue(),
                                window.getHeight())));

                        window.heightProperty().addListener((property, oldHeight, newHeight) -> preferencesEntry.accept(
                            new Rectangle2D(
                                window.getX(),
                                window.getY(),
                                window.getWidth(),
                                newHeight.doubleValue())));
                    }
                });
            }

            if (bounds == null) {
                noStoredBoundsHandler.accept(window);
            } else {
                Dimension2D size;

                if (newScene != null) {
                    window.sizeToScene();
                    size = new Dimension2D(window.getWidth(), window.getHeight());
                } else {
                    size = new Dimension2D(bounds.getWidth(), bounds.getHeight());
                }

                window.setX(bounds.getMinX());
                window.setY(bounds.getMinY());
                window.setWidth(size.getWidth());
                window.setHeight(size.getHeight());
            }
        });
    }

}
