package com.github.windchopper.common.fx.search;

import com.github.windchopper.common.util.HierarchyIterator;
import javafx.scene.control.TreeItem;

public class TreeItemIterator<T> extends HierarchyIterator<TreeItem<T>> {

    public TreeItemIterator(TreeItem<T> root) {
        super(root, TreeItem::getChildren);
    }

}
