package wz.crawler.model;

import lombok.Data;
import wz.crawler.utils.HttpClientUtil;
import wz.crawler.utils.StreamUtils;

/**
 * Created by wangz on 16-12-24.
 */
@Data
public class HtmlPage {
    private String url;
    private String pageHtml;

    private HtmlPage(String url) {
        this.url = url;
        this.pageHtml = StreamUtils.parseStreamToStr(HttpClientUtil.request(url));
    }

    public static HtmlPage instance(String url) {
        return new HtmlPage(url);
    }
}
