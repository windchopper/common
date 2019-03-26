package com.github.windchopper.common.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JUnit4.class) public class SharedLockTest {

    @Test public void testSingleTime() throws InterruptedException {
        var sharedLock1st = new SharedLock();
        var sharedLock2nd = new SharedLock();

        test(sharedLock1st, sharedLock2nd);

        assertFalse(sharedLock1st.tryLock());
        assertFalse(sharedLock2nd.tryLock());
    }

    @Test public void testMultipleTimes() throws InterruptedException {
        var sharedLock1st = new SharedLock();
        var sharedLock2nd = new SharedLock();

        test(sharedLock1st, sharedLock2nd);

        sharedLock1st.unlock();
        sharedLock2nd.unlock();

        test(sharedLock1st, sharedLock2nd);

        sharedLock1st.unlock();
        sharedLock2nd.unlock();

        test(sharedLock1st, sharedLock2nd);

        assertFalse(sharedLock1st.tryLock());
        assertFalse(sharedLock2nd.tryLock());
    }

    void test(SharedLock sharedLock1st, SharedLock sharedLock2nd) throws InterruptedException {
        var tickets = new ArrayList<>();

        sharedLock1st.lock();
        sharedLock2nd.lock();

        Runnable runnable1st = () -> {
            tickets.add("1-1");
            sharedLock1st.unlock();
            sharedLock2nd.lock();
            tickets.add("1-2");
        };

        Runnable runnable2nd = () -> {
            sharedLock1st.lock();
            tickets.add("2-1");
            tickets.add("2-2");
            sharedLock2nd.unlock();
        };

        var thread1st = new Thread(runnable1st);
        thread1st.start();

        var thread2nd = new Thread(runnable2nd);
        thread2nd.start();

        thread1st.join();
        thread2nd.join();

        var correctTickets = asList("1-1", "2-1", "2-2", "1-2");

        assertEquals(correctTickets.size(), tickets.size());

        for (int i = 0, count = correctTickets.size(); i < count; i++) {
            assertEquals("1-1", tickets.get(0));
        }
    }

}
