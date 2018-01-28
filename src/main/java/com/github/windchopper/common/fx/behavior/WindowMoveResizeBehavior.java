package com.github.windchopper.common.fx.behavior;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.stream.Stream;

import static java.util.Arrays.stream;

public class WindowMoveResizeBehavior implements Behavior<Region> {

    private static class AngleRange {

        private final double minAngle;
        private final double maxAngle;

        public AngleRange(double minAngle, double maxAngle) {
            this.minAngle = minAngle;
            this.maxAngle = maxAngle;
        }

        public boolean matches(double angle) {
            return angle >= minAngle && angle <= maxAngle;
        }

    }

    private abstract class DragMode {

        protected double savedSceneX;
        protected double savedSceneY;
        protected double savedScreenX;
        protected double savedScreenY;
        protected double savedWidth;
        protected double savedHeight;

        public abstract void apply(MouseEvent event);

        public void store(MouseEvent event) {
            savedSceneX = event.getSceneX();
            savedSceneY = event.getSceneY();

            savedScreenX = event.getScreenX();
            savedScreenY = event.getScreenY();

            Window window = region.getScene().getWindow();

            savedWidth = window.getWidth();
            savedHeight = window.getHeight();
        }

    }

    private class MoveMode extends DragMode {

        private final Cursor normalCursor;
        private final Cursor dragCursor;

        public MoveMode(Cursor normalCursor, Cursor dragCursor) {
            this.normalCursor = normalCursor;
            this.dragCursor = dragCursor;
        }

        @Override public void apply(MouseEvent event) {
            if (event.getEventType() != MouseEvent.MOUSE_DRAGGED) {
                region.setCursor(normalCursor);
            } else {
                region.setCursor(dragCursor);
                Window window = region.getScene().getWindow();

                window.setX(event.getScreenX() - savedSceneX);
                window.setY(event.getScreenY() - savedSceneY);
            }
        }

    }

    private abstract class ResizeMode extends DragMode {

        private final Cursor cursor;
        private final AngleRange []angleRanges;

        public abstract Bounds calculateBounds(MouseEvent dragEvent, Window window);

        public ResizeMode(Cursor cursor, AngleRange... angleRanges) {
            this.cursor = cursor;
            this.angleRanges = angleRanges;
        }

        public boolean matches(double angle) {
            return stream(angleRanges).anyMatch(range -> range.matches(angle));
        }

