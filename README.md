# concurrent-world-kata

## ReentrantLock

```
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
```

## Producer - Consumer

```
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
```

## Condition

```
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
```
