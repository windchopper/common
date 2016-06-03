package ru.wind.common.fx.builder;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import ru.wind.common.fx.Alignment;
import ru.wind.common.fx.Fill;
import ru.wind.common.fx.Inset;
import ru.wind.common.fx.MutableInsets;

import java.util.List;
import java.util.function.Supplier;

public class GridPaneBuilder extends AbstractPaneBuilder<GridPane, GridPaneBuilder> {

    public GridPaneBuilder(Supplier<GridPane> targetSupplier) {
        super(targetSupplier);
    }

    private <T> T constraintsAt(Supplier<? extends List<T>> listSupplier, Supplier<T> itemSupplier, int index) {
        List<T> items = listSupplier.get();

        while (items.size() < index + 1) {
            items.add(
                itemSupplier.get()
            );
        }

        return items.get(index);
    }

    public GridPaneBuilder rowConstraints(int rowIndex, double preferredHeight) {
        RowConstraints constraints = constraintsAt(target::getRowConstraints, RowConstraints::new, rowIndex);

        constraints.setPrefHeight(preferredHeight);

        return this;
    }

    public GridPaneBuilder columnConstraints(int columnIndex, double preferredWidth) {
        ColumnConstraints constraints = constraintsAt(target::getColumnConstraints, ColumnConstraints::new, columnIndex);

        constraints.setPrefWidth(preferredWidth);

        return this;
    }

    public GridPaneBuilder add(Node node, int columnIndex, int rowIndex, int columnSpan, int rowSpan, HPos horizontalAlignment, VPos verticalAlignment,
        Priority horizontalGrow, Priority verticalGrow, boolean fillWidth, boolean fillHeight, Insets margin) {

        target.add(node, columnIndex, rowIndex);

        GridPane.setColumnSpan(node, columnSpan);
        GridPane.setRowSpan(node, rowSpan);
        GridPane.setHalignment(node, horizontalAlignment);
        GridPane.setValignment(node, verticalAlignment);
        GridPane.setHgrow(node, horizontalGrow);
        GridPane.setVgrow(node, verticalGrow);
        GridPane.setFillWidth(node, fillWidth);
        GridPane.setFillHeight(node, fillHeight);
        GridPane.setMargin(node, margin);

        return this;
    }

    public GridPaneBuilder add(Node node, int columnIndex, int rowIndex, int columnSpan, int rowSpan, Alignment alignment, Fill fill, Inset... insets) {
        target.add(node, columnIndex, rowIndex);

        GridPane.setColumnSpan(node, columnSpan);
        GridPane.setRowSpan(node, rowSpan);

        alignment.accept(node);
        fill.accept(node);

        GridPane.setMargin(
            node, new MutableInsets(insets).toFxInsets()
        );

        return this;
    }

    public GridPaneBuilder add(Node node, int columnIndex, int rowIndex, Alignment alignment, Fill fill, Inset... insets) {
        return add(
            node,
            columnIndex, rowIndex,
            1, 1,
            alignment,
            fill,
            insets
        );
    }

    public GridPaneBuilder alignment(Pos alignment) {
        target.setAlignment(alignment);
        return this;
    }

}
