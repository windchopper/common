package com.github.windchopper.common.fx.search;

import javafx.scene.control.TreeItem;
import com.github.windchopper.common.util.HierarchyIterator;

import static java.util.Objects.requireNonNull;

public class TreeItemIterator<T> extends HierarchyIterator<TreeItem<T>> {

    public TreeItemIterator(TreeItem<T> root) {
        super(requireNonNull(root), TreeItem::getChildren);
    }

}
