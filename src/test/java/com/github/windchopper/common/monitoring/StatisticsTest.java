package com.github.windchopper.common.monitoring;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class StatisticsTest {

    @BeforeClass
    public static void prepareStatic() {
        StatisticsCollector.Holder.instance.setEnabled(true);
        ExpensesCollector.Holder.instance.setEnabled(true);
    }

    public void monitoredOperation(String name, boolean fail) throws InterruptedException {
        Monitor monitor = new Monitor(name);
        monitor.started();

        Thread.sleep(123);

        if (fail) {
            monitor.failed();
        } else {
            monitor.succeeded();
        }
    }

    @Test
    public void testOperationMonitor() throws InterruptedException {
        long beginTime = currentTimeMillis();

        monitoredOperation("operationName", false);

        long endTime = currentTimeMillis();

        Statistics[] statisticsArray = StatisticsCollector.Holder.instance.gatherStatistics(
            beginTime / 1000, endTime / 1000 + 1);

        assertEquals(1, statisticsArray.length);

        Statistics statistics = statisticsArray[0];

        assertEquals(1, statistics.getStartedCount());
        assertEquals(1, statistics.getSucceededCount());
        assertEquals(0, statistics.getFailedCount());
        assertTrue(statistics.getTotalTimeNanoseconds() >= 123 * 1000000);
        assertTrue(statistics.getMinTimeNanoseconds() == statistics.getMaxTimeNanoseconds());

        assertTrue(ExpensesCollector.Holder.instance.getOperationStatisticsTimeNanoseconds() > 0);
        assertEquals(1, ExpensesCollector.Holder.instance.getOperationCount());
    }

}