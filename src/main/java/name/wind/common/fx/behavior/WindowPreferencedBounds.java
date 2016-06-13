package name.wind.common.fx.behavior;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Window;
import name.wind.common.fx.preferences.RectanglePreferencesEntry;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowPreferencedBounds implements Behavior<Window> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger(
        WindowPreferencedBounds.class.getName());

    private double validNumber(double number) {
        return Double.isInfinite(number) || Double.isNaN(number) ? 0 : number;
    }

    private void saveRectangleValue(Window window, RectanglePreferencesEntry preferencesEntry, double x, double y, double width, double height) {
        Rectangle2D rectangle = preferencesEntry.get();

        if (rectangle == null) {
            rectangle = new Rectangle2D(
                window.getX(),
                window.getY(),
                window.getWidth(),
                window.getHeight());
        }

        preferencesEntry.accept(
            new Rectangle2D(
                Double.isNaN(x) ? rectangle.getMinX() : x,
                Double.isNaN(y) ? rectangle.getMinY() : y,
                Double.isNaN(width) ? rectangle.getWidth() : width,
                Double.isNaN(height) ? rectangle.getHeight() : height));
    }

    private void applyPreferredOrPreferencedBounds(Window window) {
        String identifier = (String) window.getProperties().get(CommonProperties.PROPERTY__IDENTIFIER);
        Dimension2D preferredSize = (Dimension2D) window.getProperties().get(CommonProperties.PROPERTY__WINDOW__PREFERRED_SIZE);

        if (window.getScene() != null) {
            window.sizeToScene();
            if (preferredSize != null) {
                preferredSize = new Dimension2D(
                    Math.max(preferredSize.getWidth(), validNumber(window.getWidth())),
                    Math.max(preferredSize.getHeight(), validNumber(window.getHeight())));
            } else {
                preferredSize = new Dimension2D(
                    window.getWidth(),
                    window.getHeight());
            }
        }

        Rectangle2D bounds = new Rectangle2D(
            window.getX(),
            window.getY(),
            preferredSize.getWidth(),
            preferredSize.getHeight());

        if (identifier != null) {
            RectanglePreferencesEntry boundsPreferencesEntry = new RectanglePreferencesEntry(WindowPreferencedBounds.class, identifier + "Bounds");
            bounds = Optional.ofNullable(boundsPreferencesEntry.get()).orElse(bounds);

            window.xProperty().addListener(
                (xProperty, oldX, newX) -> saveRectangleValue(
                    window,
                    boundsPreferencesEntry,
                    newX.doubleValue(),
                    Double.NaN,
                    Double.NaN,
                    Double.NaN));

            window.yProperty().addListener(
                (yProperty, oldY, newY) -> saveRectangleValue(
                    window,
                    boundsPreferencesEntry,
                    Double.NaN,
                    newY.doubleValue(),
                    Double.NaN,
                    Double.NaN));

            window.widthProperty().addListener(
                (widthProperty, oldWidth, newWidth) -> saveRectangleValue(
                    window,
                    boundsPreferencesEntry,
                    Double.NaN,
                    Double.NaN,
                    newWidth.doubleValue(),
                    Double.NaN));

            window.heightProperty().addListener(
                (heightProperty, oldHeight, newHeight) -> saveRectangleValue(
                    window,
                    boundsPreferencesEntry,
                    Double.NaN,
                    Double.NaN,
                    Double.NaN,
                    newHeight.doubleValue()));
        } else {
            logger.log(Level.WARNING, bundle.getString("common.fx.behavior.WindowPreferencedBounds.missingIdentifier"));
        }

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
    }

    @Override public void apply(Window window) {
        window.sceneProperty().addListener(
            (sceneProperty, oldScene, newScene) -> Platform.runLater(
                () -> applyPreferredOrPreferencedBounds(window)));
    }

}
