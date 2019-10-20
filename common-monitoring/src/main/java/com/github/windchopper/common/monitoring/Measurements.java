package com.github.windchopper.common.monitoring;

import java.util.concurrent.atomic.AtomicLongArray;

import static java.lang.System.currentTimeMillis;

public class Measurements {

    private static final int STARTED_COUNT_INDEX = 0;
    private static final int SUCCEEDED_COUNT_INDEX = 1;
    private static final int FAILED_COUNT_INDEX = 2;
    private static final int TOTAL_TIME_INDEX = 3;
    private static final int MIN_TIME_INDEX = 4;
    private static final int MAX_TIME_INDEX = 5;

    private static final int SLOT_NUMBER = 600;
    private static final int SLOT_LENGTH = 6;

    private final AtomicLongArray values = new AtomicLongArray(SLOT_NUMBER * SLOT_LENGTH);

    private long periodStartTimeSeconds;
    private long periodEndTimeSeconds;

    private int determineOffset(long timeSeconds) {
        int offset = (int) (timeSeconds - periodStartTimeSeconds) * SLOT_LENGTH;

        if (offset > SLOT_NUMBER) {
            periodStartTimeSeconds = timeSeconds;
            offset = 0;

            for (int i = 0; i < values.length(); i++) {
                values.set(i, 0L);
            }

            for (int i = MIN_TIME_INDEX; i < values.length(); i += SLOT_LENGTH) {
                values.set(i, Long.MAX_VALUE);
            }
        }

        periodEndTimeSeconds = timeSeconds;

        return offset;
    }

    public void registerStart() {
        int offset = determineOffset(currentTimeMillis() / 1000L);

        values.incrementAndGet(offset + STARTED_COUNT_INDEX);
    }

    public void registerFinish(int index, long executionTimeMilliseconds) {
        int offset = determineOffset(currentTimeMillis() / 1000L);

        values.incrementAndGet(offset + index);
        values.addAndGet(offset + TOTAL_TIME_INDEX, executionTimeMilliseconds);
        values.getAndUpdate(offset + MIN_TIME_INDEX, oldMinTime -> Math.min(oldMinTime, executionTimeMilliseconds));
        values.getAndUpdate(offset + MAX_TIME_INDEX, oldMaxTime -> Math.max(oldMaxTime, executionTimeMilliseconds));
    }

    public void registerSuccess(long executionTimeMilliseconds) {
        registerFinish(SUCCEEDED_COUNT_INDEX, executionTimeMilliseconds);
    }

    public void registerFail(long executionTimeMilliseconds) {
        registerFinish(FAILED_COUNT_INDEX, executionTimeMilliseconds);
    }

    public Statistics gatherStatistics(String name, long startTimeSeconds, long endTimeSeconds) {
        if (endTimeSeconds < startTimeSeconds) throw new IllegalArgumentException("endTimeSeconds < startTimeSeconds");

        startTimeSeconds = Math.max(startTimeSeconds, periodStartTimeSeconds);
        endTimeSeconds = Math.min(endTimeSeconds, periodStartTimeSeconds + SLOT_NUMBER);

        Statistics statistics = new Statistics(name, startTimeSeconds, endTimeSeconds);

        if (startTimeSeconds > periodEndTimeSeconds) {
            return statistics;
        }

        for (int i = (int) (startTimeSeconds - periodStartTimeSeconds) * SLOT_LENGTH; i < (int) (endTimeSeconds - periodStartTimeSeconds + 1) * SLOT_LENGTH; i += SLOT_LENGTH) {
            statistics.add(
                values.get(i + STARTED_COUNT_INDEX),
                values.get(i + SUCCEEDED_COUNT_INDEX),
                values.get(i + FAILED_COUNT_INDEX),
                values.get(i + TOTAL_TIME_INDEX),
                values.get(i + MIN_TIME_INDEX),
                values.get(i + MAX_TIME_INDEX));
        }

        return statistics;
    }

}
