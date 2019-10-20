package com.github.windchopper.common.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static java.util.Collections.addAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HierarchyIteratorTest {

    static class Node {

        final Object value;
        final Collection<Node> subnodes = new ArrayList<>();

        Node(Object value) {
            this.value = value;
        }

        Node subnodes(Node... nodes) {
            addAll(subnodes, nodes);
            return this;
        }

    }

    @Test public void testIterator() {
        var valueIterator = IntStream.range(0, 20).iterator();
        int lastValue;

        var root = new Node(valueIterator.next()).subnodes(
            new Node(valueIterator.next()).subnodes(
                new Node(valueIterator.next()),
                new Node(valueIterator.next()),
                new Node(valueIterator.next())),
            new Node(valueIterator.next()).subnodes(
                new Node(valueIterator.next()),
                new Node(valueIterator.next()),
                new Node(valueIterator.next())),
            new Node(valueIterator.next()).subnodes(
                new Node(valueIterator.next()),
                new Node(valueIterator.next()),
                new Node(lastValue = valueIterator.next())));

        var iterator = new HierarchyIterator<>(root, node -> node.subnodes);
        valueIterator = IntStream.range(0, 20).iterator();

        while (iterator.hasNext()) {
            assertEquals(valueIterator.next(), iterator.next().value);
            lastValue--;
        }

        assertEquals(-1, lastValue);
        assertFalse(iterator.hasNext());

        try {
            iterator.next();
            throw new AssertionError("Iterator.next() of exhausted iterator must throw NoSuchElementException");
        } catch (NoSuchElementException ignored) {
        }
    }

}
