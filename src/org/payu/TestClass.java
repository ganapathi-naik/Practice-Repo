package org.payu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestClass {
    public static void main(String[] args) {
        Lock resource1 = new ReentrantLock();
        Lock resource2 = new ReentrantLock();
        Thread t1 = new Deadlock("T1", resource1, resource2, 1);
        Thread t2 = new Deadlock("T2", resource1, resource2, 2);
        t1.start();
        t2.start();
    }
}

class Deadlock extends Thread {

    private final Lock resource1;
    private final Lock resource2;
    private final int resourceAcquisitionOrder;

    Deadlock(String name, Lock resource1, Lock resource2, int resourceAcquisitionOrder) {
        super(name);
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.resourceAcquisitionOrder = resourceAcquisitionOrder;
    }

    @Override
    public void run() {
        boolean acquiredBothLocks = false;
        while (!acquiredBothLocks)
            if (resourceAcquisitionOrder == 1) {
                if (resource1.tryLock()) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource-1");
                    if (resource2.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + " acquired resource-2");
                        acquiredBothLocks = true;
                        System.out.println(Thread.currentThread().getName() + " released resource-1");
                        System.out.println(Thread.currentThread().getName() + " released resource-2");
                        resource1.unlock();
                        resource2.unlock();
                    } else {
                        System.out.println(Thread.currentThread().getName() + " released resource-1");
                        resource1.unlock();
                    }
                }
            } else if (resourceAcquisitionOrder == 2) {
                if (resource2.tryLock()) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource-2");
                    if (resource1.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + " acquired resource-1");
                        acquiredBothLocks = true;
                        System.out.println(Thread.currentThread().getName() + " released resource-2");
                        System.out.println(Thread.currentThread().getName() + " released resource-1");
                        resource2.unlock();
                        resource1.unlock();
                    } else {
                        System.out.println(Thread.currentThread().getName() + " released resource-2");
                        resource2.unlock();
                    }
                }
            }
    }
}