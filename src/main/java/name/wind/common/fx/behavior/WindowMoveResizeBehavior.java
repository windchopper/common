package name.wind.common.fx.behavior;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class WindowMoveResizeBehavior implements Behavior<Region> {

    private enum DragMode {

        DRAG(Cursor.DEFAULT),
        RESIZE_NORTH_WEST(Cursor.NW_RESIZE),
        RESIZE_NORTH(Cursor.N_RESIZE),
        RESIZE_NORTH_EAST(Cursor.NE_RESIZE),
        RESIZE_EAST(Cursor.E_RESIZE),
        RESIZE_SOUTH_EAST(Cursor.SE_RESIZE),
        RESIZE_SOUTH(Cursor.S_RESIZE),
        RESIZE_SOUTH_WEST(Cursor.SW_RESIZE),
        RESIZE_WEST(Cursor.W_RESIZE);

        private final Cursor cursor;

        DragMode(Cursor cursor) {
            this.cursor = cursor;
        }

        static DragMode detect(Region region, MouseEvent event, double borderSize, boolean changeCursor) {
            DragMode detectedMode;

            if (event.getY() < borderSize) {
                if (event.getX() < borderSize) {
                    detectedMode = DragMode.RESIZE_NORTH_WEST;
                } else if (event.getX() > region.getWidth() - borderSize) {
                    detectedMode = DragMode.RESIZE_NORTH_EAST;
                } else {
                    detectedMode = DragMode.RESIZE_NORTH;
                }
            } else if (event.getY() > region.getHeight() - borderSize) {
                if (event.getX() < borderSize) {
                    detectedMode = DragMode.RESIZE_SOUTH_WEST;
                } else if (event.getX() > region.getWidth() - borderSize) {
                    detectedMode = DragMode.RESIZE_SOUTH_EAST;
                } else {
                    detectedMode = DragMode.RESIZE_SOUTH;
                }
            } else {
                if (event.getX() < borderSize) {
                    detectedMode = DragMode.RESIZE_WEST;
                } else if (event.getX() > region.getWidth() - borderSize) {
                    detectedMode = DragMode.RESIZE_EAST;
                } else {
                    detectedMode = DragMode.DRAG;
                }
            }

            if (changeCursor) {
                region.setCursor(detectedMode.cursor);
            }

            return detectedMode;
        }

    }

    private double dragBeginX;
    private double dragBeginY;
    private double resizeBeginX;
    private double resizeBeginY;
    private double originalWidth;
    private double originalHeight;

    private DragMode dragMode;

    private final double borderSize;
    private final Stage stage;

    public WindowMoveResizeBehavior(double borderSize, Stage stage) {
        this.borderSize = borderSize;
        this.stage = stage;
    }

    @Override public void apply(Region region) {
        region.setOnMousePressed(this::mousePressed);
        region.setOnMouseMoved(this::mouseMoved);
        region.setOnMouseDragged(this::mouseDragged);
    }

    private void mousePressed(MouseEvent event) {
        dragBeginX = event.getX();
        dragBeginY = event.getY();

        resizeBeginX = event.getScreenX();
        resizeBeginY = event.getScreenY();

        Region region = ((Region) event.getSource());

        originalWidth = region.getWidth();
        originalHeight = region.getHeight();
    }

    private void mouseMoved(MouseEvent event) {
        dragMode = DragMode.detect(
            (Region) event.getSource(), event, borderSize, true
        );
    }

    private void mouseDragged(MouseEvent event) {
        double x = stage.getX();
        double y = stage.getY();
        double width = stage.getWidth();
        double height = stage.getHeight();

        switch (dragMode) {
            case DRAG:
                x = event.getScreenX() - dragBeginX;
                y = event.getScreenY() - dragBeginY;
                break;

            case RESIZE_NORTH_WEST:
                x = event.getScreenX();
                y = event.getScreenY();
                width = originalWidth - event.getScreenX() + resizeBeginX;
                height = originalHeight - event.getScreenY() + resizeBeginY;
                break;

            case RESIZE_NORTH:
                y = event.getScreenY();
                height = originalHeight - event.getScreenY() + resizeBeginY;
                break;

            case RESIZE_NORTH_EAST:
                y = event.getScreenY();
                width = originalWidth + event.getScreenX() - resizeBeginX;
                height = originalHeight - event.getScreenY() + resizeBeginY;
                break;

            case RESIZE_EAST:
                width = originalWidth + event.getScreenX() - resizeBeginX;
                break;

            case RESIZE_SOUTH_EAST:
                width = originalWidth + event.getScreenX() - resizeBeginX;
                height = originalHeight + event.getScreenY() - resizeBeginY;
                break;

            case RESIZE_SOUTH:
                height = originalHeight + event.getScreenY() - resizeBeginY;
                break;

            case RESIZE_SOUTH_WEST:
                x = event.getScreenX();
                width = originalWidth - event.getScreenX() + resizeBeginX;
                height = originalHeight + event.getScreenY() - resizeBeginY;
                break;

            case RESIZE_WEST:
                x = event.getScreenX();
                width = originalWidth - event.getScreenX() + resizeBeginX;
                break;
        }

        if (dragMode == DragMode.DRAG) {
            stage.setX(x);
            stage.setY(y);
        } else {
            if (width >= stage.getMinWidth()) {
                stage.setX(x);
                stage.setWidth(width);
            }

            if (height >= stage.getMinHeight()) {
                stage.setY(y);
                stage.setHeight(height);
            }
        }
    }

}
