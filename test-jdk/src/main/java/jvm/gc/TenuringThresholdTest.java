package jvm.gc;

/**
 * Created by wangz on 17-9-29.
 */
public class TenuringThresholdTest {
    private static final int _1M = 1024 * 1024;

    /**
     * -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:+PrintTenuringDistribution -XX:SurvivorRatio=8  -XX:MaxTenuringThreshold=1 -XX:+UseParNewGC
     */
    public static void testTenuringThreshold() throws InterruptedException {
        byte[] allocation1, allocation2, allocation3;
        allocation2 = new byte[4 * _1M];
        while(true){
            System.out.println("=========start");
            System.out.println();
            allocation1 = new byte[_1M/4];
            allocation1 = null;
            System.out.println("-1-");
            allocation3 = new byte[4 * _1M];
            System.out.println("-2-");
            allocation3 = null;
            allocation3 = new byte[4 * _1M];
            System.out.println("-3-");
            Thread.sleep(1000);
            System.out.println();
            System.out.println("=========end");
            System.out.println();
        }

    }

    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3,allocation4,allocation5,allocation6,allocation7;
        allocation1 = new byte[2 *_1M];
        allocation2 = new byte[2 *_1M];
        allocation3 = new byte[2 * _1M];
        allocation1 = null;

        allocation4 = new byte[2 *_1M];
        allocation5 = new byte[2 *_1M];
        allocation6 = new byte[2 * _1M];
        allocation4 = null;

        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1M];
    }

    public static void main(String[] args) throws InterruptedException {
        TenuringThresholdTest.testTenuringThreshold();
//        TenuringThresholdTest.testHandlePromotion();
    }
}
