package com.github.windchopper.common.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static java.util.Collections.addAll;

@RunWith(JUnit4.class) public class HierarchyIteratorTest {

    class Node {

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
            Assert.assertEquals(valueIterator.next(), iterator.next().value);
            lastValue--;
        }

        Assert.assertEquals(-1, lastValue);
        Assert.assertFalse(iterator.hasNext());

        try {
            iterator.next();
            throw new AssertionError("Iterator.next() of exhausted iterator must throw NoSuchElementException");
        } catch (NoSuchElementException ignored) {
        }
    }

}
