package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.PreferencesEntryStructuralType;
import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.preferences.types.BooleanType;
import com.github.windchopper.common.preferences.types.IntegerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class StandardEntryTest {

    @Mock private PreferencesStorage storage;
    @Mock private PreferencesStorage childStorage1st;
    @Mock private PreferencesStorage childStorage2nd;

    @BeforeEach void prepare() {
        reset(storage, childStorage1st, childStorage2nd);
    }

    @Test public void testFlat() throws Exception {
        when(storage.child(eq("integerKey"))).thenReturn(childStorage1st);
        when(childStorage1st.value(eq("value"))).thenReturn("1");
        when(storage.child(eq("booleanKey"))).thenReturn(childStorage2nd);
        when(childStorage2nd.value(eq("value"))).thenReturn("true");

        var integerEntry = new StandardEntry<>(
            storage, "integerKey", new IntegerType());

        var booleanEntry = new StandardEntry<>(
            storage, "booleanKey", new BooleanType());

        assertEquals(1, integerEntry.load().getValue());
        assertEquals(true, booleanEntry.load().getValue());
    }

    @Test public void testStructural() throws Exception {
        class Structure {

            final Integer integerValue;
            final Boolean booleanValue;

            public Structure(Integer integerValue, Boolean booleanValue) {
                this.integerValue = integerValue;
                this.booleanValue = booleanValue;
            }

        }

        when(storage.child(eq("structure"))).thenReturn(childStorage1st);
        when(childStorage1st.value(eq("integer"))).thenReturn("1");
        when(childStorage1st.value(eq("boolean"))).thenReturn("true");

        var entry = new StandardEntry<>(storage, "structure", new PreferencesEntryStructuralType<>(
            Map.of("integer", new IntegerType(), "boolean", new BooleanType()),
            map -> new Structure((Integer) map.get("integer"), (Boolean) map.get("boolean")),
            structure -> Map.of("integer", structure.integerValue, "boolean", structure.booleanValue)));

        var structure = entry.load().getValue();

        assertEquals(1, structure.integerValue);
        assertEquals(true, structure.booleanValue);

        structure = new Structure(2, false);
        entry.save(structure);

        verify(childStorage1st, times(1)).saveValue(eq("integer"), eq("2"));
        verify(childStorage1st, times(1)).saveValue(eq("boolean"), eq("false"));
    }

}
