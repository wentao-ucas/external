package cn.cncc.caos.external.provider.cloud.service.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * httpclient配置类
 */
@Service("HttpClientService")
@Slf4j
public class HttpClientService {

    /** 建立连接超时时间(ms) */
    @Value("${httpClientConfig.connectTimeout}")
    private int connectTimeout;

    /** socket超时时间(ms) */
    @Value("${httpClientConfig.socketTimeout}")
    private int socketTimeout;

    /** 获取连接超时时间(ms) */
    @Value("${httpClientConfig.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    /** IO线程池中线程的数量 */
    @Value("${httpClientConfig.ioThreadCount}")
    private int ioThreadCount;

    /** 并行连接的最大数量 */
    @Value("${httpClientConfig.maxTotal}")
    private int maxTotal;

    /** 同一个ip的最大连接数量 */
    @Value("${httpClientConfig.maxPerRoute}")
    private int maxPerRoute;

    /** httpclient */
    private CloseableHttpAsyncClient httpClient;

    /** http连接池配置 */
    private PoolingNHttpClientConnectionManager connManager;

    /**
     * 初始化httpclient连接池
     */
    @PostConstruct
    public void initHttpClients() throws Exception {
        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        // 配置io线程池
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(ioThreadCount)
                .setSoKeepAlive(true)
                .build();
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        } catch (IOReactorException e) {
            log.error("初始化http连接池异常:", e);
        }

        SSLContextBuilder sslContextBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy());

        // 配置httpclient连接池
        connManager = new PoolingNHttpClientConnectionManager(ioReactor,
                RegistryBuilder.<SchemeIOSessionStrategy>create()
                        .register("http", NoopIOSessionStrategy.INSTANCE)
                        .register("https", new SSLIOSessionStrategy(sslContextBuilder.build(), NoopHostnameVerifier.INSTANCE))
                        .build());
        connManager.setMaxTotal(maxTotal);
        connManager.setDefaultMaxPerRoute(maxPerRoute);

        // httpclient
        httpClient = HttpAsyncClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        httpClient.start();
    }

    /**
     * 关闭http连接池
     */
    public void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            log.error("关闭http连接池异常:", e);
        }
    }

    /**
     * 异步发送http请求(POST)
     * @param msgID 报文标识号
     * @param url 请求url
     * @param requestHeaders 请求头
     * @param requestBody 请求体
     * @param futureCallback 回调函数
     * @return Future<HttpResponse>
     */
    public Future<HttpResponse> doAsycPost(String msgID, String url, Map<String, String> requestHeaders, String requestBody, final FutureCallback<HttpResponse> futureCallback) {
        // httpPost设置请求url
        HttpPost httpPost = new HttpPost(url);
        // httpPost设置body
        StringEntity entity = new StringEntity(requestBody, Charset.forName("utf-8"));
        httpPost.setEntity(entity);
        // httpPosts设置header
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        if (requestHeaders != null) {
            for (Map.Entry<String, String> key : requestHeaders.entrySet()) {
                httpPost.setHeader(key.getKey(), key.getValue());
            }
        }
        return httpClient.execute(httpPost, futureCallback);
    }

    /**
     * 异步发送http请求(GET)
     * @param msgID 报文标识号
     * @param url 请求url
     * @param requestHeaders 请求头
     * @param futureCallback 回调函数
     * @return Future<HttpResponse>
     */
    public Future<HttpResponse> doAsycGet(String msgID, String url, Map<String, String> requestHeaders, final FutureCallback<HttpResponse> futureCallback) {
        // 设置请求url
        HttpGet httpGet = new HttpGet(url);
        // 设置header
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("Accept", "application/json");
        if (requestHeaders != null) {
            for (Map.Entry<String, String> key : requestHeaders.entrySet()) {
                httpGet.setHeader(key.getKey(), key.getValue());
            }
        }
        // 发送请求
        return httpClient.execute(httpGet, futureCallback);
    }

    /**
     * 异步发送http请求(PUT)
     * @param msgID 报文标识号
     * @param url 请求url
     * @param requestHeaders 请求头
     * @param requestBody 请求体
     * @param futureCallback 回调函数
     * @return Future<HttpResponse>
     */
    public Future<HttpResponse> doAsycPut(String msgID, String url, Map<String, String> requestHeaders, String requestBody, final FutureCallback<HttpResponse> futureCallback){
        // httpPut设置请求url
        HttpPut httpPut = new HttpPut(url);
        // httpPut设置body
        StringEntity entity = new StringEntity(requestBody, Charset.forName("utf-8"));
        httpPut.setEntity(entity);
        // httpPosts设置header
        httpPut.setHeader("Content-Type", "application/json");
        httpPut.setHeader("Accept", "application/json");
        if (requestHeaders != null) {
            for (Map.Entry<String, String> key : requestHeaders.entrySet()) {
                httpPut.setHeader(key.getKey(), key.getValue());
            }
        }
        return httpClient.execute(httpPut, futureCallback);
    }

    /**
     * 异步发送http请求(DELETE)
     * @param msgID 报文标识号
     * @param url 请求url
     * @param requestHeaders 请求头
     * @param futureCallback 回调函数
     * @return Future<HttpResponse>
     */
    public Future<HttpResponse> doAsycDelete(String msgID, String url, Map<String, String> requestHeaders, final FutureCallback<HttpResponse> futureCallback) {
        // 设置请求url
        HttpDelete httpDelete = new HttpDelete(url);
        // 设置header
        httpDelete.setHeader("Content-Type", "application/json");
        httpDelete.setHeader("Accept", "application/json");
        if (requestHeaders != null) {
            for (Map.Entry<String, String> key : requestHeaders.entrySet()) {
                httpDelete.setHeader(key.getKey(), key.getValue());
            }
        }
        // 发送请求
        return httpClient.execute(httpDelete, futureCallback);
    }
}
