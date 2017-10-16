package wz.test.jdk.sysn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-9-12.
 */
public class FairLock {
    private boolean isLocked = false;
    private Thread lockingThread = null;
    private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

    public void lock() throws InterruptedException {
        QueueObject queueObject = new QueueObject();
        boolean shouldWait = true;
        synchronized (this) {
            waitingThreads.add(queueObject);
        }

        while (shouldWait) {
            synchronized (this) {
                shouldWait = isLocked || waitingThreads.get(0) != queueObject;
                if (!shouldWait) {
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try {
                queueObject.doWait();
            } catch (InterruptedException e) {
                synchronized (this) {
                    waitingThreads.remove(queueObject);
                }
                throw e;
            }
        }
    }

    public synchronized void unlock() {
        if (this.lockingThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException(
                    "Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        if (waitingThreads.size() > 0) {
            waitingThreads.get(0).doNotify();
        }
    }


    public static class QueueObject {
        private boolean isNotified = false;

        public synchronized void doWait() throws InterruptedException {
            while (!isNotified) {
                this.wait();
            }
            this.isNotified = false;
        }

        public synchronized void doNotify() {
            this.isNotified = true;
            this.notify();
        }

        public boolean equals(Object o) {
            return this == o;
        }
    }

    public static class Synchronizer {
        FairLock lock = new FairLock();

        public void doSynchronized() throws InterruptedException {
            this.lock.lock();
            System.out.println("工作_" + Thread.currentThread().getName() + " 进行中.....");
            Thread.sleep(3000);
            System.out.println("工作_" + Thread.currentThread().getName() + " 结束....");
            this.lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Synchronizer work = new Synchronizer();
        for (int i = 0; i < 10; i++) {
            new Thread(i + "") {
                @Override
                public void run() {
                    try {
                        work.doSynchronized();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        while (true) {
        }
    }
}

