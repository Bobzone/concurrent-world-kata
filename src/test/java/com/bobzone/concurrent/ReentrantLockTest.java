package com.bobzone.concurrent;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    @Test
    public void firstTest() {
    }

    /**
     * Basic usage of ReentrantLock.
     */
    private void acquireLock() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            // do stuff here
        } finally {
            lock.unlock();
        }
    }

    /**
     * Interruptible pattern. Thread will wait until it can enter the guarded block of code.
     * But another thread can interrupt it by calling its interrupt method. InterruptedException is thrown and releases the waiting thread.
     */
    private void acquireLockInterruptibly() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            // do stuff here
        } finally {
            lock.unlock();
        }
    }

    /**
     * Using the tryLock() method results in check if there is a thread already executing inside block of code. If it is it returns false and returns immediately.
     * So the trying-to-enter thread does not block, but is able to do something else at once.
     */
    private void acquireTimedLock() {
        Lock lock = new ReentrantLock();
        if (lock.tryLock()) {
            try {
                // do stuff here
            } finally {
                lock.unlock();
            }
        } else {
            // do other stuff if block is busy
        }
    }

    /**
     * Note we can pass timeout parameter to tryLock() method. In this example thread waits one second. If block is still not available it executes the else block of code.
     */
    private void acquireTimedLockWithTimeout() throws InterruptedException {
        Lock lock = new ReentrantLock();
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                // do stuff here
            } finally {
                lock.unlock();
            }
        } else {
            // do other stuff if block is busy
        }
    }

    /**
     * Without fairness ,the first thread to enter the guarded block of code is chosen randomly.
     * Fairness means that if someone waits first, he will enter the block of code first.
     * Achieving this is costly.
     */
    private void acquireFairLock() {
        Lock lock = new ReentrantLock(true);
        try {
            lock.lock();
            // do stuff here
        } finally {
            lock.unlock();
        }
    }
}