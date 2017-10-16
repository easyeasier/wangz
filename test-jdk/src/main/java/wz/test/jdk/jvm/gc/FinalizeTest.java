package wz.test.jdk.jvm.gc;

/**
 * Created by wangz on 17-9-27.
 */

/**
 * 1.第一次gc时，对象不能到达则第一次标记
 * 2.如果覆盖finalize()方法时，第一次gc会被执行，第二次gc不再执行，第二次标记为 待回收区域
 * 3.如果没有覆盖finalize()，在哪次gc都不会执行。
 * 4.第一次标记之后，还可以恢复，不会被直接回收。两次标记后会被回收
 */
public class FinalizeTest {

    public static FinalizeTest  INSTANCE = null;

    public void isAlive(){
        System.out.println(" i am still alive....");
    }

    /**
     * 如果不覆盖，不会被执行
     * 如果覆盖，第一次gc会调用执行，第二次不再执行
     * @param args
     * @throws InterruptedException
     */
//    @Override
//    public void finalize() throws Throwable {
//        super.finalize();
//        System.out.println("finalize method executed...");

//        FinalizeTest.INSTANCE = this;
//    }

    public static void main(String[] args) throws InterruptedException {
        INSTANCE = new FinalizeTest();

        //gc root不到达INSTANCE
        INSTANCE = null;
        //第一次gc
        System.gc();
        //等待执行finalize(),  finalize()优先级比较低
        Thread.sleep(1000);
        if(INSTANCE != null){
            INSTANCE.isAlive();
        }else{
            System.out.println("i am dead...");
        }

        //gc root不到达INSTANCE
        INSTANCE = null;
        //第二次gc，不在调用被覆盖的finalize
        System.gc();
        Thread.sleep(1000);
        if(INSTANCE != null){
            INSTANCE.isAlive();
        }else{
            System.out.println("i am dead...");
        }
    }
}
