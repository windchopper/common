package ru.wind.common.fx;

import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.function.Function;

public class TreeViewHelper {

    public static <T> void trackExpansions(TreeItem<T> treeItem, Collection<String> paths, Function<TreeItem<T>, String> treeItemStringer, Runnable callback) {
        treeItem.expandedProperty().addListener(
            (property, oldExpandedState, newExpandedState) -> {
                boolean changed;
                String path = treeItemPath(treeItem, treeItemStringer);

                if (newExpandedState) {
                    changed = paths.add(path);
                } else {
                    changed = paths.remove(path);
                }

                if (changed) {
                    callback.run();
                }
            }
        );

        treeItem.getChildren().forEach(
            childItem -> trackExpansions(childItem, paths, treeItemStringer, callback)
        );
    }

    @SafeVarargs public static <T> void restoreExpanded(TreeItem<T> treeItem, Collection<String> paths, Function<TreeItem<T>, String> treeItemStringer, TreeItem<T>... excludeTreeItems) {
        boolean set = true;

        for (TreeItem<T> exclusion : excludeTreeItems) {
            if (treeItem == exclusion) {
                set = false;
                break;
            }
        }

        if (set) {
            treeItem.setExpanded(
                paths.contains(
                    treeItemPath(treeItem, treeItemStringer)
                )
            );
        }

        treeItem.getChildren().forEach(
            (descendantItem) -> restoreExpanded(descendantItem, paths, treeItemStringer, excludeTreeItems)
        );
    }

    public static <T> String treeItemPath(TreeItem<T> treeItem, Function<TreeItem<T>, String> treeItemStringer) {
        StringBuilder pathBuilder = new StringBuilder(
            treeItemStringer.apply(treeItem)
        );

        if (treeItem.getParent() != null) {
            pathBuilder.insert(0, '/').insert(
                0, treeItemPath(
                    treeItem.getParent(), treeItemStringer
                )
            );
        }

        return pathBuilder.toString();
    }

}
