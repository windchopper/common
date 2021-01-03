package com.github.windchopper.common.preferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) public class JndiPreferencesStorageTest {

    @Mock private Context context;
    @Mock private NamingEnumeration<NameClassPair> bindings;

    @BeforeEach public void prepare() throws NamingException {
        when(bindings.hasMoreElements())
            .thenReturn(true)
            .thenReturn(true)
            .thenReturn(false);
        when(bindings.nextElement())
            .thenReturn(new NameClassPair("value:k#1", String.class.getName()))
            .thenReturn(new NameClassPair("value:k#2", String.class.getName()));

        when(context.lookup(eq("value:k#1")))
            .thenReturn("v#1");
        when(context.lookup(eq("value:k#2")))
            .thenReturn("v#2");
        when(context.list(eq("")))
            .thenReturn(bindings);
    }

    @Test public void test() throws NamingException {
        var storageRoot = new JndiPreferencesStorage(() -> context, "value:", "child:");

        var value1st = storageRoot.value("k#1");
        assertTrue(value1st.isPresent());
        assertEquals("v#1", value1st.get().text());

        var value2nd = storageRoot.value("k#2");
        assertTrue(value2nd.isPresent());
        assertEquals("v#2", value2nd.get().text());

        var valueOops = storageRoot.value("oops");
        assertFalse(valueOops.isPresent());
    }

}
