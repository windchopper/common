package com.github.windchopper.common.monitoring;

import java.beans.ConstructorProperties;

public class Statistics {

    private String name;
    private long startTimeSeconds;
    private long endTimeSeconds;
    private long startedCount;
    private long succeededCount;
    private long failedCount;
    private long totalTimeMilliseconds;
    private long averageTimeMilliseconds;
    private long minTimeMilliseconds;
    private long maxTimeMilliseconds;

    @ConstructorProperties({
        "name",
        "startTimeSeconds",
        "endTimeSeconds"
    })
    public Statistics(String name,
                      long startTimeSeconds,
                      long endTimeSeconds) {
        this.name = name;
        this.startTimeSeconds = startTimeSeconds;
        this.endTimeSeconds = endTimeSeconds;
    }

    @ConstructorProperties({
        "name",
        "startTimeSeconds",
        "endTimeSeconds",
        "startedCount",
        "succeededCount",
        "failedCount",
        "totalTimeMilliseconds",
        "averageTimeMilliseconds",
        "minTimeMilliseconds",
        "maxTimeMilliseconds"
    })
    public Statistics(String name,
                      long startTimeSeconds,
                      long endTimeSeconds,
                      long startedCount,
                      long succeededCount,
                      long failedCount,
                      long totalTimeMilliseconds,
                      long averageTimeMilliseconds,
                      long minTimeMilliseconds,
                      long maxTimeMilliseconds) {
        this(name, startTimeSeconds, endTimeSeconds);
        this.startedCount = startedCount;
        this.succeededCount = succeededCount;
        this.failedCount = failedCount;
        this.totalTimeMilliseconds = totalTimeMilliseconds;
        this.averageTimeMilliseconds = averageTimeMilliseconds;
        this.minTimeMilliseconds = minTimeMilliseconds;
        this.maxTimeMilliseconds = maxTimeMilliseconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(long startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

    public long getEndTimeSeconds() {
        return endTimeSeconds;
    }

    public void setEndTimeSeconds(long endTimeSeconds) {
        this.endTimeSeconds = endTimeSeconds;
    }

    public long getStartedCount() {
        return startedCount;
    }

    public void setStartedCount(long startedCount) {
        this.startedCount = startedCount;
    }

    public long getSucceededCount() {
        return succeededCount;
    }

    public void setSucceededCount(long succeededCount) {
        this.succeededCount = succeededCount;
    }

    public long getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(long failedCount) {
        this.failedCount = failedCount;
    }

    public long getTotalTimeMilliseconds() {
        return totalTimeMilliseconds;
    }

    public void setTotalTimeMilliseconds(long totalTimeMilliseconds) {
        this.totalTimeMilliseconds = totalTimeMilliseconds;
    }

    public long getAverageTimeMilliseconds() {
        return averageTimeMilliseconds;
    }

    public void setAverageTimeMilliseconds(long averageTimeMilliseconds) {
        this.averageTimeMilliseconds = averageTimeMilliseconds;
    }

    public long getMinTimeMilliseconds() {
        return minTimeMilliseconds;
    }

    public void setMinTimeMilliseconds(long minTimeMilliseconds) {
        this.minTimeMilliseconds = minTimeMilliseconds;
    }

    public long getMaxTimeMilliseconds() {
        return maxTimeMilliseconds;
    }

    public void setMaxTimeMilliseconds(long maxTimeMilliseconds) {
        this.maxTimeMilliseconds = maxTimeMilliseconds;
    }

    public void add(long startedCountAmount,
                    long succeededCountAmount,
                    long failedCountAmount,
                    long totalTimeMillisecondsAmount,
                    long minTimeMillisecondsAmount,
                    long maxTimeMillisecondsAmount) {
        startedCount += startedCountAmount;
        succeededCount += succeededCountAmount;
        failedCount += failedCountAmount;

        totalTimeMilliseconds += totalTimeMillisecondsAmount;

        if (succeededCount + failedCount > 0) {
            averageTimeMilliseconds = totalTimeMilliseconds / (succeededCount + failedCount);
        }

        if (minTimeMillisecondsAmount > 0) {
            if (minTimeMilliseconds > 0) {
                minTimeMilliseconds = Math.min(minTimeMilliseconds, minTimeMillisecondsAmount);
            } else {
                minTimeMilliseconds = minTimeMillisecondsAmount;
            }
        }

        if (maxTimeMillisecondsAmount > 0) {
            if (maxTimeMilliseconds > 0) {
                maxTimeMilliseconds = Math.max(maxTimeMilliseconds, maxTimeMillisecondsAmount);
            } else {
                maxTimeMilliseconds = maxTimeMillisecondsAmount;
            }
        }
    }

}
