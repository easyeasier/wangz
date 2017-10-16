package wz.crawler.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangz on 16-12-24.
 */
public class HttpClientUtil {
    private static HttpClient client = HttpClients.createDefault();
    public static InputStream request(String url) {
        if (url == null) {
            return null;
        }

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response ;
        try {
            response = client.execute(httpGet);
            if (response == null) {
                return null;
            } else {
                return response.getEntity().getContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
