package com.github.windchopper.common.monitoring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsCollector extends AnyCollector implements StatisticsCollectorMXBean {

    public interface Holder {
        StatisticsCollector instance = new StatisticsCollector();
    }

    private final Map<String, Measurements> namedMeasurements = new ConcurrentHashMap<>();
    private final Measurements missingMeasurements = new Measurements() {
        @Override public void registerStart(long startTimeSeconds) {}
        @Override public void registerSuccess(long startTimeSeconds, long executionTimeNanoseconds) {}
        @Override public void registerFail(long startTimeSeconds, long executionTimeNanoseconds) {}
    };

    public void registerStart(String name, long startTimeSeconds) {
        if (!enabled) return;
        namedMeasurements.computeIfAbsent(name, missingName -> new Measurements())
            .registerStart(startTimeSeconds);
    }

    public void registerSuccess(String name, long startTimeSeconds, long executionTimeNanoseconds) {
        if (!enabled) return;
        namedMeasurements.getOrDefault(name, missingMeasurements)
            .registerSuccess(startTimeSeconds, executionTimeNanoseconds);
    }

    public void registerFail(String name, long startTimeSeconds, long executionTimeNanoseconds) {
        if (!enabled) return;
        namedMeasurements.getOrDefault(name, missingMeasurements)
            .registerFail(startTimeSeconds, executionTimeNanoseconds);
    }

    @Override
    public Statistics[] gatherStatistics(long startTimeSeconds, long endTimeSeconds) {
        return namedMeasurements.entrySet().stream()
            .map(entry -> entry.getValue().gatherStatistics(entry.getKey(), startTimeSeconds, endTimeSeconds))
            .toArray(Statistics[]::new);
    }

}