        @Override public void apply(MouseEvent event) {
            region.setCursor(cursor);

            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                Window window = region.getScene().getWindow();
                Bounds bounds = calculateBounds(event, window);

                double width = bounds.getWidth();
                double height = bounds.getHeight();

                if (window instanceof Stage) {
                    Stage stage = (Stage) window;

                    if (stage.isResizable()) {
                        width = Math.min(Math.max(width, stage.getMinWidth()), stage.getMaxWidth());
                        height = Math.min(Math.max(height, stage.getMinHeight()), stage.getMaxHeight());
                    } else {
                        width = window.getWidth();
                        height = window.getHeight();
                    }
                }

                window.setX(bounds.getMinX());
                window.setY(bounds.getMinY());
                window.setWidth(width);
                window.setHeight(height);
            }
        }

    }

    private class ResizeEastMode extends ResizeMode {

        public ResizeEastMode(AngleRange... angleRanges) {
            super(Cursor.E_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                window.getX(),
                window.getY(),
                savedWidth + dragEvent.getScreenX() - savedScreenX,
                window.getHeight());
        }

    }

    private class ResizeNorthEastMode extends ResizeMode {

        public ResizeNorthEastMode(AngleRange... angleRanges) {
            super(Cursor.NE_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                window.getX(),
                dragEvent.getScreenY() - savedSceneY,
                savedWidth + dragEvent.getScreenX() - savedScreenX,
                savedHeight - dragEvent.getScreenY() + savedScreenY);
        }

    }

    private class ResizeNorthMode extends ResizeMode {

        public ResizeNorthMode(AngleRange... angleRanges) {
            super(Cursor.N_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                window.getX(),
                dragEvent.getScreenY() - savedSceneY,
                window.getWidth(),
                savedHeight - dragEvent.getScreenY() + savedScreenY);
        }

    }

    private class ResizeNorthWestMode extends ResizeMode {

        public ResizeNorthWestMode(AngleRange... angleRanges) {
            super(Cursor.NW_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                dragEvent.getScreenX() - savedSceneX,
                dragEvent.getScreenY() - savedSceneY,
                savedWidth - dragEvent.getScreenX() + savedScreenX,
                savedHeight - dragEvent.getScreenY() + savedScreenY);
        }

    }

    private class ResizeWestMode extends ResizeMode {

        public ResizeWestMode(AngleRange... angleRanges) {
            super(Cursor.W_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                dragEvent.getScreenX() - savedSceneX,
                window.getY(),
                savedWidth - dragEvent.getScreenX() + savedScreenX,
                window.getHeight());
        }

    }

    private class ResizeSouthWestMode extends ResizeMode {

        public ResizeSouthWestMode(AngleRange... angleRanges) {
            super(Cursor.SW_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                dragEvent.getScreenX() - savedSceneX,
                window.getY(),
                savedWidth - dragEvent.getScreenX() + savedScreenX,
                savedHeight + dragEvent.getScreenY() - savedScreenY);
        }

    }

    private class ResizeSouthMode extends ResizeMode {

        public ResizeSouthMode(AngleRange... angleRanges) {
            super(Cursor.S_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                window.getX(),
                window.getY(),
                window.getWidth(),
                savedHeight + dragEvent.getScreenY() - savedScreenY);
        }

    }

    private class ResizeSouthEastMode extends ResizeMode {

        public ResizeSouthEastMode(AngleRange... angleRanges) {
            super(Cursor.SE_RESIZE, angleRanges);
        }

        @Override public Bounds calculateBounds(MouseEvent dragEvent, Window window) {
            return new BoundingBox(
                window.getX(),
                window.getY(),
                savedWidth + dragEvent.getScreenX() - savedScreenX,
                savedHeight + dragEvent.getScreenY() - savedScreenY);
        }

    }

    private final Region region;
    private final double resizeBorderSize;

    private final MoveMode move;
    private final ResizeMode resizeEast;
    private final ResizeMode resizeNorthEast;
    private final ResizeMode resizeNorth;
    private final ResizeMode resizeNorthWest;
    private final ResizeMode resizeWest;
    private final ResizeMode resizeSouthWest;
    private final ResizeMode resizeSouth;
    private final ResizeMode resizeSouthEast;

    private DragMode dragMode;

    public WindowMoveResizeBehavior(Region region) {
        this(region, 2, 2);
    }

    public WindowMoveResizeBehavior(Region region, double resizeBorderSize, double diagonalResizeSpotAngularSize) {
        this.region = region;
        this.resizeBorderSize = resizeBorderSize;

        dragMode = move = new MoveMode(Cursor.DEFAULT, Cursor.MOVE);
        resizeEast = new ResizeEastMode(new AngleRange(0, 45 - diagonalResizeSpotAngularSize), new AngleRange(315 + diagonalResizeSpotAngularSize, 360));
        resizeNorthEast = new ResizeNorthEastMode(new AngleRange(45 - diagonalResizeSpotAngularSize, 45 + diagonalResizeSpotAngularSize));
        resizeNorth = new ResizeNorthMode(new AngleRange(45 + diagonalResizeSpotAngularSize, 135 - diagonalResizeSpotAngularSize));
        resizeNorthWest = new ResizeNorthWestMode(new AngleRange(135 - diagonalResizeSpotAngularSize, 135 + diagonalResizeSpotAngularSize));
        resizeWest = new ResizeWestMode(new AngleRange(135 + diagonalResizeSpotAngularSize, 225 - diagonalResizeSpotAngularSize));
        resizeSouthWest = new ResizeSouthWestMode(new AngleRange(225 - diagonalResizeSpotAngularSize, 225 + diagonalResizeSpotAngularSize));
        resizeSouth = new ResizeSouthMode(new AngleRange(225 + diagonalResizeSpotAngularSize, 315 - diagonalResizeSpotAngularSize));
        resizeSouthEast = new ResizeSouthEastMode(new AngleRange(315 - diagonalResizeSpotAngularSize, 315 + diagonalResizeSpotAngularSize));
    }

    private double calculateAngle(double x, double y) {
        double angle = Math.toDegrees(
            Math.atan2(y, x));

        if (angle < 0) {
            angle = 360 + angle;
        }

        if (angle > 360) {
            angle = 360;
        }

        return angle;
    }

    private DragMode detect(MouseEvent event) {
        Bounds bounds = region.getLayoutBounds();

        Bounds outerBounds = new BoundingBox(bounds.getMinX() - resizeBorderSize, bounds.getMinY() - resizeBorderSize,
            bounds.getWidth() + resizeBorderSize * 2, bounds.getHeight() + resizeBorderSize * 2);
        Bounds innerBounds = new BoundingBox(bounds.getMinX() + resizeBorderSize, bounds.getMinY() + resizeBorderSize,
            bounds.getWidth() - resizeBorderSize * 2, bounds.getHeight() - resizeBorderSize * 2);

        Point2D center = new Point2D(innerBounds.getWidth() / 2,
            innerBounds.getHeight() / 2);
        Point2D ptr = new Point2D(
            event.getX(), event.getY());

        if (outerBounds.contains(ptr) && !innerBounds.contains(ptr)) {
            double angle = calculateAngle(ptr.getX() - center.getX(),
                (ptr.getY() - center.getY()) * (outerBounds.getWidth() / outerBounds.getHeight()) * -1);
            return Stream.of(resizeEast, resizeNorthEast, resizeNorth, resizeNorthWest, resizeWest, resizeSouthWest, resizeSouth, resizeSouthEast)
                .filter(mode -> mode.matches(angle))
                .findFirst().orElseThrow(AssertionError::new);
        }

        return move;
    }

    private void mouseAny(MouseEvent event) {
        dragMode.apply(event);
    }

    private void mousePress(MouseEvent event) {
        dragMode.store(event);
        mouseAny(event);
    }

    private void mouseRelease(MouseEvent event) {
        mouseAny(event);
    }

    private void mouseMove(MouseEvent event) {
        dragMode = detect(event);
        mouseAny(event);
    }

    private void mouseDrag(MouseEvent event) {
        mouseAny(event);
    }

    /*
     *
     */

    Node lastTarget;

    @Override public void apply(Region region) {
        region.setOnMousePressed(this::mousePress);
        region.setOnMouseReleased(this::mouseRelease);
        region.setOnMouseMoved(this::mouseMove);
        region.setOnMouseDragged(this::mouseDrag);

//        EventHandler<MouseEvent> restoreCursorHandler = event -> {
//            Node target = (Node) event.getTarget();
//            if (target != lastTarget) {
//                if (target != region) {
//                    Cursor cursor = ((Node) event.getTarget()).getCursor();
//
//                    if (cursor == null) {
//                        target.setCursor(Cursor.DEFAULT);
//                        System.out.println("correction");
//                    }
//                }
//
//                lastTarget = target;
//            }
//        };

//        region.sceneProperty().addListener((property, oldScene, newScene) -> {
//            if (oldScene != null) {
//                oldScene.removeEventFilter(MouseEvent.MOUSE_MOVED, restoreCursorHandler);
//            }
//
//            if (newScene != null) {
//                newScene.addEventFilter(MouseEvent.MOUSE_MOVED, restoreCursorHandler);
//            }
//        });
    }

}
