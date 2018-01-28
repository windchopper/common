package com.github.windchopper.common.monitoring;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
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

    public void registerStart(@Nonnull String name, @Nonnegative long startTimeSeconds) {
        if (!enabled) return;
        namedMeasurements.computeIfAbsent(name, missingName -> new Measurements())
            .registerStart(startTimeSeconds);
    }

    public void registerSuccess(@Nonnull String name, @Nonnegative long startTimeSeconds, @Nonnegative long executionTimeNanoseconds) {
        if (!enabled) return;
        namedMeasurements.getOrDefault(name, missingMeasurements)
            .registerSuccess(startTimeSeconds, executionTimeNanoseconds);
    }

    public void registerFail(@Nonnull String name, @Nonnegative long startTimeSeconds, @Nonnegative long executionTimeNanoseconds) {
        if (!enabled) return;
        namedMeasurements.getOrDefault(name, missingMeasurements)
            .registerFail(startTimeSeconds, executionTimeNanoseconds);
    }

    @Override
    public @Nonnull Statistics[] gatherStatistics(@Nonnegative long startTimeSeconds, @Nonnegative long endTimeSeconds) {
        return namedMeasurements.entrySet().stream()
            .map(entry -> entry.getValue().gatherStatistics(entry.getKey(), startTimeSeconds, endTimeSeconds))
            .toArray(Statistics[]::new);
    }

}
