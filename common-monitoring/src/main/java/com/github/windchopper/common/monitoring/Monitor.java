package com.github.windchopper.common.monitoring;

import static java.lang.System.currentTimeMillis;

public class Monitor {

    private String name;
    private long startTimeMilliseconds;

    public Monitor(String name) {
        this.name = name;
    }

    public void started() {
        startTimeMilliseconds = currentTimeMillis();
        StatisticsCollector.Holder.instance.registerStart(name);
    }

    public void succeeded() {
        StatisticsCollector.Holder.instance.registerSuccess(name,
            currentTimeMillis() - startTimeMilliseconds);
    }

    public void failed() {
        StatisticsCollector.Holder.instance.registerFail(name,
            currentTimeMillis() - startTimeMilliseconds);
    }

}
