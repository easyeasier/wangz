package wz.test.jdk.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-9-26.
 */
public class OutOfMemoryErrorTest {
    private static final int _1M = 1024 * 1024;

    /**
     * 堆 OOM
     * vm options = -Xms20m -Xmx20m -XX:+PrintGCDetails
     * 观察GC日志
     */
    public void heapOOMFun() {
        try{
            byte[] obj_1 = new byte[_1M * 4];
            byte[] obj_2 = new byte[_1M * 4];
            byte[] obj_3 = new byte[_1M * 4];
            byte[] obj_4 = new byte[_1M * 4];
            byte[] obj_5 = new byte[_1M * 4];
            byte[] obj_6 = new byte[_1M * 4];

            System.out.println("allocation success....");
        }catch (OutOfMemoryError e){
            System.out.println("OutOfMemoryError...");
        }
    }

    /**
     * 方法区 常量池OOM
     * 在java8以前可设置参数 vm options = -XX:PermSize=10M -XX:MaxPermSize=10M
     * java8之后移除了永久代，直接放到堆内存中
     *
     * 1.设置 永久代 最大内存区 10M
     * 2.放到list中，不让gc回收
     */
    public void ConstantsPoolOOM(){
        List<String> list = new ArrayList<>();
        int i=0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }


    public static void main(String[] args) {
        OutOfMemoryErrorTest test = new OutOfMemoryErrorTest();
//        test.heapOOMFun();
        test.ConstantsPoolOOM();


    }
}
