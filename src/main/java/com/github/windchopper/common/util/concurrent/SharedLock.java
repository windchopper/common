package com.github.windchopper.common.util.concurrent;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SharedLock implements Lock, Serializable {

    private static final long serialVersionUID = 7348524842901561057L;

    private static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = -7880228059275366427L;

        protected int tryAcquireShared(int ignore) {
            return compareAndSetState(0, 1) ? +1 : -1;
        }

        protected boolean tryReleaseShared(int ignore) {
            if (compareAndSetState(1, 0)) {
                return true;
            } else {
                throw new IllegalMonitorStateException("Not locked");
            }
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    private final Sync sync = new Sync();

    @Override public void lock() {
        sync.acquireShared(1);
    }

    @Override public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override public boolean tryLock(long timeout, @Nonnull TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    @Override public void unlock() {
        sync.releaseShared(1);
    }

    @Override public @Nonnull Condition newCondition() {
        return sync.newCondition();
    }

}
