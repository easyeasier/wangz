package executor;

/**
 * Created by wangz on 17-7-9.
 */
public class SynchronizedTest {
    private String field1 = "0";

    public synchronized void test1() throws InterruptedException {
        Thread.sleep(2000);
        this.field1 = "1";
        System.out.println("test1 change the field");
    }

    /**
     * test，test2分别有两个线程执行
     * synchronized 获取的时对象锁，当test1获取到对象锁时，test2就得等待test1
     * 而如果test2没有synchronized修饰，则不需要获取对象锁，不需要等待test1
     *
     * ：所以，当setter 同步时，getter需要 synchronized 同步，才会起到效果
     */
//    public  void test2() {
    public synchronized  void test2(){
        this.field1 = "2";
        System.out.println("test2 change the field");
    }

    public String getField1(){
        return this.field1;
    }

    /**
     * synchronized(this) 同样是获取的对象锁，只是起始位置在synchronized(this)开始
     * 其他效果 同修饰方法相同
     * @throws InterruptedException
     */
    public void test3() throws InterruptedException {
        System.out.println("test3 is starting");
        Thread.sleep(2000);

        synchronized(this){
            this.field1 = "3";
        }
    }

    public void test4(){
        System.out.println("test4 is starting");
//        synchronized(this){
            this.field1 = "4";
//        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest test = new SynchronizedTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.test3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.test4();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("now the field is " + test.getField1());

        Thread.sleep(3000);
        System.out.println("now the field is " + test.getField1());
    }
}
