package com.bobzone.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ProducerConsumerTest {

    @Test
    public void test() {
    }


    /**
     * Two classes are organised around synchronized block. If the buffer for producer is full then the producing thread has to wait.
     * And to be awaken the consumer thread has to call notify() or notifyAll() on the lock object.
     * Same goes for the other side. If consuming thread has nothing to consume it has to wait.
     * If no thread calls notify() or notifyAll() then there is no chance that threads will be awakened.
     */
    Object lock = new Object();

    class Producer {
        public void produce() {
            synchronized (lock) {
                while (isFull(buffer))
                    lock.wait();
                buffer[count++] = 1;
                lock.notifyAll();
            }
        }
    }


    class Consumer {
        public void consume() {
            synchronized (lock) {
                while (isEmpty(buffer))
                    lock.wait();
                buffer[--count] = 0;
                lock.notifyAll();
            }
        }
    }


    /**
     * If the buffer is full we need to put the producer thread in a wait state. And on consuming side consumer has to notify the producer. How can this be done?
     * We use Condition object.
     * We use .await(); on Producer side if queue is full and .signal() on consumer when consumer is done.
     */
    ReentrantLock lock = new ReentrantLock();
    Condition notFull = lock.newCondition();
    Condition notEmpty = lock.newCondition();

    class BetterProducer {
        public void produce() {
            try {
                lock.lock();
                while (isFull(buffer))
                    notFull.await();
                buffer[count++] = 1;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    class BetterConsumer {
        public void consume() {
            try {
                lock.lock();
                while (isEmpty(buffer))
                    notEmpty.await();
                buffer[--count] = 0;
                notFull.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
