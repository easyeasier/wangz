package javaApiTest;


import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by wangz on 16-12-19.
 */
public class JavaApiTest {

    @Test
    public void inetAddressTest() throws Exception {

        InetAddress address = InetAddress.getLocalHost();
        byte[] bytes = address.getAddress();
        String host = address.getHostAddress();  //127.0.1.1
        String hostName = address.getHostName(); //wangz

        System.out.println(host);
        System.out.println(hostName);
    }

    @Test
    public void reflectionTest() throws Exception {
        Class clz = JavaApiTest.class;
        System.out.println(clz.getName());  //"javaApiTest.JavaApiTest"

    }

    @Test
    public void linkedHashMapTest() throws Exception {
        HashMap<String,String> hashMap = Maps.newHashMap();
        hashMap.put("2","two");
        hashMap.put("1","one");

        LinkedHashMap<String,String> linkedHashMap = Maps.newLinkedHashMap();
        linkedHashMap.put("2","two");
        linkedHashMap.put("1","one");

        //LinkedHashMap 维护了插入对象的顺序
        System.out.println(hashMap.toString());//{1=one, 2=two}
        System.out.println(linkedHashMap.toString());//{2=two, 1=one}

    }

    @Test
    public void shiftTest() throws Exception{
        int i = 1;
        System.out.println(i >> 2);
    }

    @Test
    public void stringTest() throws Exception{
        String str = "1 and 2 equals 3";
        str.replace("and", "+").replace("equals", "=");
        System.out.println(str);//1 and 2 equals 3. 所以不会替换本对象，而是生成一个新的对象

        String str2 = str.replace("and", "+").replace("equals", "=");
        System.out.println(str2);//1 + 2 = 3
    }

    public static void main(String[] args) throws Exception{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String str = input.readLine().trim();//去掉行首行尾的无效空格“ asdf   方法符  ”

            System.out.println(str);
//            String excludeTrim = str.trim();
//            System.out.println(excludeTrim);
            System.out.println(str.split("\\s+")[0]);   // '\s'代表空站位符 如空格 /r /b tab等
        }

    }
}
