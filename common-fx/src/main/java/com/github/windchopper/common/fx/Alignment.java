package com.github.windchopper.common.fx;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public enum Alignment {

    LEFT_BASELINE(Pos.BASELINE_LEFT),
    CENTER_BASELINE(Pos.BASELINE_CENTER),
    RIGHT_BASELINE(Pos.BASELINE_RIGHT),

    LEFT_TOP(Pos.TOP_LEFT),
    CENTER_TOP(Pos.TOP_CENTER),
    RIGHT_TOP(Pos.TOP_RIGHT),

    LEFT_CENTER(Pos.CENTER_LEFT),
    CENTER_CENTER(Pos.CENTER),
    RIGHT_CENTER(Pos.CENTER_RIGHT),

    LEFT_BOTTOM(Pos.BOTTOM_LEFT),
    CENTER_BOTTOM(Pos.BOTTOM_CENTER),
    RIGHT_BOTTOM(Pos.BOTTOM_RIGHT);

    private final Pos pos;

    Alignment(Pos pos) {
        this.pos = pos;
    }

    public void apply(Node node) {
        GridPane.setHalignment(node, pos.getHpos());
        GridPane.setValignment(node, pos.getVpos());

        BorderPane.setAlignment(node, pos);
        StackPane.setAlignment(node, pos);
        TilePane.setAlignment(node, pos);
    }

}
