package wz.test.jdk.http;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangz on 16-12-25.
 */
public class HttpClientTest {
    public static void main(String[] args) {

        try {
            test3();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }






    /**
     * 测试1： 构建复杂uri，这种方式会很方便的设置多个参数；
     *
     * HttpClients类是client的具体一个实现类；
     *
     * URIBuilder包含：协议，主机名，端口（可选），资源路径，和多个参数（可选）
     *
     */
    private static void test1() {

        CloseableHttpClient client = HttpClients.createDefault();

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("wz/test/jdk/http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")//这里可以设置多个参数
                    .setParameter("", "")
                    .setParameter("", "")
                    .setParameter("", "")
                    .build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        //http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo
        HttpGet get = new HttpGet(uri);
        try {
            CloseableHttpResponse response = client.execute(get);

            if(response.getStatusLine().getStatusCode()==200){


                System.out.println(EntityUtils.toString(response.getEntity()));

                //以下这种方式读取流也可以，只不过用EntityUtils会更方便
                /*InputStream is = response.getEntity().getContent();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len=-1;
                while((len = is.read(buffer))!=-1){
                    os.write(buffer,0,len);
                }
                os.close();
                is.close();
                System.out.println(os.size()+new String(os.toByteArray(),"utf-8"));*/
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 为uri进行加密，并进行表单提交；
     *
     * 许多应用需要模拟提交的HTML表单的处理，例如，在
     为了登录到Web应用程序或提交的输入数据。 HttpClient提供的实体类
     UrlEncodedFormEntity可以实现该过程；
     */
    private static void test2(){

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httppost = new HttpPost("wz.test.jdk.http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");

        //构建请求参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("mobileCode", "110"));
        list.add(new BasicNameValuePair("userID", ""));

        //构建url加密实体，并以utf-8方式进行加密；
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        httppost.setEntity(entity);

        try {
            CloseableHttpResponse response = client.execute(httppost);

            if(response.getStatusLine().getStatusCode()==200){

                //org.apache.wz.test.jdk.http.util.EntityUtils类可以快速处理服务器返回实体对象
                System.out.println(EntityUtils.toString(response.getEntity()));

            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 以回调方式处理返回结果
     *
     *  处理响应的最简单和最方便的方法是通过使用ResponseHandler的
     接口。用户不必担心连接管理的问题。当使用一个
     ResponseHandler的时候，无论是否请求执行成功或导致异常，HttpClient将会自动释放连接。
     */
    private static void test3(){

        CloseableHttpClient client = HttpClients.createDefault();

        //==============================================================
        ResponseHandler<Object> handler = new ResponseHandler<Object>() {
            @Override
            public Object handleResponse(final HttpResponse response) throws IOException {

                HttpEntity entity = response.getEntity();

                if (entity == null) {
                    throw new ClientProtocolException("返回结果为空");
                }

                if(response.getStatusLine().getStatusCode()==200){

                    //获取返回结果的字符集 如：utf-8  gbk，并以这种字符集来读取流信息
//                    ContentType contentType = ContentType.getOrDefault(entity);
//                    Charset charset = contentType.getCharset();

                    InputStreamReader reader = new InputStreamReader(entity.getContent(), Consts.UTF_8);
                    BufferedReader br = new BufferedReader(reader);
                    StringBuffer sb = new StringBuffer();
                    char[] buffer = new char[1024];
                    while (br.read(buffer)!=-1) {
                        sb.append(new String(buffer));
                    }

                    return sb.toString();
                }

                return null;

            }
        };
        //===================================================================

        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("https")
//                    .setHost("webservice.webxml.com.cn")
                    .setHost("www.baidu.com.cn")
//                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
//                    .setParameter("", "")
//                    .setParameter("", "")
//                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet(uri);

        try {

            //handler回调
            Object obj = client.execute(get, handler);

            System.out.println("返回结果："+obj);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     *  设置长连接策略，根据服务器的约束或者客户端的约束来设置长连接的时长；
     */
    private static void test4() {

        ConnectionKeepAliveStrategy strategy = new DefaultConnectionKeepAliveStrategy() {

            /**
             * 服务器端配置（以tomcat为例）：keepAliveTimeout=60000，表示在60s内内，服务器会一直保持连接状态。
             * 也就是说，如果客户端一直请求服务器，且间隔未超过60s，则该连接将一直保持，如果60s内未请求，则超时。
             *
             * getKeepAliveDuration返回超时时间；
             */
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {


                //如果服务器指定了超时时间，则以服务器的超时时间为准
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            System.out.println("服务器指定的超时时间：" + value + " 秒");
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                            ignore.printStackTrace();
                        }
                    }
                }


                long keepAlive = super.getKeepAliveDuration(response, context);

                // 如果服务器未指定超时时间，则客户端默认30s超时
                if (keepAlive == -1) {
                    keepAlive = 30 * 1000;
                }

                return keepAlive;

                /*如果访问webservice.webxml.com.cn主机，则超时时间5秒，其他主机超时时间30秒
                HttpHost address = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                if ("webservice.webxml.com.cn".equalsIgnoreCase(address.getHostName())) {
                    keepAlive =  10 * 1000;
                } else {
                    keepAlive =  30 * 1000;
                }*/


            }
        };



        CloseableHttpClient client = HttpClients.custom().setKeepAliveStrategy(strategy).build();

        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("wz/test/jdk/http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        try {

            CloseableHttpResponse response =  client.execute(post);
            if(response.getStatusLine().getStatusCode()==200){
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("返回的结果====="+result);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    /**
     * 构建复杂请求体
     *
     * RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播（来自官方文档）；
     *
     */
    private void test5(){


        CloseableHttpClient client = HttpClients.createDefault();

        //构建请求配置
        RequestConfig config = RequestConfig.
                custom().
                setSocketTimeout(1000*10).
                setConnectTimeout(1000*10).
                build();
        //==============================================================
        ResponseHandler<Object> handler = new ResponseHandler<Object>() {//回调
            @Override
            public Object handleResponse(final HttpResponse response) throws IOException {

                HttpEntity entity = response.getEntity();

                if (entity == null) {
                    throw new ClientProtocolException( "返回结果为空");
                }

                if(response.getStatusLine().getStatusCode()==200){
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();

                    InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
                    BufferedReader br = new BufferedReader(reader);
                    StringBuffer sb = new StringBuffer();
                    char[] buffer = new char[1024];
                    while (br.read(buffer)!=-1) {
                        sb.append(new String(buffer));
                    }

                    return sb.toString();
                }

                return null;

            }
        };
        //===================================================================

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("wz/test/jdk/http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .setParameter("", "")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        post.setConfig(config);//设置请求配置

        try {
            Object obj = client.execute(post, handler);

            System.out.println("返回结果："+obj);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    /**
     *  异常恢复机制：
     *
     *  HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
     *  HttpRequestRetryHandler接口须要用户自己实现；
     *
     */
    private static  void test6(){

        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

            /**
             * exception异常信息；
             * executionCount：重连次数；
             * context：上下文
             */
            @Override
            public boolean retryRequest(IOException exception, int executionCount,HttpContext context) {

                System.out.println("重连接次数："+executionCount);

                if (executionCount >= 5) {//如果连接次数超过5次，就不进行重复连接
                    return false;
                }
                if (exception instanceof InterruptedIOException) {//io操作中断
                    return false;
                }
                if (exception instanceof UnknownHostException) {//未找到主机
                    // Unknown address
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {//连接超时
                    return true;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);

                HttpRequest request = clientContext.getRequest();

                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);

                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };


        CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

        RequestConfig config = RequestConfig.
                custom().
                setSocketTimeout(1000*10).
                setConnectTimeout(1000*10).
                build();

        HttpPost post = new HttpPost("wz.test.jdk.http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");

        post.setConfig(config);

        try {
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();

            InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            char[] buffer = new char[1024];
            while (br.read(buffer) != -1) {
                sb.append(new String(buffer));
            }

            System.out.println("返回的结果："+sb.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *  HTTP请求过滤器，在执行请求之前拦截HttpRequest 和 HttpContext；
     */
    private static void test7() throws ClientProtocolException, IOException{

        HttpRequestInterceptor requestInterceptor = new HttpRequestInterceptor() {
            /**
             * 处理请求：
             * 如果是客户端：这个处理在发送请求之前执行；
             * 如果是服务器：这个处理在接收到请求体之前执行；
             */
            public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
                AtomicInteger count = (AtomicInteger) context.getAttribute("count");
                request.addHeader("count", Integer.toString(count.getAndIncrement()));
            }
        };


        CloseableHttpClient httpclient = HttpClients.
                custom().
                addInterceptorLast(requestInterceptor).
                build();


        AtomicInteger count = new AtomicInteger(1);
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("count", count);
        HttpGet httpget = new HttpGet("wz.test.jdk.http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");

        for (int i = 0; i < 10; i++) {
            CloseableHttpResponse response = httpclient.execute(httpget,context);
            try {

                HttpEntity entity = response.getEntity();
//              System.out.println(EntityUtils.toString(entity));

            } finally {
                response.close();
            }
        }
    }




    /**
     *
     *  httpclient会自动处理重定向；
     *
     *  301 永久重定向,告诉客户端以后应从新地址访问.
     302 作为HTTP1.0的标准,以前叫做Moved Temporarily ,现在叫Found. 现在使用只是为了兼容性的  处理,包括PHP的默认Location重定向用的也是302.
     但是HTTP 1.1 有303 和307作为详细的补充,其实是对302的细化
     303：对于POST请求，它表示请求已经被处理，客户端可以接着使用GET方法去请求Location里的URI。
     307：对于POST请求，表示请求还没有被处理，客户端应该向Location里的URI重新发起POST请求。
     */
    private static void test8() throws ClientProtocolException, IOException,
            URISyntaxException {

        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();

        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("wz.test.jdk.http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try {
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
        } finally {
            response.close();
        }
    }




}
