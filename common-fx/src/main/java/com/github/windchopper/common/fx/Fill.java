package com.github.windchopper.common.fx;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static java.util.Objects.requireNonNull;

public enum Fill {

    NONE(false, false),
    HORIZONTAL(true, false),
    VERTICAL(false, true),
    BOTH(true, true);

    private final boolean horizontal;
    private final Priority horizontalPriority;
    private final boolean vertical;
    private final Priority verticalPriority;

    Fill(boolean horizontal, boolean vertical) {
        horizontalPriority = (this.horizontal = horizontal) ? Priority.ALWAYS : Priority.NEVER;
        verticalPriority = (this.vertical = vertical) ? Priority.ALWAYS : Priority.NEVER;
    }

    public void apply(Node node) {
        requireNonNull(node);

        GridPane.setFillWidth(node, horizontal);
        GridPane.setHgrow(node, horizontalPriority);
        HBox.setHgrow(node, horizontalPriority);

        GridPane.setFillHeight(node, vertical);
        GridPane.setVgrow(node, verticalPriority);
        VBox.setVgrow(node, verticalPriority);
    }

}
