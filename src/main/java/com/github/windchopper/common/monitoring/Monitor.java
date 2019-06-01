package com.github.windchopper.common.monitoring;

public class Monitor {

    private final String name;

    private long startTimeNanoseconds;

    public Monitor(String name) {
        this.name = name;
    }

    public void started() {
        startTimeNanoseconds = System.nanoTime();
        StatisticsCollector.Holder.instance.registerStart(name);
    }

    public void succeeded() {
        StatisticsCollector.Holder.instance.registerSuccess(name, System.nanoTime() - startTimeNanoseconds);
    }

    public void failed() {
        StatisticsCollector.Holder.instance.registerFail(name, System.nanoTime() - startTimeNanoseconds);
    }

}
