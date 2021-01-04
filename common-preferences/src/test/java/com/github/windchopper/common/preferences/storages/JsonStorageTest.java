package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.entries.StandardEntry;
import com.github.windchopper.common.preferences.types.StringType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.json.Json;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class) public class JsonStorageTest {

    @Test public void testTimestamp() {
        var timestamp = Instant.now();

        var jsonStorage = new JsonStorage(timestamp, Json.createObjectBuilder()
            .add("node1st", Json.createObjectBuilder()
                .add("timestamp", timestamp.plusSeconds(10).toString())
                .add("value", "val1st")
                .build())
            .add("node2nd", Json.createObjectBuilder()
                .add("value", "val2nd")
                .build())
            .build());

        var entry1st = new StandardEntry<>(jsonStorage, "node1st", new StringType());
        var entry2nd = new StandardEntry<>(jsonStorage, "node2nd", new StringType());

        assertNotEquals(timestamp, entry1st.load().getTimestamp());
        assertEquals(timestamp, entry2nd.load().getTimestamp());
    }

}
