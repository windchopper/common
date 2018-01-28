package com.github.windchopper.common.monitoring;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class Monitor {

    private final String name;

    private long startTimeSeconds;
    private long startTimeNanoseconds;

    public Monitor(@Nonnull String name) {
        this.name = name;
    }

    public void started() {
        long enterTimeNanoseconds = System.nanoTime();

        try {
            startTimeNanoseconds = System.nanoTime();
            StatisticsCollector.Holder.instance.registerStart(
                name, startTimeSeconds = System.currentTimeMillis() / 1000L);
        } finally {
            ExpensesCollector.Holder.instance.addAndGetOperationStatisticsTimeNanoseconds(
                System.nanoTime() - enterTimeNanoseconds);
        }
    }

    public void succeeded() {
        long enterTimeNanoseconds = System.nanoTime();

        try {
            StatisticsCollector.Holder.instance.registerSuccess(
                name, startTimeSeconds, enterTimeNanoseconds - startTimeNanoseconds);
        } finally {
            ExpensesCollector.Holder.instance.incAndGetOperationCount();
            ExpensesCollector.Holder.instance.addAndGetOperationStatisticsTimeNanoseconds(
                System.nanoTime() - enterTimeNanoseconds);
        }
    }

    public void failed() {
        long enterTimeNanoseconds = System.nanoTime();

        try {
            StatisticsCollector.Holder.instance.registerFail(
                name, startTimeSeconds, enterTimeNanoseconds - startTimeNanoseconds);
        } finally {
            ExpensesCollector.Holder.instance.incAndGetOperationCount();
            ExpensesCollector.Holder.instance.addAndGetOperationStatisticsTimeNanoseconds(
                System.nanoTime() - enterTimeNanoseconds);
        }
    }

}
