package com.bobzone.concurrent;

import org.junit.Test;

import java.util.concurrent.Semaphore;

class SemaphoreTest {

    @Test
    public void test() throws InterruptedException {

        // Semaphore allows for more than one thread inside the block of code.
        // Only 5 threads will access the block of code in this example.
        Semaphore semaphore = new Semaphore(5);

        try {
            semaphore.acquire();
            // do something
        } finally {
            semaphore.release();
        }


        // We can also ask for N permits.
        try {
            semaphore.acquire(2);
            // do something
        } finally {
            semaphore.release(2);
        }


        // If you call interrupt method on a thread that is blocked on acquire call, this Thread will throw InterruptedException at once.
        // If you dont want that you can use acquireUninterruptibly.
        Semaphore semaphore = new Semaphore(5);

        try {
            semaphore.acquireUninterruptibly();
            // do something
        } finally {
            semaphore.release();
        }


        // You can also do the same if else construction with Semaphore!
        Semaphore semaphore = new Semaphore(5);

        try {
            if (semaphore.tryAcquire()) {
                // do something
            } else {
                // do something else
            }
        } finally {
            semaphore.release();
        }

    }
}
