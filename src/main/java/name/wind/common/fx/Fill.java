package name.wind.common.fx;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public enum Fill {

    NONE(false, false),
    HORIZONTAL(true, false),
    VERTICAL(false, true),
    BOTH(true, true);

    private final boolean horizontal;
    private final boolean vertical;

    Fill(boolean horizontal, boolean vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public void apply(Node node) {
        Priority hgrow = horizontal ? Priority.ALWAYS : Priority.NEVER;
        Priority vgrow = vertical ? Priority.ALWAYS : Priority.NEVER;

        GridPane.setFillWidth(node, horizontal);
        GridPane.setHgrow(node, hgrow);
        GridPane.setFillHeight(node, vertical);
        GridPane.setVgrow(node, vgrow);
        HBox.setHgrow(node, hgrow);
        VBox.setVgrow(node, vgrow);
    }

}