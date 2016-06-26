package name.wind.common.fx.behavior;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Window;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;

import java.util.function.Consumer;

import static javafx.stage.WindowEvent.WINDOW_SHOWING;
import static javafx.stage.WindowEvent.WINDOW_SHOWN;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    private final String preferencesEntryName;
    private final Consumer<Window> boundsInitializer;

    public WindowApplyStoredBoundsBehavior(String preferencesEntryName, Consumer<Window> boundsInitializer) {
        this.preferencesEntryName = preferencesEntryName;
        this.boundsInitializer = boundsInitializer;
    }

    private void correctBounds(Window window) {
        Scene scene = window.getScene();

        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        double deltaWidth = window.getWidth() - sceneWidth;
        double deltaHeight = window.getHeight() - sceneHeight;

        Parent sceneRoot = scene.getRoot();

        double preferredWidth = sceneRoot.prefWidth(sceneHeight);
        double preferredHeight = sceneRoot.prefHeight(preferredWidth);

        if (preferredWidth > sceneWidth) {
            window.setWidth(preferredWidth + deltaWidth);
        }

        if (preferredHeight > sceneHeight) {
            window.setHeight(preferredHeight + deltaHeight);
        }
    }

    @Override public void apply(Window window) {
        RectanglePreferencesEntry preferencesEntry = new RectanglePreferencesEntry(WindowApplyStoredBoundsBehavior.class, preferencesEntryName);
        Rectangle2D bounds = preferencesEntry.get();

        if (bounds == null) {
            boundsInitializer.accept(window);
        } else {
            window.setX(bounds.getMinX());
            window.setY(bounds.getMinY());
            window.setWidth(bounds.getWidth());
            window.setHeight(bounds.getHeight());
        }

        window.addEventHandler(WINDOW_SHOWN, event -> correctBounds(window));
        window.addEventHandler(WINDOW_SHOWING, event -> {
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
    }

}
