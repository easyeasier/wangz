package jvm.gc;

/**
 * Created by wangz on 17-9-25.
 */
public class GcLogTest {
    private static final int _1MB = 1024 * 1024;

    /**
     * vm options : -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCTimeStamps
     * -Xmn10m : 10m分配给新生代
     * -XX:SurvivorRatio=8 ： Eden区和Survivor区比例为8:1:1
     * -XX:PretenureSizeThreshold=4194304 :大于4*1024*1024B时直接进入老年代。但只读ParNew和Serial收集器有效，对Parallel Scavenge无效，所以默认在这无效
     *
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[5 * _1MB];
//        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        GcLogTest.testAllocation();
    }
}
