package sysn;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by wangz on 17-9-18.
 */
public class CountDownTest {


    static class WorkUtils {
        static Work createWork() {
            return new Work();
        }
    }

    static class Work {
        private List<Runnable> worklist = Lists.newArrayList();
        private ExecutorService executorService = Executors.newCachedThreadPool();

        public void submit(Runnable work) {
            worklist.add(work);
        }

        /**
         * 1.countDownLatch 就像信号量，
         * 2.await()不会受运行线程时限控制，只是当有个线程在等待的时间内没有完成（超时，异常countDown不-1）后，返回false，反之，返回true
         *
         * @param timeout
         * @throws TimeoutException
         */
        public void await(int timeout) throws TimeoutException {
            CountDownLatch count = new CountDownLatch(worklist.size());
            for (Runnable w : worklist) {
                executorService.execute(() -> {
                    try {
                        w.run();
//                        count.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        count.countDown();
                    }
                });
            }
            try {
                boolean ret = count.await(timeout, TimeUnit.SECONDS);
                if (ret) {
                    System.out.println("all done!");
                } else {
                    System.out.println("timeout");
                }
            } catch (InterruptedException e) {
                throw new TimeoutException();
            }
        }
    }

    public static void main(String[] args) throws TimeoutException {
        Work work = WorkUtils.createWork();
        work.submit(() -> {
            System.out.println("work 1 is running");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        work.submit(() -> {
            System.out.println("work 2 is running");
        });

        work.submit(() -> {
            System.out.println("work 3 is running");
            throw new RuntimeException("test");
        });


//        work.await(2);
        work.await(4);
    }

}
