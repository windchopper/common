package com.github.windchopper.common.preferences;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test public void test() {
        var storageRoot = new JndiPreferencesStorage(() -> context, "value:", "child:");

        assertEquals("v#1", storageRoot.value("k#1", "k#1 not set"));
        assertEquals("v#2", storageRoot.value("k#2", "k#2 not set"));

        var sample = "oops";
        assertEquals(sample, storageRoot.value(sample, sample));
    }

}