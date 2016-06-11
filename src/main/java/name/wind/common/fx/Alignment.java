package name.wind.common.fx;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public enum Alignment implements Consumer<Node> {

    LEFT_BASELINE(HPos.LEFT, VPos.BASELINE),
    CENTER_BASELINE(HPos.CENTER, VPos.BASELINE),
    RIGHT_BASELINE(HPos.RIGHT, VPos.BASELINE),

    LEFT_TOP(HPos.LEFT, VPos.TOP),
    CENTER_TOP(HPos.CENTER, VPos.TOP),
    RIGHT_TOP(HPos.RIGHT, VPos.TOP),

    LEFT_CENTER(HPos.LEFT, VPos.CENTER),
    CENTER_CENTER(HPos.CENTER, VPos.CENTER),
    RIGHT_CENTER(HPos.RIGHT, VPos.CENTER),

    LEFT_BOTTOM(HPos.LEFT, VPos.BOTTOM),
    CENTER_BOTTOM(HPos.CENTER, VPos.BOTTOM),
    RIGHT_BOTTOM(HPos.RIGHT, VPos.BOTTOM);

    private final HPos hpos;
    private final VPos vpos;

    Alignment(HPos hpos, VPos vpos) {
        this.hpos = hpos;
        this.vpos = vpos;
    }

    @Override public void accept(Node node) {
        GridPane.setHalignment(node, hpos);
        GridPane.setValignment(node, vpos);
    }

}
