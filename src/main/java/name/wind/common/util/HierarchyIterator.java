package name.wind.common.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class HierarchyIterator<T> implements Iterator<T> {

    class Result {

        final Node node;
        final T next;

        public Result(Node node, T next) {
            this.node = node;
            this.next = next;
        }

    }

    private class Node {

        final Node parent;
        final T object;
        final Iterator<T> iterator;

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
    private final Function<T, Iterable<T>> exposer;

    private Node node;

    public HierarchyIterator(T root, Function<T, Iterable<T>> exposer) {
        this.root = root;
        this.exposer = exposer;
    }

    @Override
    public boolean hasNext() {
        return node == null || node.hasNext();
    }

    @Override
    public T next() {
        T next;

        if (node == null) {
            node = new Node(null, next = root);
        } else {
            next = (node = node.next()).object;
        }

        return next;
    }

}
