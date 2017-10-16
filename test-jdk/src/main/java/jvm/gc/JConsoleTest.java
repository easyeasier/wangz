package jvm.gc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-10-8.
 */

/**
 * vmoptions : -Xms100m -Xmx100m -XX:+UseSerialGC -XX:+HeapDumpOnOutOfMemoryError
 * <p>
 * 1. 不断的新增对象，但不能被回收
 * 2. 使用-XX:+UseG1GC .当大量对象被创建,而且不能被回收的时候,就会分配失败.触发备用fullGC,通过-XX:+PrintGCDetails可观察
 * 3. 使用-XX:+HeapDumpOnOutOfMemoryError可发现OOM时候时存活的对象,以及对象的大小
 * 4. 使用
 */
public class JConsoleTest {

    static class OOMObject {
        public byte[] bytes = new byte[64 * 1024];
    }

    /**
     * 内存监控
     *
     * @param num
     * @throws InterruptedException
     */
    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            long start = System.currentTimeMillis();

            list.add(new OOMObject());

            long end = System.currentTimeMillis();
            if(end - start >= 20){
                System.out.println("stop ========= " + (end-start) + ", count ======= " + i);
                System.out.println();
            }
        }

//        System.gc();
    }

    /**
     * 线程死循环监控
     */
    public static void createBusyThread() {
        Thread thread = new Thread(() -> {
            System.out.println("busy thread is running...");
            while (true) ;
        }, "testBusyThread");

        thread.start();
    }

    /**
     * 测试锁等待线程
     *
     * @param lock
     */
    public static void createLockedThread(final Object lock) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("locked thread is running....");
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "testLockedThread");

        thread.start();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        fillHeap(1000);
//        //通过readline控制线程的启动，监控每个线程启动后的监控情况
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        reader.readLine();
//        createBusyThread();
//        reader.readLine();
//        createLockedThread(new Object());


    }
}
