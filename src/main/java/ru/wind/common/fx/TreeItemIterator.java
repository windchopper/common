package ru.wind.common.fx;

import javafx.scene.control.TreeItem;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TreeItemIterator<T> implements Iterator<TreeItem<T>> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");

    private TreeItem<T> previous;

    public TreeItemIterator(TreeItem<T> root) {
        this.previous = Objects.requireNonNull(root, () -> bundle.getString("common.fx.TreeItemIterator.nullParameter.root"));
    }

    TreeItem<T> findNext(TreeItem<T> from) {
        if (from != null) {
            if (previous.getChildren().size() > 0) {
                return previous.getChildren().get(0);
            } else {
                TreeItem<T> parent = from.getParent();

                while (parent != null) {
                    int nextIndex = parent.getChildren().indexOf(from) + 1;

                    if (nextIndex < parent.getChildren().size()) {
                        return parent.getChildren().get(nextIndex);
                    } else {
                        from = parent;
                        parent = parent.getParent();
                    }
                }
            }
        }

        return null;
    }

    @Override public boolean hasNext() {
        return findNext(previous) != null;
    }

    @Override public TreeItem<T> next() {
        previous = findNext(previous);

        if (previous == null) {
            throw new NoSuchElementException(
                bundle.getString("common.fx.TreeItemIterator.noNextItem")
            );
        }

        return previous;
    }

}
