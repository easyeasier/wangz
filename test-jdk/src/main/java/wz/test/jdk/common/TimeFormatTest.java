package wz.test.jdk.common;

import java.text.SimpleDateFormat;

/**
 * Created by wangz on 17-7-31.
 */
public class TimeFormatTest {
    public static void main(String[] args) {
        System.out.println(TimeFormatTest.getThemeFromName("名字", System.currentTimeMillis()));
    }

    public static void fun1(){
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(format.format(time));
    }

    public static String getThemeFromName(String name, long time){
        final String format = "%s(%s)";
        return String.format(format, name, timeFormat(time));
    }

    public static String timeFormat(long time){
        final String format = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }
}
