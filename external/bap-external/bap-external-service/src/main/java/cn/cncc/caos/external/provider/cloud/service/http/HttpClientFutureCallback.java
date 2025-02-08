package cn.cncc.caos.external.provider.cloud.service.http;

import cn.cncc.caos.common.core.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @className: HttpClientFutureCallback
 * @Description: Http异步调用回调方法
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/11 17:08
 */
@Slf4j
public class HttpClientFutureCallback implements FutureCallback<HttpResponse> {
    private HttpClientService httpClientService = SpringUtil.getBan(HttpClientService.class);
    private DeferResultService resultService = SpringUtil.getBan(DeferResultService.class);

    /** 报文标识号 */
    public String msgID = "";

    /** 请求url */
    public String url = "";

    /** http请求header */
    public Map<String, String> requestHeaders = null;

    /** http请求body */
    public String requestBody = null;

    /** 当前请求次数 */
    public int callnum = 1;

    /** 最大请求次数 */
    public int maxCallnum = 1;

    /** 请求方式 */
    public String method = "";

    /**
     * 构造函数
     * @param msgID 报文标识号
     * @param url 请求url
     * @param requestHeaders http请求header
     * @param requestBody http请求body
     * @param callnum 当前请求次数
     * @param maxCallnum 最大请求次数
     * @param method HTTP请求方法
     */
    public HttpClientFutureCallback(String msgID, String url, Map<String, String> requestHeaders, String requestBody, int callnum, int maxCallnum, String method) {
        this.msgID = msgID;
        this.url = url;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.callnum = callnum;
        this.maxCallnum = maxCallnum;
        this.method = method;
    }



