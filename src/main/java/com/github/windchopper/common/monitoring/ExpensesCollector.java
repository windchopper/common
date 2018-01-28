package com.github.windchopper.common.monitoring;

import java.util.concurrent.atomic.AtomicLong;

public class ExpensesCollector extends AnyCollector implements ExpensesCollectorMXBean {

    public interface Holder {
        ExpensesCollector instance = new ExpensesCollector();
    }

    private final AtomicLong operationStatisticsTimeNanoseconds = new AtomicLong();
    private final AtomicLong operationCount = new AtomicLong();

    @Override
    public long getOperationStatisticsTimeNanoseconds() {
        return operationStatisticsTimeNanoseconds.get();
    }
    
    public long addAndGetOperationStatisticsTimeNanoseconds(long nanoseconds) {
        return operationStatisticsTimeNanoseconds.addAndGet(nanoseconds);
    }

    @Override
    public long getOperationCount() {
        return operationCount.get();
    }
    
    public long incAndGetOperationCount() {
        return operationCount.incrementAndGet();
    } 

}
