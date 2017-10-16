package sysn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by wangz on 17-9-18.
 */
public class SemaphoreTest {
    /**
     * 信号量的一般用法 Semaphore:
     * 1.跟共享队列一样，满设置个信号量值
     * 2.acquire()请求一个信号量许可，没有的话阻塞
     * 3.可以随意release。但会打乱信号量的值
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService  executors = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for(int i=0;i<20;i++){
            executors.execute(()->{
                try {
//                    semaphore.release();
                    semaphore.acquire();
                    System.out.println("Thread_" + Thread.currentThread().getName() + "正在执行...");
//                    Thread.sleep(2000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(2000);
        System.out.println(semaphore.availablePermits());
    }
}
