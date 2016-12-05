package name.wind.common.util;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class HierarchyIterator<T> implements Iterator<T> {

    private class Node {

        final Node parent;
        final T object;
        final Iterator<? extends T> iterator;

        Node(Node parent, T object) {
            this.parent = parent;
            iterator = exposer.apply(this.object = object).iterator();
        }

        boolean hasNext() {
            return iterator.hasNext() || parent != null && parent.hasNext();
        }

        Node next() throws NoSuchElementException {
            try {
                return new Node(this, iterator.next());
            } catch (NoSuchElementException thrown) {
                if (parent != null) {
                    return parent.next();
                }

                throw thrown;
            }
        }

    }

    private final T root;
    private final Function<T, Iterable<? extends T>> exposer;

    private Node node;

    public HierarchyIterator(@Nonnull T root, @Nonnull Function<T, Iterable<? extends T>> exposer) {
        this.root = requireNonNull(root);
        this.exposer = requireNonNull(exposer);
    }

    @Override public boolean hasNext() {
        return node == null || node.hasNext();
    }

    @Override @Nonnull public T next() {
        T next;

        if (node == null) {
            node = new Node(null, next = root);
        } else {
            next = (node = node.next()).object;
        }

        return next;
    }

}
