package sysn;

/**
 * Created by wangz on 17-9-12.
 */
public class LockTest {
    private boolean isLocked = false;
    private Thread lockedThread = null;

    public synchronized void lock() throws InterruptedException {
//        while (isLocked) {
        if(isLocked){
            System.out.println("work_" + Thread.currentThread().getName() + "等待锁. lock = " + isLocked);
            wait();
            System.out.println("work_" + Thread.currentThread().getName() + "获得锁. lock = " + isLocked);
        }

        isLocked = true;
        lockedThread = Thread.currentThread();
        System.out.println("======work_" + Thread.currentThread().getName() + " 加锁======");
    }

    public synchronized void unLock() {
        if (lockedThread != Thread.currentThread()) {
            throw new RuntimeException("解锁不匹配");
        }

        isLocked = false;
        lockedThread = null;
        notify();
        System.out.println("======work_" + Thread.currentThread().getName() + " 解锁======");
    }

    public static class Synchronizer {
        LockTest lock = new LockTest();

        public void doSynchronized() throws InterruptedException {
            this.lock.lock();
            System.out.println("工作_" + Thread.currentThread().getName() + " 进行中.....");
            Thread.sleep(3000);
            System.out.println("工作_" + Thread.currentThread().getName() + " 结束....");
            this.lock.unLock();
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
