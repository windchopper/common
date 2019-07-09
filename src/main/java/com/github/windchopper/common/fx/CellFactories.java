package com.github.windchopper.common.fx;

import javafx.scene.control.*;
import javafx.util.Callback;

public class CellFactories {

    @FunctionalInterface public interface CellUpdater<ItemType, CellType extends Cell<ItemType>> {
        void update(CellType cell, ItemType item, boolean empty);
    }

    @FunctionalInterface public interface ColumnCellUpdater<ColumnType, ItemType, CellType extends IndexedCell<ItemType>> {
        void update(CellType cell, ColumnType column, ItemType item, boolean empty);
    }

    public static <T> Callback<ListView<T>, ListCell<T>> listCellFactory(CellUpdater<T, ListCell<T>> cellUpdater) {
        return view -> new ListCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                cellUpdater.update(this, item, empty);
            }
        };
    }

    public static <T> Callback<TreeView<T>, TreeCell<T>> treeCellFactory(CellUpdater<T, TreeCell<T>> cellUpdater) {
        return view -> new TreeCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                cellUpdater.update(this, item, empty);
            }
        };
    }

    public static <C, T> Callback<TableColumn<C, T>, TableCell<C, T>> tableColumnCellFactory(ColumnCellUpdater<C, T, TableCell<C, T>> cellUpdater) {
        return column -> new TableCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                cellUpdater.update(this, getTableRow().getItem(), item, empty);
            }
        };
    }

    public static <C, T> Callback<TreeTableColumn<C, T>, TreeTableCell<C, T>> treeTableColumnCellFactory(ColumnCellUpdater<C, T, TreeTableCell<C, T>> cellUpdater) {
        return column -> new TreeTableCell<>() {
            @Override protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                cellUpdater.update(this, getTreeTableRow().getItem(), item, empty);
            }
        };
    }

}
