package com.github.monaboiste.transactional;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class LockTest {


    static int c1 = 0;
    @Test
    void without_lock() throws InterruptedException {
        // race condition - c1 may be lesser than 10000

        Thread[] threads = new Thread[100000];
        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(() -> {
                c1++;
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Counter with no locking: " + c1);
    }

    static int c2 = 0;
    @Test
    void with_pesimistic_lock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread[] threads = new Thread[100000];
        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(() -> {
                try {
                    lock.tryLock(1, TimeUnit.SECONDS);
                   c2++;
               } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                   lock.unlock();
               }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Counter with lock (pessimistic locking): " + c1);
    }

    static AtomicInteger c3 = new AtomicInteger(0);
    @Test
    void with_optimistic_lock() throws InterruptedException {
        int version = 0;

        Thread[] threads = new Thread[100000];
        for (int i = 0; i < 100000; i++) {
            threads[i] = new Thread(() -> {
                int localC3;

                do {
                    localC3 = c3.get();
                } while (c3.compareAndSet(localC3, localC3 + 1));
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Counter with lock (optimistic locking): " + c1);
    }
}
