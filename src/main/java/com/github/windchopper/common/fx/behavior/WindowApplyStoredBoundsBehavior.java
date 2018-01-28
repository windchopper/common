package com.github.windchopper.common.fx.behavior;

import com.github.windchopper.common.fx.preferences.PointPreferencesEntryType;
import com.github.windchopper.common.fx.preferences.RectanglePreferencesEntryType;
import com.github.windchopper.common.preferences.PlatformPreferencesStorage;
import com.github.windchopper.common.preferences.PreferencesEntryType;
import com.github.windchopper.common.preferences.PreferencesStorage;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.github.windchopper.common.preferences.PreferencesEntry;

import java.time.Duration;
import java.util.prefs.Preferences;

import static javafx.stage.WindowEvent.WINDOW_SHOWING;
import static javafx.stage.WindowEvent.WINDOW_SHOWN;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    @FunctionalInterface public interface BoundsInitializer {
        void initialize(Window window, boolean resizable);
    }

    private static final PreferencesStorage boundsStorage = new PlatformPreferencesStorage(
        Preferences.userRoot().node("name/wind/common/fx/behavior"));

    private static final PreferencesEntryType<Rectangle2D> rectangleType = new RectanglePreferencesEntryType();
    private static final PreferencesEntryType<Point2D> pointType = new PointPreferencesEntryType();

    private final String preferencesEntryName;
    private final BoundsInitializer boundsInitializer;

    public WindowApplyStoredBoundsBehavior(String preferencesEntryName, BoundsInitializer boundsInitializer) {
        this.preferencesEntryName = preferencesEntryName;
        this.boundsInitializer = boundsInitializer;
    }

    private void adjustSize(Window window) {
        Scene scene = window.getScene();

        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();

        Parent sceneRoot = scene.getRoot();

        double preferredWidth = sceneRoot.prefWidth(-1);
        double preferredHeight = sceneRoot.prefHeight(preferredWidth);

        if (preferredWidth > sceneWidth) {
            window.setWidth(preferredWidth + window.getWidth() - sceneWidth);
        }

        if (preferredHeight > sceneHeight) {
            window.setHeight(preferredHeight + window.getHeight() - sceneHeight);
        }
    }

    @Override public void apply(Window window) {
        if (window instanceof Stage && ((Stage) window).isResizable()) {
            applyResizable(window);
        } else {
            applyNonResizable(window);
        }
    }

    private void applyNonResizable(Window window) {
        PreferencesEntry<Point2D> preferencesEntry = new PreferencesEntry<>(
            boundsStorage,
            preferencesEntryName,
            pointType,
            Duration.ofMinutes(1));

        Point2D location = preferencesEntry.get();

        if (location != null) {
            window.setX(location.getX());
            window.setY(location.getY());
        }

        boundsInitializer.initialize(window, false);

        window.addEventHandler(WINDOW_SHOWING, event -> {
            window.xProperty().addListener((property, oldX, newX) -> preferencesEntry.accept(
                new Point2D(
                    newX.doubleValue(),
                    window.getY())));

            window.yProperty().addListener((property, oldY, newY) -> preferencesEntry.accept(
                new Point2D(
                    window.getX(),
                    newY.doubleValue())));
        });
    }

    private void applyResizable(Window window) {
        PreferencesEntry<Rectangle2D> preferencesEntry = new PreferencesEntry<>(
            boundsStorage,
            preferencesEntryName,
            rectangleType,
            Duration.ofMinutes(1));

        Rectangle2D bounds = preferencesEntry.get();

        if (bounds == null) {
            boundsInitializer.initialize(window, true);
        } else {
            window.setX(bounds.getMinX());
            window.setY(bounds.getMinY());
            window.setWidth(bounds.getWidth());
            window.setHeight(bounds.getHeight());
        }

        window.addEventHandler(WINDOW_SHOWN, event -> adjustSize(window));
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
