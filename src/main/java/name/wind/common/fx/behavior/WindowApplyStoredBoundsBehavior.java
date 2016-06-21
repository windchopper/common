package name.wind.common.fx.behavior;

import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;

import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger("name.wind.common.fx.behavior");

    private final String preferencesEntryName;
    private final Consumer<Window> initialBounder;

    public WindowApplyStoredBoundsBehavior(String preferencesEntryName, Consumer<Window> initialBounder) {
        this.preferencesEntryName = preferencesEntryName;
        this.initialBounder = initialBounder;
    }

    @Override public void apply(Window window) {
        window.sceneProperty().addListener((sceneProperty, previousScene, scene) -> {
            RectanglePreferencesEntry preferencesEntry = new RectanglePreferencesEntry(WindowApplyStoredBoundsBehavior.class, preferencesEntryName);
            Rectangle2D bounds = preferencesEntry.get();

            if (bounds == null) {
                initialBounder.accept(window);
            } else {
                window.setX(bounds.getMinX());
                window.setY(bounds.getMinY());

                Dimension2D size = new Dimension2D(bounds.getWidth(), bounds.getHeight());

                if (scene != null) {
                    window.sizeToScene();

                    size = new Dimension2D(Math.max(window.getWidth(), size.getWidth()),
                        Math.max(window.getHeight(), size.getHeight()));
                }

                window.setWidth(size.getWidth());
                window.setHeight(size.getHeight());
            }

            preferencesEntry.accept(
                new Rectangle2D(
                    window.getX(),
                    window.getY(),
                    window.getWidth(),
                    window.getHeight()));

            window.addEventHandler(WindowEvent.WINDOW_SHOWING, event -> {
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
            });
        });
    }

}
