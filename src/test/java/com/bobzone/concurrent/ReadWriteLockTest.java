package com.bobzone.concurrent;

import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteLockTest {

    @Test
    public void test() {

    }

    /**
     * Exclusive writes and exclusive reads. ReadWriteLock is an interface with two methods. readlock() and writeLock()
     * Read operations are free, write operations are exlusive of other writes and reads.
     * Allows superior throughput, when many reads and fewer writes.
     */
    public void body() {


        ReadWriteLock lock = new ReentrantReadWriteLock();
        final Lock readLock = lock.readLock();
        final Lock writeLock = lock.writeLock();

        Map<Long, User> cache = new HashMap<>();

        try {
            writeLock.lock();
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }

    }
}
