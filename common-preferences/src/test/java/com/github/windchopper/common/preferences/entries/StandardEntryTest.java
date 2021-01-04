package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.preferences.types.BooleanType;
import com.github.windchopper.common.preferences.types.IntegerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) public class StandardEntryTest {

    @Mock private PreferencesStorage storage;
    @Mock private PreferencesStorage childStorage1st;
    @Mock private PreferencesStorage childStorage2nd;

    @Test public void test() throws Exception {
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

}
