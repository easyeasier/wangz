package wz.crawler;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import wz.crawler.model.HtmlPage;

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
        String url = "wz.test.jdk.http://www.hao123.com";
        HtmlPage htmlPage = new Main(url).getPage();
        Document html = Jsoup.parse(htmlPage.getPageHtml());
        Elements elements = html.getAllElements();
        System.out.println(elements);

    }
}
