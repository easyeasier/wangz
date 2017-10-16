package http;

import com.google.common.base.Strings;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangz on 16-12-25.
 */
public class HttpClientSimpleTest {
    public static String sendGet(String url) {
        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
//
//        Header[] headers = httpGet.getAllHeaders();   //null
//        for(Header header : headers){
//            System.out.println(header.getName()+"====="+header.getValue());
//        }
        try {
            HttpResponse response = client.execute(httpGet);
            InputStream is = response.getEntity().getContent();
            if(is == null){
                return null;
            }


            byte[] bytes = new byte[1024];
            ByteArrayBuffer buffer = new ByteArrayBuffer(4096);
            while(is.read(bytes) != -1){
                buffer.append(bytes, 0, 1024);
            }

            String result = new String(buffer.toByteArray(), "utf-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com";
        System.out.println(HttpClientSimpleTest.sendGet(url));
    }
}
