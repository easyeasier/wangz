package sysn;

/**
 * Created by wangz on 17-9-11.
 */
public class WaitAndNotifyTest {
    public void waitFun() {
        synchronized (this) {
            System.out.println("进入wait方法....");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("退出wait方法...");
        }
    }

    public void notifyFun() {
        synchronized (this) {
            System.out.println("进入notify方法...");
            this.notify();
            System.out.println("退出notify方法...");
        }
    }

    public static void main(String[] args) {
        WaitAndNotifyTest test1 = new WaitAndNotifyTest();
        WaitAndNotifyTest test2 = new WaitAndNotifyTest();
//        for (int i = 0; i < 10; i++) {
//            int j = i;
            new Thread() {
                @Override
                public void run() {
                    synchronized (test1) {
//                        System.out.println("进入wait_" + j + "...");
                        System.out.println("进入wait...");

                        try {
                            test1.wait();
                            test2.notify();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.out.println("退出wait_" + j + "...");
                        System.out.println("退出wait...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
//        }

        new Thread() {
            @Override
            public void run() {
                synchronized (test2) {
                    System.out.println("进入notify...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        test1.notify();
                        test2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("退出notify...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        while (true) {
        }
    }
}
