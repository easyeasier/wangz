package wz.test.jdk.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangz on 17-7-7.
 */
public class ScheduledExecutorTest {
    static ScheduledExecutorService pool;
    volatile int number = 1;

    static {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("wz.test.jdk.Test-%d").setDaemon(true).build();
        pool = Executors.newScheduledThreadPool(2, threadFactory);
//        pool = Executors.newScheduledThreadPool(1, threadFactory);
    }

    /**
     * 普通执行
     * 当一个 出错时，后续的不会block，跟Timer不一样
     */
    public void test1() {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("this thread is failed.." + Thread.currentThread().getName());
                throw new RuntimeException("eeee");
            }
        });
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("task is running...." + Thread.currentThread().getName());
            }
        });
    }

    /**
     * 延迟一秒执行
     * 前面线程出错时，后面不阻塞，和定时周期任务不同
     */
    public void test2() {
        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("the server is running...." + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                throw new RuntimeException();
            }
        }, 1, TimeUnit.SECONDS); // (runnable, delay, timeunit)

        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("the server is running...." + Thread.currentThread().getName());
            }
        }, 2, TimeUnit.SECONDS); // (runnable, delay, timeunit)
    }

    /**
     * 定时循环执行，任务间设置delay时长  （单线程排队执行）
     * 在执行完前一个任务后再 计时 + delay后执行下一个
     * 当有一个失败，后续阻塞
     */
    public void test3() {
        long start = System.currentTimeMillis();
        pool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("the task is running..." + " thread = " + Thread.currentThread()
                        .getName() + ", start at " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                throw new RuntimeException();
            }
        }, 2, 1, TimeUnit.SECONDS);
    }


    /**
     * 定时周期任务，固定间隔，
     * 等待前面任务执行，如果前面执行时长超过period，直接执行，如果不满period，补够后执行
     * 前面出错，后续阻塞
     */
    public void test4() {
        long start = System.currentTimeMillis();
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("the task is running..." + " thread = " + Thread.currentThread()
                        .getName() + ", start at " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException();
            }
        }, 2, 3, TimeUnit.SECONDS);
    }

    public void test5() throws InterruptedException {

        //每个2秒钟输出一次number
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("current number is " + number);
            }
        }, 2, 2, TimeUnit.SECONDS);

        //每个1秒钟number+1
        while (true) {
            Thread.sleep(1000);
            number++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        new ScheduledExecutorTest().test3();
        new ScheduledExecutorTest().test4();
        Thread.sleep(50000);
    }

}
