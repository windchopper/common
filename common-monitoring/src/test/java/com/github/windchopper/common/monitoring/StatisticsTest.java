package com.github.windchopper.common.monitoring;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatisticsTest {

    @BeforeAll static void initialize() {
        StatisticsCollector.Holder.instance.setEnabled(true);
    }

    void monitoredOperation(String name, boolean fail) throws InterruptedException {
        var monitor = new Monitor(name);
        monitor.started();

        Thread.sleep(123);

        if (fail) {
            monitor.failed();
        } else {
            monitor.succeeded();
        }
    }

    @Test public void testOperationMonitor() throws InterruptedException {
        long beginTime = currentTimeMillis();

        monitoredOperation("operationName", false);

        long endTime = currentTimeMillis();

        var statisticsArray = StatisticsCollector.Holder.instance.gatherStatistics(
            beginTime / 1000, endTime / 1000 + 1);

        assertEquals(1, statisticsArray.length);

        var statistics = statisticsArray[0];

        assertEquals(1, statistics.getStartedCount());
        assertEquals(1, statistics.getSucceededCount());
        assertEquals(0, statistics.getFailedCount());
        assertTrue(statistics.getTotalTimeMilliseconds() >= 123);
        assertEquals(statistics.getMinTimeMilliseconds(), statistics.getMaxTimeMilliseconds());
    }

}
