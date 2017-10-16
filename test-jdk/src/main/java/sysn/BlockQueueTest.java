package sysn;

/**
 * Created by wangz on 17-9-14.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * blockqueue 是信号量的一种，通过，共享一个信号量对象进行信息通信
 * 1.当对空队列插入时，唤醒取元素的线程
 * 2.当从满队列取时，唤醒插入的线程
 * 3.当信号量的上线为1时就起到锁的作用了，同一时间只允许一个线程对队列进行操作
 */
public class BlockQueueTest {

    static class BlockQueue<E> {
        private int limit = 10;
        private List<E> list = new ArrayList<>();

        public BlockQueue(int limit) {
            this.limit = limit;
            list = new ArrayList<>(limit);
        }

        /**
         * 插入元素
         * 1.当队列满时，wait挂起
         * 2，当队列空时，唤起取线程
         * 3.当队列不满不空时，任何插入线程可同步进入
         * @param obj
         * @throws InterruptedException
         */
        public synchronized void enqueue(E obj) throws InterruptedException {
            while (list.size() == limit) {
                wait();
            }

            if (list.size() == 0) {
                notifyAll();
            }

            list.add(obj);
        }

        /**
         * 取元素
         * 1，当队列空时，wait挂起
         * 1.当队列满时，唤起插入线程
         * 3.当队列不满不空时，任何取线程可同步进入
         * @throws InterruptedException
         */
        public synchronized E dequeue() throws InterruptedException {
            while (list.size() == 0) {
                wait();
            }

            if (list.size() == limit) {
                notifyAll();
            }

            return list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        BlockQueue<String> queue = new BlockQueue<>(10);
        for (int i = 0; i < 100; i++) {
            int j = i;
            new Thread(i + "") {
                @Override
                public void run() {
                    try {
                        queue.enqueue(j + "");
                        System.out.println("存_" + Thread.currentThread().getName() + " 存 数据 ： " + j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(i + "") {
                @Override
                public void run() {
                    try {
                        String obj = queue.dequeue();
                        System.out.println("取_" + Thread.currentThread().getName() + " 取 数据 ： " + obj);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