    @Override
    public void completed(HttpResponse httpResponse) {
        log.debug("["+msgID+"]服务返回响应");
        String errMsg = "";
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            /**
             * 华为删除操作成功的HTTP状态码是204
             */
            if(httpResponse.getStatusLine().getStatusCode() == 204){
                log.info("[{}]删除操作成功",msgID);
                CompletableFuture deferredResult = resultService.getCompletableFutureById(msgID);
                if (null == deferredResult) {
                    log.error("从result map中未匹配到msgID: " + msgID);
                    return;
                }
                deferredResult.complete("success");
                return;
            }
            /**
             * 华为创建类请求完全成功的HTTP状态码是201
             */
            if(httpResponse.getStatusLine().getStatusCode() == 201){
                returnResponse(httpResponse);
                return;
            }

            // 读取body
            String responseBodyStr = "";
            try{
                ByteArrayOutputStream byteBody = new ByteArrayOutputStream();
                InputStream input = httpResponse.getEntity().getContent();
                byte[] arr = new byte[1024];
                int len = 0;
                while((len = input.read(arr)) != -1) {
                    byteBody.write(arr, 0, len);
                }
                responseBodyStr = byteBody.toString("utf-8");
            } catch (Exception e) {
                log.error("["+msgID+"]读取响应body异常:", e);
            }
            log.debug("打印响应报文体:\n" + responseBodyStr);

            // http请求状态码异常处理
            errMsg = "["+msgID+"]第" + callnum + "次调用失败,url:" + url + ",HTTP/HTTPS状态码:" + httpResponse.getStatusLine().getStatusCode()+"\n详细信息:"+responseBodyStr;
            log.error(errMsg);

            // 重试
            if (retry()) {
                log.warn("["+msgID+"]调用" + url + "HTTP/HTTPS状态码异常后,调用retry函数结束,等待系统回应......");
            }

            CompletableFuture deferredResult = resultService.getCompletableFutureById(msgID);
            if (null == deferredResult) {
                log.error("从result map中未匹配到msgID: " + msgID);
                return;
            }
            String code = null;
            String message = null;
            try {
                JSONObject jsonObject = new JSONObject(responseBodyStr);
                code = String.valueOf(jsonObject.get("error_code"));
                message = jsonObject.getString("error_message");
            } catch (JSONException e) {
                log.error("["+msgID+"]请求华为云响应解析异常,原样返回响应",e);
                deferredResult.complete("ERROR:"+responseBodyStr);
            }

            deferredResult.complete("ERROR:【"+code+"】"+message);
            log.debug("completed处理函数返回");
        }else {
            returnResponse(httpResponse);
        }
    }

    /**
     * http请求状态码正常(200/201/204)处理
     */
    public void returnResponse(HttpResponse httpResponse){
        log.debug("["+msgID+"]第" + callnum + "次调用成功,url:" + url);
        // 读取header
        Header[] responseHeaders = httpResponse.getAllHeaders();
        String hresponseHeadersStr = "";
        for (Header header:responseHeaders) {
            hresponseHeadersStr += header.getName() + ":" + header.getValue() + "\n";
        }
        log.debug("打印响应报文头:\n" + hresponseHeadersStr);

        // 读取body
        String responseBodyStr = "";
        try{
            ByteArrayOutputStream byteBody = new ByteArrayOutputStream();
            InputStream input = httpResponse.getEntity().getContent();
            byte[] arr = new byte[1024];
            int len = 0;
            while((len = input.read(arr)) != -1) {
                byteBody.write(arr, 0, len);
            }
            responseBodyStr = byteBody.toString("utf-8");
        } catch (Exception e) {
            log.error("["+msgID+"]读取响应body异常:", e);
        }
        log.debug("打印响应报文体:\n" + responseBodyStr);

        CompletableFuture deferredResult = resultService.getCompletableFutureById(msgID);
        if (null == deferredResult) {
            log.error("从result map中未匹配到msgID: " + msgID);
            return;
        }
        deferredResult.complete(responseBodyStr);
    }

    @Override
    public void failed(Exception e) {
        log.error("["+msgID+"]request failed,异常信息:", e);
        String errMsg = "["+msgID+"]第" + callnum + "次调用url:" + url + "失败,网络访问异常:" + e.getMessage();
        log.error(errMsg);
        if (retry()) {
            log.warn("["+msgID+"]调用" + url + "网络访问异常后,调用retry函数结束,等待系统回应......");
        }

        CompletableFuture deferredResult = resultService.getCompletableFutureById(msgID);
        if (null == deferredResult) {
            log.error("从result map中未匹配到msgID: " + msgID);
            return;
        }
        deferredResult.complete("ERROR:request failed,"+e.getMessage());

        log.debug("failed处理函数返回");
    }

    private boolean retry() {
        String errMsg = "";
        // 判断是否超过最大调用次数
        if (callnum >= maxCallnum) {
            errMsg = "["+msgID+"]重试url:" + url + "失败,超过最大调用次数";
            log.error(errMsg);
            return false;
        }
        // 开始重试
        callnum++;
        if(method.equals("POST")){
            httpClientService.doAsycPost(msgID, url, requestHeaders, requestBody,
                    new HttpClientFutureCallback(msgID, url, requestHeaders, requestBody, callnum, maxCallnum, method));
            return true;
        }else if(method.equals("PUT")){
            httpClientService.doAsycPut(msgID, url, requestHeaders, requestBody,
                    new HttpClientFutureCallback(msgID, url, requestHeaders, requestBody, callnum, maxCallnum, method));
            return true;
        }else if(method.equals("GET")){
            httpClientService.doAsycGet(msgID, url, requestHeaders,
                    new HttpClientFutureCallback(msgID, url, requestHeaders, requestBody, callnum, maxCallnum, method));
            return true;
        }else if(method.equals("DELETE")){
            httpClientService.doAsycDelete(msgID, url, requestHeaders,
                    new HttpClientFutureCallback(msgID, url, requestHeaders, requestBody, callnum, maxCallnum, method));
            return true;
        }else{
            log.error("[{}]method不合法!",msgID);
            return false;
        }
    }

    @Override
    public void cancelled() {
        log.info("[{}]request cancelled!",msgID);
        CompletableFuture deferredResult = resultService.getCompletableFutureById(msgID);
        if (null == deferredResult) {
            log.error("从result map中未匹配到msgID: " + msgID);
            return;
        }
        deferredResult.complete("ERROR:request cancelled");
    }
}
