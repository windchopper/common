package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.PreferencesEntryStructuralType;
import com.github.windchopper.common.preferences.entries.StandardEntry;
import com.github.windchopper.common.preferences.types.BooleanType;
import com.github.windchopper.common.preferences.types.IntegerType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS) public class PlatformStorageTest {

    private Preferences preferencesNode;

    @BeforeAll void preparePreferencesNode() {
        preferencesNode = Preferences.userNodeForPackage(PlatformStorageTest.class);
    }

    @AfterAll void dropPreferencesNode() throws BackingStoreException {
        preferencesNode.removeNode();
    }

    @Test public void testSaveStructural() {
        class Structure {

            final Integer integerValue;
            final Boolean booleanValue;

            public Structure(Integer integerValue, Boolean booleanValue) {
                this.integerValue = integerValue;
                this.booleanValue = booleanValue;
            }

        }

        var storage = new PlatformStorage(preferencesNode);

        var entry = new StandardEntry<>(storage, "structure", new PreferencesEntryStructuralType<>(
            Map.of("integer", new IntegerType(), "boolean", new BooleanType()),
            map -> new Structure((Integer) map.get("integer"), (Boolean) map.get("boolean")),
            structure -> Map.of("integer", structure.integerValue, "boolean", structure.booleanValue)));

        var structure = new Structure(1, true);
        entry.save(structure);

        var structurePreferencesNode = preferencesNode.node("structure");

        assertEquals("1", structurePreferencesNode.get("integer", null));
        assertEquals("true", structurePreferencesNode.get("boolean", null));
    }

}
