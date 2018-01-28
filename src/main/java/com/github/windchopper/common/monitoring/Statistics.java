package com.github.windchopper.common.monitoring;

import java.beans.ConstructorProperties;

public class Statistics {

    private String name;
    private long startTimeSeconds;
    private long endTimeSeconds;
    private long startedCount;
    private long succeededCount;
    private long failedCount;
    private long totalTimeNanoseconds;
    private long averageTimeNanoseconds;
    private long minTimeNanoseconds;
    private long maxTimeNanoseconds;

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
        "totalTimeNanoseconds",
        "averageTimeNanoseconds",
        "minTimeNanoseconds",
        "maxTimeNanoseconds"
    })
    public Statistics(String name,
                      long startTimeSeconds,
                      long endTimeSeconds,
                      long startedCount,
                      long succeededCount,
                      long failedCount,
                      long totalTimeNanoseconds,
                      long averageTimeNanoseconds,
                      long minTimeNanoseconds,
                      long maxTimeNanoseconds) {
        this(name, startTimeSeconds, endTimeSeconds);
        this.startedCount = startedCount;
        this.succeededCount = succeededCount;
        this.failedCount = failedCount;
        this.totalTimeNanoseconds = totalTimeNanoseconds;
        this.averageTimeNanoseconds = averageTimeNanoseconds;
        this.minTimeNanoseconds = minTimeNanoseconds;
        this.maxTimeNanoseconds = maxTimeNanoseconds;
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

    public long getTotalTimeNanoseconds() {
        return totalTimeNanoseconds;
    }

    public void setTotalTimeNanoseconds(long totalTimeNanoseconds) {
        this.totalTimeNanoseconds = totalTimeNanoseconds;
    }

    public long getAverageTimeNanoseconds() {
        return averageTimeNanoseconds;
    }

    public void setAverageTimeNanoseconds(long averageTimeNanoseconds) {
        this.averageTimeNanoseconds = averageTimeNanoseconds;
    }

    public long getMinTimeNanoseconds() {
        return minTimeNanoseconds;
    }

    public void setMinTimeNanoseconds(long minTimeNanoseconds) {
        this.minTimeNanoseconds = minTimeNanoseconds;
    }

    public long getMaxTimeNanoseconds() {
        return maxTimeNanoseconds;
    }

    public void setMaxTimeNanoseconds(long maxTimeNanoseconds) {
        this.maxTimeNanoseconds = maxTimeNanoseconds;
    }

    public void add(long startedCountAmount,
                    long succeededCountAmount,
                    long failedCountAmount,
                    long totalTimeNanosecondsAmount,
                    long minTimeNanosecondsAmount,
                    long maxTimeNanosecondsAmount) {
        startedCount += startedCountAmount;
        succeededCount += succeededCountAmount;
        failedCount += failedCountAmount;

        totalTimeNanoseconds += totalTimeNanosecondsAmount;

        if (succeededCount + failedCount > 0) {
            averageTimeNanoseconds = totalTimeNanoseconds / (succeededCount + failedCount);
        }

        if (minTimeNanosecondsAmount > 0) {
            if (minTimeNanoseconds > 0) {
                minTimeNanoseconds = Math.min(minTimeNanoseconds, minTimeNanosecondsAmount);
            } else {
                minTimeNanoseconds = minTimeNanosecondsAmount;
            }
        }

        if (maxTimeNanosecondsAmount > 0) {
            if (maxTimeNanoseconds > 0) {
                maxTimeNanoseconds = Math.max(maxTimeNanoseconds, maxTimeNanosecondsAmount);
            } else {
                maxTimeNanoseconds = maxTimeNanosecondsAmount;
            }
        }
    }

}
