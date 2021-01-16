package com.github.windchopper.common.fx.search;

import com.github.windchopper.common.util.HierarchyIterator;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class FormIterator extends HierarchyIterator<Object> {

    public FormIterator(Object root) {
        super(root, source -> source instanceof Scene
            ? singletonList(((Scene) source).getRoot()) : source instanceof ContextMenu
            ? ((ContextMenu) source).getItems() : source instanceof Pane
            ? ((Pane) source).getChildren() : source instanceof Control
            ? singletonList(((Control) source).getContextMenu()) : emptyList());
    }

}
