package jvm.load;

/**
 * Created by wangz on 17-10-13.
 */
public class Test {
    static String str = "abc";

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.getClass().getClassLoader());
        System.out.println(test.str.getClass().getClassLoader());


    }
}
