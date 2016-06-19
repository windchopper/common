package name.wind.common.fx.behavior;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import name.wind.common.fx.CommonProperties;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger(WindowApplyStoredBoundsBehavior.class.getName());

    @Override public void apply(Window window) {
        window.sceneProperty().addListener((sceneProperty, oldScene, newScene) -> Platform.runLater(() -> {
            String identifier = (String) window.getProperties().get(CommonProperties.PROPERTY__IDENTIFIER);
            Dimension2D preferredSize = (Dimension2D) window.getProperties().get(CommonProperties.PROPERTY__PREFERRED_SIZE);
            Rectangle2D bounds = null;

            if (identifier == null) {
                logger.log(Level.WARNING, bundle.getString("common.fx.behavior.WindowPreferencedBounds.missingIdentifier"));
            } else {
                RectanglePreferencesEntry preferencesEntry = new RectanglePreferencesEntry(WindowApplyStoredBoundsBehavior.class, identifier + "Bounds");
                bounds = preferencesEntry.get();

                window.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
                    @Override public void handle(WindowEvent event) {
                        window.removeEventHandler(WindowEvent.WINDOW_SHOWN, this);

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

            if (bounds != null) {
                window.setX(bounds.getMinX());
                window.setY(bounds.getMinY());
                window.setWidth(bounds.getWidth());
                window.setHeight(bounds.getHeight());
            } else if (preferredSize != null) {
                window.setWidth(preferredSize.getWidth());
                window.setHeight(preferredSize.getHeight());
            }

            if (bounds == null && preferredSize == null) {
                window.sizeToScene();
            }

            if (bounds == null) {
                window.centerOnScreen();
            }
        }));
    }

}
