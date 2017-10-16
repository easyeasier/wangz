package wz.test.jdk.sysn;

import lombok.Getter;

/**
 * Created by wangz on 17-9-13.
 */
public class ReadWriteLockTest {

    /**
     * 读写锁
     * 1.只有没有正在写的线程和没有请求写的线程时，才可以读
     * 2.只有没有写的线程和没有在读的线程时，才可以写
     * 3.当各被阻塞线程被唤醒后，只有一个线程能获得对象锁，所有用while,其他线程不符合条件再次阻塞
     * 4.当同时有读线程和写线程被 唤醒，写线程将获得对象锁，因为。writeRequests > 0；读线程将再次被阻塞
     */

    /**
     * notifyAll的必要性：
     * 1.如果有待读和待写线程 。 当待读被唤醒，因为writeRequest > 0，会被再次阻塞，程序无法进行下去
     * 2.当写线程执行完，如果没有待写，能唤起所有待读执行，而不是只唤起一个。
     */
    public static class ReadWriteLock {

        @Getter
        private int readers = 0;
        @Getter
        private int writers = 0;
        @Getter
        private int writeRequests = 0;

        /**
         * 读锁
         * 1.当没人写并且没有请求写的时候可以读
         * 2.如果有正在写或者正在请求写，阻塞。放弃对象锁
         *
         * @throws InterruptedException
         */
        public synchronized void lockRead() throws InterruptedException {
            while (writers > 0 || writeRequests > 0) {
                wait();
            }

            readers++;
            //预期 ：writers = 0; writeRequests = 0
//            System.out.println(Thread.currentThread().getName() + " 获得读锁！ reader=" + readers + ",writers=" + writers + "," +
//                    "writerRequests=" + writeRequests);
        }

        /**
         * 关闭写锁
         * 1.读者-1
         * 2.唤醒所有被阻塞的线程
         */
        public synchronized void unlockRead() {
            readers--;
            notify();
        }

        /**
         * 获取写入锁
         * 1.首先要将写请求+1；
         * 2.只有没有在写的线程并且没有在读的线程时，可以写入
         * 3.如果不符合条件，则，写阻塞，放弃对象锁
         * 4.当符合写的条件后，写请求-1，写线程+1，进入写程序
         *
         * @throws InterruptedException
         */
        public synchronized void lockWrite() throws InterruptedException {
            writeRequests++;
            while (readers > 0 || writers > 0) {
                wait();
            }


            writers++;
            writeRequests--;
            //预期 ：readers = 0; writers = 1
            System.out.println(Thread.currentThread().getName() + " 获得写锁！ reader=" + readers + ",writers=" + writers
                    + "," + "writerRequests=" + writeRequests);
        }

        /**
         * 解写入锁
         * 1.写完毕后，写入者-1；
         * 2.唤醒其他阻塞线程
         * 3.放弃写入锁
         */
        public synchronized void unlockWrite() {
            writers--;
            notifyAll();
        }
    }


    public static class Work {
        ReadWriteLock lock = new ReadWriteLock();

        public void read() throws InterruptedException {
            lock.lockRead();
//            System.out.println(Thread.currentThread().getName() + "执行读....");
            lock.unlockRead();
        }

        public void write() throws InterruptedException {
            lock.lockWrite();
//            System.out.println(Thread.currentThread().getName() + "执行写....");
            lock.unlockWrite();
        }
    }

    public static void main(String[] args) {
        Work work = new Work();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                new Thread(i + "") {
                    @Override
                    public void run() {
                        try {
                            work.write();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                new Thread(i + "") {
                    @Override
                    public void run() {
                        try {
                            work.read();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

        }
    }
}
