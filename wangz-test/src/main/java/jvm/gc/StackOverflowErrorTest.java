package jvm.gc;

import lombok.Getter;

/**
 * Created by wangz on 17-9-26.
 */
public class StackOverflowErrorTest {
    @Getter
    int i = 0;

    /**
     * 栈溢出
     * 1.自循环调用
     * 2.发生溢出的栈环境不固定，基本都在 10*1024 个栈深度以上
     */
    public void loop() {
        i++;
        try {
            loop();
        } catch (StackOverflowError e) {
            System.out.println("stack over flow....");
            System.out.println(getI() / 1024);
        }

    }


    public static void main(String[] args) {
        StackOverflowErrorTest test = new StackOverflowErrorTest();
        test.loop();

        System.out.println("end...");
    }
}
