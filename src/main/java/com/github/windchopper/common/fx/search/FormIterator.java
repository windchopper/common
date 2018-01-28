package com.github.windchopper.common.fx.search;

import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import com.github.windchopper.common.util.HierarchyIterator;

import javax.annotation.Nonnull;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

public class FormIterator extends HierarchyIterator<Object> {

    public FormIterator(@Nonnull Object root) {
        super(requireNonNull(root), source -> source instanceof Scene
            ? singletonList(Scene.class.cast(source).getRoot()) : source instanceof ContextMenu
            ? ContextMenu.class.cast(source).getItems() : source instanceof Pane
            ? Pane.class.cast(source).getChildren() : source instanceof Control
            ? singletonList(Control.class.cast(source).getContextMenu()) : emptyList());
    }

}
