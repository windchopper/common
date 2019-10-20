package com.github.windchopper.common.fx.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import com.github.windchopper.common.fx.Alignment;
import com.github.windchopper.common.fx.Fill;
import com.github.windchopper.common.util.Pipeliner;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DialogSkeleton {

    public enum PartType {

        HEADING,
        CONTENT,
        BUTTONS,
        FOOTING

    }

    /*
     *
     */

    private final Map<PartType, Node> parts = new HashMap<>();

    /*
     *
     */

    public void add(PartType type, Node part) {
        parts.put(type, part);
    }

    protected <F extends DialogFrame> Pane createRootPane(F frame, Collection<DialogAction> actions) {
        class MutableInt {
            int value;
        }

        var rowIndex = new MutableInt();

        return Pipeliner.of(GridPane::new)
            .add(root -> root::getChildren, Stream.of(
                Optional.ofNullable(parts.get(PartType.HEADING))
                    .map(node -> {
                        GridPane.setConstraints(node, 0, rowIndex.value++, 2, 1);
                        Alignment.CENTER_CENTER.apply(node);
                        Fill.HORIZONTAL.apply(node);
                        return node;
                    })
                    .orElse(null),
                Optional.ofNullable(parts.get(PartType.CONTENT))
                    .map(node -> {
                        GridPane.setConstraints(node, 0, rowIndex.value++, 2, 1);
                        Alignment.CENTER_CENTER.apply(node);
                        Fill.BOTH.apply(node);
                        return node;
                    })
                    .orElse(null),
                Optional.of(Optional.ofNullable(parts.get(PartType.BUTTONS))
                    .orElseGet(Pipeliner.of(HBox::new)
                        .set(box -> box::setAlignment, Pos.BASELINE_RIGHT)
                        .set(box -> box::setSpacing, 10.0)
                        .set(box -> box::setPadding, new Insets(20.0))
                        .add(box -> box::getChildren, actions.stream()
                            .map(action -> Pipeliner.of(Button::new)
                                .accept(action::bind)
                                .set(button -> button::setPrefWidth, 80.0)
                                .set(button -> button::setDefaultButton, DialogAction.ThreatThreshold.ACCEPT.belongs(action))
                                .set(button -> button::setCancelButton, DialogAction.ThreatThreshold.REJECT.belongs(action))
                                .get())
                            .collect(
                                toList()))
                        .accept(box -> {
                            GridPane.setConstraints(box, 1, rowIndex.value++);
                            Alignment.CENTER_CENTER.apply(box);
                            Fill.HORIZONTAL.apply(box);
                        })))
                    .map(node -> {
                        GridPane.setConstraints(node, 1, rowIndex.value++);
                        Alignment.CENTER_CENTER.apply(node);
                        Fill.HORIZONTAL.apply(node);
                        return node;
                    })
                    .orElse(null),
                Optional.ofNullable(parts.get(PartType.FOOTING))
                    .map(node -> {
                        GridPane.setConstraints(node, 0, rowIndex.value++);
                        Alignment.CENTER_CENTER.apply(node);
                        Fill.NONE.apply(node);
                        return node;
                    })
                    .orElse(null))
                .filter(Objects::nonNull)
                .collect(
                    toList()))
            .get();
    }

}
