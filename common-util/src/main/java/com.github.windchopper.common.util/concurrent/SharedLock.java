package com.github.windchopper.common.util.concurrent;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class SharedLock implements Lock, Serializable {

    @Serial private static final long serialVersionUID = 7348524842901561057L;

    private static class Synchronizer extends AbstractQueuedSynchronizer {

        @Serial private static final long serialVersionUID = -7880228059275366427L;

        protected int tryAcquireShared(int ignored) {
            return compareAndSetState(0, 1) ? +1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
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

    private final Synchronizer synchronizer = new Synchronizer();

    @Override public void lock() {
        synchronizer.acquireShared(1);
    }

    @Override public void lockInterruptibly() throws InterruptedException {
        synchronizer.acquireSharedInterruptibly(1);
    }

    @Override public boolean tryLock() {
        return synchronizer.tryAcquireShared(1) >= 0;
    }

    @Override public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return synchronizer.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    @Override public void unlock() {
        synchronizer.releaseShared(1);
    }

    @Override public Condition newCondition() {
        return synchronizer.newCondition();
    }

}
