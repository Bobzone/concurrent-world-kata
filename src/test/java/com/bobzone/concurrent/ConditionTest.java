package com.bobzone.concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ConditionTest {

    @Test
    public void test() {
    }

    /**
     * Fair threads generate fair conditions.
     */
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void methodBody() {
        /**
         * Regular await.
         */
        condition.await();

        /**
         * Three awaits with timeout that can be expressed with time units.
         */
        condition.await(1, TimeUnit.SECONDS);
        condition.awaitNanos(1000);
        condition.awaitUntil(new Date());

        /**
         * Prevents the interruption of thread.
         */
        condition.awaitUninterruptibly();
    }


}
