package wangz;

import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wangz.model.HtmlPage;
import wangz.utils.StreamUtils;

import javax.xml.ws.spi.http.HttpContext;
import java.io.*;

/**
 * Created by wangz on 16-12-24.
 */
@Data
public class Main {
    HtmlPage page ;
    public Main(String url){
        this.page = HtmlPage.instance(url);
    }

    public static void main(String[] args) {
        String url = "http://www.hao123.com";
        HtmlPage htmlPage = new Main(url).getPage();
        Document html = Jsoup.parse(htmlPage.getPageHtml());
        Elements elements = html.getAllElements();
        System.out.println(elements);

    }
}
