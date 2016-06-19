package name.wind.common.fx.behavior;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Window;
import name.wind.common.fx.CommonProperties;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;
import name.wind.common.preferences.PreferencesEntry;
import name.wind.common.util.Optional;
import name.wind.common.util.Value;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger(
        WindowApplyStoredBoundsBehavior.class.getName());

    @Override public void apply(Window window) {
        window.sceneProperty().addListener(
            (sceneProperty, oldScene, newScene) -> Platform.runLater(
                () -> Optional.of(window.getProperties().get(CommonProperties.PROPERTY__IDENTIFIER))
                    .ifNotPresent(() -> logger.log(Level.WARNING, bundle.getString("common.fx.behavior.WindowPreferencedBounds.missingIdentifier")))
                    .map(identifier -> new RectanglePreferencesEntry(WindowApplyStoredBoundsBehavior.class, identifier + "Bounds"))
                    .ifPresent(preferencesEntry -> Value.of(window)
                        .with(target -> target.xProperty().addListener(
                            (xProperty, oldX, newX) -> preferencesEntry.accept(
                                new Rectangle2D(
                                    newX.doubleValue(),
                                    window.getY(),
                                    window.getWidth(),
                                    window.getHeight()))))
                        .with(target -> target.yProperty().addListener(
                            (yProperty, oldY, newY) -> preferencesEntry.accept(
                                new Rectangle2D(
                                    window.getX(),
                                    newY.doubleValue(),
                                    window.getWidth(),
                                    window.getHeight()))))
                        .with(target -> target.widthProperty().addListener(
                            (widthProperty, oldWidth, newWidth) -> preferencesEntry.accept(
                                new Rectangle2D(
                                    window.getX(),
                                    window.getY(),
                                    newWidth.doubleValue(),
                                    window.getHeight()))))
                        .with(target -> target.heightProperty().addListener(
                            (heightProperty, oldHeight, newHeight) -> preferencesEntry.accept(
                                new Rectangle2D(
                                    window.getX(),
                                    window.getY(),
                                    window.getWidth(),
                                    newHeight.doubleValue())))))
                    .map(PreferencesEntry::get)
                    .ifNotPresent(() -> Value.of(window)
                        .with(Window::sizeToScene)
                        .with(Window::centerOnScreen))
                    .ifPresent(bounds -> Value.of(window)
                        .with(target -> target.setX(bounds.getMinX()))
                        .with(target -> target.setY(bounds.getMinY()))
                        .with(target -> target.setWidth(bounds.getWidth()))
                        .with(target -> target.setHeight(bounds.getHeight())))));
    }

}
