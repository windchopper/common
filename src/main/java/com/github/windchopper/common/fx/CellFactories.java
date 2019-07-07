package com.github.windchopper.common.fx;

import javafx.scene.control.*;
import javafx.util.Callback;

import static java.util.Arrays.stream;

public class CellFactories {

    @FunctionalInterface public interface CellUpdater<ItemType, CellType extends Cell<ItemType>> {

        void update(CellType cell, ItemType item, boolean empty);

    }

    public static class DefaultListCell<ItemType> extends ListCell<ItemType> {

        private final CellUpdater<ItemType, ListCell<ItemType>>[] updaters;

        @SafeVarargs public DefaultListCell(CellUpdater<ItemType, ListCell<ItemType>>... updaters) {
            this.updaters = updaters;
        }

        @Override protected void updateItem(ItemType item, boolean empty) {
            super.updateItem(item, empty);
            stream(updaters).forEach(updater -> updater.update(this, item, empty));
        }

    }

    public static class DefaultTreeCell<ItemType> extends TreeCell<ItemType> {

        private final CellUpdater<ItemType, TreeCell<ItemType>>[] updaters;

        @SafeVarargs public DefaultTreeCell(CellUpdater<ItemType, TreeCell<ItemType>>... updaters) {
            this.updaters = updaters;
        }

        @Override protected void updateItem(ItemType item, boolean empty) {
            super.updateItem(item, empty);
            stream(updaters).forEach(updater -> updater.update(this, item, empty));
        }

    }

    @FunctionalInterface public interface ColumnContainerCellUpdater<ColumnType, ItemType, CellType extends IndexedCell<ItemType>> {

        void update(CellType cell, ColumnType column, ItemType item, boolean empty);

    }

    public static class DefaultTableCell<ColumnType, ItemType> extends TableCell<ColumnType, ItemType> {

        private final ColumnContainerCellUpdater<ColumnType, ItemType, TableCell<ColumnType, ItemType>>[] updaters;

        @SafeVarargs public DefaultTableCell(ColumnContainerCellUpdater<ColumnType, ItemType, TableCell<ColumnType, ItemType>>... updaters) {
            this.updaters = updaters;
        }

        @Override protected void updateItem(ItemType item, boolean empty) {
            super.updateItem(item, empty);
            stream(updaters).forEach(updater -> updater.update(this, getTableRow().getItem(), item, empty));
        }

    }

    public static class DefaultTreeTableCell<ColumnType, ItemType> extends TreeTableCell<ColumnType, ItemType> {

        private final ColumnContainerCellUpdater<ColumnType, ItemType, TreeTableCell<ColumnType, ItemType>>[] updaters;

        @SafeVarargs public DefaultTreeTableCell(ColumnContainerCellUpdater<ColumnType, ItemType, TreeTableCell<ColumnType, ItemType>>... updaters) {
            this.updaters = updaters;
        }

        @Override protected void updateItem(ItemType item, boolean empty) {
            super.updateItem(item, empty);
            stream(updaters).forEach(updater -> updater.update(this, getTreeTableRow().getItem(), item, empty));
        }

    }

    @SafeVarargs public static <T> Callback<ListView<T>, ListCell<T>> listCellFactory(CellUpdater<T, ListCell<T>>... updaters) {
        return listView -> new DefaultListCell<>(updaters);
    }

    @SafeVarargs public static <T> Callback<TreeView<T>, TreeCell<T>> treeCellFactory(CellUpdater<T, TreeCell<T>>... updaters) {
        return view -> new DefaultTreeCell<>(updaters);
    }

    @SafeVarargs public static <ColumnType, ItemType> Callback<TableColumn<ColumnType, ItemType>, TableCell<ColumnType, ItemType>> tableColumnCellFactory(
        ColumnContainerCellUpdater<ColumnType, ItemType, TableCell<ColumnType, ItemType>>... updaters) {
        return column -> new DefaultTableCell<>(updaters);
    }

    @SafeVarargs public static <ColumnType, ItemType> Callback<TreeTableColumn<ColumnType, ItemType>, TreeTableCell<ColumnType, ItemType>> treeTableColumnCellFactory(
        ColumnContainerCellUpdater<ColumnType, ItemType, TreeTableCell<ColumnType, ItemType>>... updaters) {
        return column -> new DefaultTreeTableCell<>(updaters);
    }

}
