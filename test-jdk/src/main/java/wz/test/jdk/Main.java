package wz.test.jdk;

import com.google.common.collect.Maps;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wangz on 16-12-20.
 */
public class Main {

    public static void main(String[] args) throws IOException {

           Map<String,String> map1 = Maps.newHashMap();
           map1.put("1","a");

           Map<String,String> map2 = Maps.newHashMap(map1);
        System.out.println(map2);
    }



    public static int count(int[] arr, int key) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], 0);
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            int left = iterator.next().getKey();
            if (map.get(key + left) != null) {
                map.put(left,1);
            }
        }

        int count = 0;
        iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            int value = iterator.next().getValue();
            if(value == 1){
                count++;
            }
        }
        return count;


    }

    public static void fun() {
        String content = "file output stream test : append";
        String path = Main.class.getResource("").getPath() + "doc/fone_result";
        System.out.println("path :" + path);
        File file = new File(path);
        if (file.exists()) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file, true);
                fos.write(content.getBytes());
                fos.flush();
                System.out.println("写入文件....");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("file is not exist");
        }
    }

    public static void fun2() throws IOException {
        String path = Main.class.getResource("/").getPath() + "doc/fone_result";
        Path filePath = Paths.get(path);
        BufferedReader r = Files.newBufferedReader(filePath);
        String temp = null;
        while ((temp = r.readLine()) != null) {
            System.out.println(temp);
        }
    }

    public static void fun3() throws IOException {
        String path = Main.class.getResource("/").getPath() + "doc/fone_result";
        Path filePath = Paths.get(path);
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void fun4() throws IOException {
        String path = Main.class.getResource("/").getPath() + "doc/fone_result";
        Path filePath = Paths.get(path);
        byte[] bytes = Files.readAllBytes(filePath);
        String content = new String(bytes, "iso-8859-1");
        System.out.println(content);
    }

    public static void fun5() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);
            System.out.println(address.getHostName());
            System.out.println(address.getAddress());

//            String address = "www.baidu.com";
            String host = "www.google.com";
            InetAddress[] addresses = InetAddress.getAllByName(host);
            for (InetAddress a : addresses) {
                System.out.println(a);
                System.out.println(a.getHostName());
                System.out.println(a.getAddress());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
