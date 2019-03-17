package com.github.windchopper.common.preferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class JndiPreferencesStorageTest {

    @Mock private Context context;

    @Before @SuppressWarnings("unchecked") public void prepare() throws NamingException {
        NamingEnumeration<NameClassPair> bindingEnumeration = mock(NamingEnumeration.class);
        Iterator<NameClassPair> bindingIterator = Arrays.asList(
            new NameClassPair("value:k#1", String.class.getName()),
            new NameClassPair("value:k#2", String.class.getName())).iterator();

        when(bindingEnumeration.hasMoreElements()).thenAnswer(invocation -> bindingIterator.hasNext());
        when(bindingEnumeration.nextElement()).thenAnswer(invocation -> bindingIterator.next());
        doNothing().when(bindingEnumeration).close();

        when(context.lookup("value:k#1")).thenReturn("v#1");
        when(context.lookup("value:k#2")).thenReturn("v#2");
        when(context.list(anyString())).thenAnswer(invocationOnMock -> bindingEnumeration);
    }

    @Test public void test() {
        JndiPreferencesStorage storageRoot = new JndiPreferencesStorage(() -> context, "value:", "child:");

        assertEquals("v#1", storageRoot.value("k#1", "k#1 not set"));
        assertEquals("v#2", storageRoot.value("k#2", "k#2 not set"));

        String sample = "oops";
        assertEquals(sample, storageRoot.value(sample, sample));
    }

}
