package com.github.windchopper.common.monitoring;

import javax.annotation.CheckForSigned;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Measurements {

    private static final int STARTED_COUNT_INDEX = 0;
    private static final int SUCCEEDED_COUNT_INDEX = 1;
    private static final int FAILED_COUNT_INDEX = 2;
    private static final int TOTAL_TIME_INDEX = 3;
    private static final int MIN_TIME_INDEX = 4;
    private static final int MAX_TIME_INDEX = 5;

    private static final int SLOT_NUMBER = 600;
    private static final int SLOT_LENGTH = 6;

    private final long[] values = new long[SLOT_NUMBER * SLOT_LENGTH];
    private final Lock lock = new ReentrantLock();

    private long periodStartTimeSeconds;
    private long periodEndTimeSeconds;

    private @CheckForSigned int determineOffset(@Nonnegative long startTimeSeconds) {
        if (periodStartTimeSeconds > startTimeSeconds) {
            return -1;
        }

        int offset = (int) (startTimeSeconds - periodStartTimeSeconds) * SLOT_LENGTH;

        if (offset > SLOT_NUMBER) {
            periodStartTimeSeconds = startTimeSeconds;
            offset = 0;

            for (int i = 0; i < values.length; i++) {
                values[i] = 0L;
            }

            for (int i = MIN_TIME_INDEX; i < values.length; i += SLOT_LENGTH) {
                values[i] = Long.MAX_VALUE;
            }
        }

        periodEndTimeSeconds = startTimeSeconds;

        return offset;
    }

    public void registerStart(@Nonnegative long startTimeSeconds) {
        lock.lock();

        try {
            int offset = determineOffset(startTimeSeconds);

            if (offset >= 0) {
                values[offset + STARTED_COUNT_INDEX]++;
            }
        } finally {
            lock.unlock();
        }
    }

    public void registerFinish(@Nonnegative int index, @Nonnegative long startTimeSeconds, @Nonnegative long executionTimeNanoseconds) {
        lock.lock();

        try {
            int offset = determineOffset(startTimeSeconds);

            if (offset >= 0) {
                values[offset + index]++;
                values[offset + TOTAL_TIME_INDEX] += executionTimeNanoseconds;
                values[offset + MIN_TIME_INDEX] = Math.min(values[offset + MIN_TIME_INDEX], executionTimeNanoseconds);
                values[offset + MAX_TIME_INDEX] = Math.max(values[offset + MAX_TIME_INDEX], executionTimeNanoseconds);
            }
        } finally {
            lock.unlock();
        }
    }

    public void registerSuccess(@Nonnegative long startTimeSeconds, @Nonnegative long executionTimeNanoseconds) {
        registerFinish(SUCCEEDED_COUNT_INDEX, startTimeSeconds, executionTimeNanoseconds);
    }

    public void registerFail(@Nonnegative long startTimeSeconds, @Nonnegative long executionTimeNanoseconds) {
        registerFinish(FAILED_COUNT_INDEX, startTimeSeconds, executionTimeNanoseconds);
    }

    public @Nonnull Statistics gatherStatistics(@Nonnull String name, @Nonnegative long startTimeSeconds, @Nonnegative long endTimeSeconds) {
        if (endTimeSeconds < startTimeSeconds) throw new IllegalArgumentException("endTimeSeconds < startTimeSeconds");

        startTimeSeconds = Math.max(startTimeSeconds, periodStartTimeSeconds);
        endTimeSeconds = Math.min(endTimeSeconds, periodStartTimeSeconds + SLOT_NUMBER);

        Statistics statistics = new Statistics(name, startTimeSeconds, endTimeSeconds);

        if (startTimeSeconds > periodEndTimeSeconds) {
            return statistics;
        }

        long[] copiedValues;

        lock.lock();

        try {
            copiedValues = Arrays.copyOfRange(values, (int) (startTimeSeconds - periodStartTimeSeconds) * SLOT_LENGTH,
                (int) (endTimeSeconds - periodStartTimeSeconds + 1) * SLOT_LENGTH);
        } finally {
            lock.unlock();
        }

        for (int i = 0; i < copiedValues.length; i += SLOT_LENGTH) {
            statistics.add(
                copiedValues[i + STARTED_COUNT_INDEX],
                copiedValues[i + SUCCEEDED_COUNT_INDEX],
                copiedValues[i + FAILED_COUNT_INDEX],
                copiedValues[i + TOTAL_TIME_INDEX],
                copiedValues[i + MIN_TIME_INDEX],
                copiedValues[i + MAX_TIME_INDEX]);
        }

        return statistics;
    }

}
