package wz.test.jdk.executor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangz on 17-7-7.
 */
public class TimerTest {
    Timer timer = new Timer();


    /**
     * 循环执行
     * 设置间隔时间
     */
    public void test1() {
        long start = System.currentTimeMillis();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1 is running....Thread.id= " + Thread.currentThread().getId() + ", start at " +
                        "" + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(2000);     //单线程执行，执行完当前任务后才会执行下一个
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(task, 1000, 1000); //（task,delay,period） 延迟1秒后执行，间隔1秒循环
    }

    /**
     * 执行两个任务
     * 但也是单线程先后执行
     */
    public void test2() {
        long start = System.currentTimeMillis();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1 is running....start at clock = " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(3000); //执行1秒中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2 is running... start at clock = " + (System.currentTimeMillis() - start));
            }
        };


        //1秒后执行
        timer.schedule(task1, 1000);
        //3秒后执行
        timer.schedule(task2, 1000);
    }


    /**
     * 定时执行两个任务
     * 当第一个任务跑出异常时，后面任务不会被执行
     */
    public void test3(){
        long start = System.currentTimeMillis();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1 is running....start at clock = " + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(2000); //执行1秒中

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw  new RuntimeException("stop task1");
            }
        };


        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2 is running... start at clock = " + (System.currentTimeMillis() - start));
            }
        };
        //1秒后执行
        timer.schedule(task1, 1000);
        //2秒后执行
        timer.schedule(task2, 2000);
    }
    public static void main(String[] args) {
        new TimerTest().test3();
    }
}
