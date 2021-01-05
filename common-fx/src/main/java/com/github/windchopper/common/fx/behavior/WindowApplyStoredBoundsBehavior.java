package com.github.windchopper.common.fx.behavior;

import com.github.windchopper.common.fx.preferences.PointPreferencesEntryType;
import com.github.windchopper.common.fx.preferences.RectanglePreferencesEntryType;
import com.github.windchopper.common.preferences.*;
import com.github.windchopper.common.preferences.entries.BufferedEntry;
import com.github.windchopper.common.preferences.entries.StandardEntry;
import com.github.windchopper.common.preferences.storages.PlatformStorage;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.time.Duration;
import java.util.prefs.Preferences;

import static javafx.stage.WindowEvent.WINDOW_SHOWING;
import static javafx.stage.WindowEvent.WINDOW_SHOWN;

public class WindowApplyStoredBoundsBehavior implements Behavior<Window> {

    @FunctionalInterface public interface BoundsInitializer {
        void initialize(Window window, boolean resizable);
    }

    private static final PreferencesStorage boundsStorage = new PlatformStorage(
        Preferences.userRoot().node("com/github/windchopper/common/fx/behavior"));

    private static final PreferencesEntryStructuralType<Rectangle2D> rectangleType = new RectanglePreferencesEntryType();
    private static final PreferencesEntryStructuralType<Point2D> pointType = new PointPreferencesEntryType();

    private final String preferencesEntryName;
    private final BoundsInitializer boundsInitializer;

    public WindowApplyStoredBoundsBehavior(String preferencesEntryName, BoundsInitializer boundsInitializer) {
        this.preferencesEntryName = preferencesEntryName;
        this.boundsInitializer = boundsInitializer;
    }

    private void adjustSize(Window window) {
        var scene = window.getScene();

        var sceneWidth = scene.getWidth();
        var sceneHeight = scene.getHeight();

        var sceneRoot = scene.getRoot();

        var preferredWidth = sceneRoot.prefWidth(-1);
        var preferredHeight = sceneRoot.prefHeight(preferredWidth);

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
        PreferencesEntry<Point2D> preferencesEntry = new BufferedEntry<>(Duration.ofMinutes(1),
            new StandardEntry<>(boundsStorage, preferencesEntryName, pointType));

        var locationEntry = preferencesEntry.load();

        if (locationEntry.isNotEmpty()) {
            var location = locationEntry.getValue();

            window.setX(location.getX());
            window.setY(location.getY());
        }

        boundsInitializer.initialize(window, false);

        window.addEventHandler(WINDOW_SHOWING, event -> {
            window.xProperty().addListener((property, oldX, newX) -> preferencesEntry.save(
                new Point2D(
                    newX.doubleValue(),
                    window.getY())));

            window.yProperty().addListener((property, oldY, newY) -> preferencesEntry.save(
                new Point2D(
                    window.getX(),
                    newY.doubleValue())));
        });
    }

    private void applyResizable(Window window) {
        var preferencesEntry = new BufferedEntry<>(Duration.ofMinutes(1),
            new StandardEntry<>(boundsStorage, preferencesEntryName, rectangleType));

        var boundsEntry = preferencesEntry.load();

        if (boundsEntry.isEmpty()) {
            boundsInitializer.initialize(window, true);
        } else {
            var bounds = boundsEntry.getValue();

            window.setX(bounds.getMinX());
            window.setY(bounds.getMinY());
            window.setWidth(bounds.getWidth());
            window.setHeight(bounds.getHeight());
        }

        window.addEventHandler(WINDOW_SHOWN, event -> adjustSize(window));
        window.addEventHandler(WINDOW_SHOWING, event -> {
            window.xProperty().addListener((property, oldX, newX) -> {
                if (window instanceof Stage && ((Stage) window).isMaximized()) {
                    return;
                }

                preferencesEntry.save(
                    new Rectangle2D(
                        newX.doubleValue(),
                        window.getY(),
                        window.getWidth(),
                        window.getHeight()));
            });

            window.yProperty().addListener((property, oldY, newY) -> {
                if (window instanceof Stage && ((Stage) window).isMaximized()) {
                    return;
                }

                preferencesEntry.save(
                    new Rectangle2D(
                        window.getX(),
                        newY.doubleValue(),
                        window.getWidth(),
                        window.getHeight()));
            });

            window.widthProperty().addListener((property, oldWidth, newWidth) -> {
                if (window instanceof Stage && ((Stage) window).isMaximized()) {
                    return;
                }

                preferencesEntry.save(
                    new Rectangle2D(
                        window.getX(),
                        window.getY(),
                        newWidth.doubleValue(),
                        window.getHeight()));
            });

            window.heightProperty().addListener((property, oldHeight, newHeight) -> {
                if (window instanceof Stage && ((Stage) window).isMaximized()) {
                    return;
                }

                preferencesEntry.save(
                    new Rectangle2D(
                        window.getX(),
                        window.getY(),
                        window.getWidth(),
                        newHeight.doubleValue()));
            });
        });
    }

}
