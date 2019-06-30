package com.github.windchopper.common.fx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.List;

public class CellFactories {

    @FunctionalInterface public interface ListCellUpdater<I, C> {
        void update(C cell, I item);
    }

    public static <I> Callback<ListView<I>, ListCell<I>> listCellFactory(
        List<ListCellUpdater<I, ListCell<I>>> updaters) {
        return listView -> new ListCell<I>() {
            @Override protected void updateItem(I item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    updaters.forEach(
                        updater -> updater.update(this, item));
                }
            }
        };
    }

    @FunctionalInterface public interface ColumnContainerCellUpdater<I, T, C> {
        void updater(C cell, I item, T value);
    }

    public static <I, T> Callback<TableColumn<I, T>, TableCell<I, T>> tableColumnCellFactory(
        List<ColumnContainerCellUpdater<I, T, TableCell<I, T>>> updaters) {
        return column -> new TableCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    updaters.forEach(
                        updater -> updater.updater(this, (I) getTableRow().getItem(), item));
                }
            }
        };
    }

    public static <I, T> Callback<TreeTableColumn<I, T>, TreeTableCell<I, T>> treeTableColumnCellFactory(
        List<ColumnContainerCellUpdater<I, T, TreeTableCell<I, T>>> squeezers) {
        return column -> new TreeTableCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    squeezers.forEach(
                        squeezer -> squeezer.updater(this, getTreeTableRow().getItem(), item));
                }
            }
        };
    }

    @FunctionalInterface public interface ColumnContainerCellValueSupplier<T, F> {
        ObservableValue<T> observable(F features);
    }

    public static <I, T> Callback<TableColumn.CellDataFeatures<I, T>, ObservableValue<T>> tableColumnCellValueFactory(
        ColumnContainerCellValueSupplier<T, TableColumn.CellDataFeatures<I, T>> observableSupplier) {
        return observableSupplier::observable;
    }

    public static <I, T> Callback<TreeTableColumn.CellDataFeatures<I, T>, ObservableValue<T>> treeTableColumnCellValueFactory(
        ColumnContainerCellValueSupplier<T, TreeTableColumn.CellDataFeatures<I, T>> observableSupplier) {
        return observableSupplier::observable;
    }

}
