package cn.cncc.caos.log.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpUtil {

    public static final String DEFAULT_ENCODING = "UTF-8";//默认编码
    //    public static final int SOCKET_TIMEOUT = 20000;//数据读取等待超时
    public static final int SOCKET_TIMEOUT = 600000;
    public static final int CONNECTION_TIMEOUT = 5000;//连接超时时间
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;//请求超时时间
    public static final String HTTPS = "https";

    /**
     * post请求(1.处理http请求;2.处理https请求,信任所有证书)
     *
     * @param url
     * @param jsonParams
     * @param encoding
     * @return
     */
    public static String post(String url, String jsonParams, String token, int socketTimeout, String encoding) {
        long nBegin = System.currentTimeMillis();
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }

        CloseableHttpClient httpClient = null;
        if (url.startsWith(HTTPS)) {
            // 创建一个SSL信任所有证书的httpClient对象
            return result;
        } else {
            httpClient = HttpClients.createDefault();
        }
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT) // 设置连接超时时间
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT) // 设置请求超时时间
                    .setSocketTimeout(socketTimeout).setRedirectsEnabled(true)// 默认允许自动重定向
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json");
            if(!StringUtils.isEmpty(token)) {
                httpPost.setHeader("X-Token", token);
            }
            httpPost.setEntity(new StringEntity(jsonParams, ContentType.create("application/json", encoding)));

            // 发送请求，并接收响应
            response = httpClient.execute(httpPost);

            result = handleResponse(url, encoding, response);

        } catch (Exception e) {
            throw new RuntimeException("Http Client Executed: used[{}]ms url:[{}], method:[post] , " + url + "  RPC:连接超时 \n" + e.getMessage());
        } finally {
            closeResource(httpClient, response);
        }

        System.out.println("Http Client Executed: used[{}]ms url:[{}], method:[post] , " + (System.currentTimeMillis() - nBegin) + "  , " + url);
        return result;
    }

    /**
     * 释放资源
     * @param httpClient
     * @param response
     */
    private static void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                System.out.println("HttpClient请求， 释放response资源异常:" + e.getMessage());
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                System.out.println("HttpClient请求， 释放httpclient资源异常:" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理响应，获取响应报文
     *
     * @param url
     * @param encoding
     * @param response
     * @return
     * @throws IOException
     */
    private static String handleResponse(String url, String encoding, CloseableHttpResponse response) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    // 获取响应实体
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
                        String s = null;
                        while ((s = br.readLine()) != null) {
                            sb.append(s);
                        }
                    }
                    // 锟酵凤拷entity
                    EntityUtils.consume(entity);
                }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                    System.out.println("HttpClient请求， get请求404,未找到资源. url:" + url);
                }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                    System.out.println("HttpClient请求， get请求500,服务器端异常. url:" + url);
                }
            }
        } catch (Exception e) {
            System.out.println("HttpClient请求，url:" + url + ",处理响应，获取响应报文异常：" + e.getMessage());
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }



}
