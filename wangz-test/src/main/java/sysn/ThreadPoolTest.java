package sysn;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-9-14.
 */
public class ThreadPoolTest {

    static class BlockQueue<E> {
        private int limit = 10;
        private List<E> list = null;

        BlockQueue() {
            this.list = new ArrayList<>(limit);
        }

        BlockQueue(int limit) {
            this.limit = limit;
            this.list = new ArrayList<>(limit);
        }

        public synchronized void enqueue(E obj) throws InterruptedException {
            while (list.size() == limit) {
                wait();
            }

            if (list.size() == 0) {
                notifyAll();
            }

            list.add(obj);
        }

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

    static class PoolThread extends Thread {
        private BlockQueue<Runnable> queue;
        private boolean isStoped = false;

        PoolThread(BlockQueue<Runnable> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable work = queue.dequeue();
                    work.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean isStoped() {
            return isStoped;
        }
    }

    static class ThreadPool {
        private BlockQueue<Runnable> queue = new BlockQueue<>(50);
        private List<PoolThread> threads;

        public ThreadPool() {
            threads = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                threads.add(new PoolThread(queue));
            }

            for (PoolThread poolThread : threads) {
                poolThread.start();
            }
        }

        public void execute(Runnable task) {
            try {
                queue.enqueue(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).stop();
            }
        }
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        for(int i=0; i<50; i++){
            pool.execute( ()-> {
                System.out.println(Thread.currentThread().getName() + " working...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for(int i=50;i<100;i++){
            pool.execute( ()-> {
                System.out.println(Thread.currentThread().getName() + " working...too");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        while(true);
    }
}
