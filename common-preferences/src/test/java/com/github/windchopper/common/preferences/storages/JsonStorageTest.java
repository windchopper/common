package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.entries.StandardEntry;
import com.github.windchopper.common.preferences.types.StringType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class) public class JsonStorageTest {

    @Test public void testTimestamp() {
        var timestamp = Instant.now();

        var node1st = new HashMap<>();

        node1st.put("timestamp", timestamp.plusSeconds(10));
        node1st.put("value", "val1st");

        var node2nd = new HashMap<>();

        node2nd.put("value", "val2nd");

        var root = new HashMap<String, Object>();

        root.put("node1st", node1st);
        root.put("node2nd", node2nd);

        var jsonStorage = new JsonStorage(timestamp, root);

        var entry1st = new StandardEntry<>(jsonStorage, "node1st", new StringType());
        var entry2nd = new StandardEntry<>(jsonStorage, "node2nd", new StringType());

        assertNotEquals(timestamp, entry1st.load().getTimestamp());
        assertEquals(timestamp, entry2nd.load().getTimestamp());
    }

}
