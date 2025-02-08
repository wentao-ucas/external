package cn.cncc.caos.external.provider.cloud.service.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: DeferResultService
 * @Description: 异步结果服务类
 * @version: v1.0.0
 * @author: dengjq
 * @date: 2024/11/11 17:06
 */
@Component
@Slf4j
public class DeferResultService {
    /**
     * 封装DeferredResult,包括DeferredResult和put时间
     */
    public class DeferredResultObject {
        /** put的时间戳 */
        private long putTimeStamp;
        /** put的时间 */
        private String putTime;
        /** deferredResult */
        private DeferredResult deferredResult;
        /*completableFuture*/
        private CompletableFuture completableFuture;

        public DeferredResultObject(long putTimeStamp, String putTime, DeferredResult deferredResult) {
            this.putTimeStamp = putTimeStamp;
            this.putTime = putTime;
            this.deferredResult = deferredResult;
        }

        public DeferredResultObject(long putTimeStamp, String putTime, CompletableFuture completableFuture) {
            this.putTimeStamp = putTimeStamp;
            this.putTime = putTime;
            this.completableFuture = completableFuture;
        }

        public long getPutTimeStamp() {
            return putTimeStamp;
        }

        public String getPutTime() {
            return putTime;
        }

        public void setPutTimeStamp(long putTimeStamp) {
            this.putTimeStamp = putTimeStamp;
        }

        public void setPutTime(String putTime) {
            this.putTime = putTime;
        }

        public void setDeferredResult(DeferredResult deferredResult) {
            this.deferredResult = deferredResult;
        }

        public DeferredResult getDeferredResult() {
            return deferredResult;
        }

        public CompletableFuture getCompletableFuture() {
            return completableFuture;
        }

        public void setCompletableFuture(CompletableFuture completableFuture) {
            this.completableFuture = completableFuture;
        }
    }

    /** 保存deferredResult的map */
    public volatile ConcurrentHashMap<String, DeferredResultObject> deferredResultMap = new ConcurrentHashMap<String, DeferredResultObject>();

    /**
     * put deferredResult
     * @param uid 报文标识号
     * @param deferredResult CompletableFuture
     */
    public void putDeferredResult(String uid, CompletableFuture<Object> deferredResult) {
        log.debug("[{}]CompletableFuture put...",uid);
        // 生成当前时间戳和时间(yyyy-MM-dd HH:mm:ss.SSS)
        long currentTimeStamp = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(currentTimeStamp);
        String currentTime = simpleDateFormat.format(date);

        DeferredResultObject deferredResultObject = new DeferredResultObject(currentTimeStamp, currentTime, deferredResult);

        this.deferredResultMap.put(uid, deferredResultObject);
        log.debug("mappingCount:{}",deferredResultMap.mappingCount());
    }

    /**
     * put deferredResult
     * @param uid 报文标识号
     * @param deferredResult DeferredResult
     */
    public void putDeferredResult(String uid, DeferredResult<Object> deferredResult) {
        log.debug("[{}]DeferredResult put...",uid);
        // 生成当前时间戳和时间(yyyy-MM-dd HH:mm:ss.SSS)
        long currentTimeStamp = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(currentTimeStamp);
        String currentTime = simpleDateFormat.format(date);

        DeferredResultObject deferredResultObject = new DeferredResultObject(currentTimeStamp, currentTime, deferredResult);

        this.deferredResultMap.put(uid, deferredResultObject);
    }

    /**
     * get deferredResult by Id
     * @param oriMsgID 报文标识号
     * @return DeferredResult
     */
    public DeferredResult getDeferredResultById(String oriMsgID) {
        log.debug("[{}]DeferredResult get...",oriMsgID);
        DeferredResultObject deferredResultObject = deferredResultMap.get(oriMsgID);
        if (null == deferredResultObject) {
            return null;
        }
        return deferredResultObject.deferredResult;
    }

    /**
     * get deferredResult by Id
     * @param oriMsgID 报文标识号
     * @return CompletableFuture
     */
    public CompletableFuture getCompletableFutureById(String oriMsgID) {
        log.debug("[{}]CompletableFuture get...",oriMsgID);
        DeferredResultObject deferredResultObject = deferredResultMap.get(oriMsgID);
        if (null == deferredResultObject) {
            return null;
        }
        return deferredResultObject.completableFuture;
    }

    /**
     * del deferredResult by Id
     * @param oriMsgID 报文标识号
     */
    public void delDeferredResultById(String oriMsgID) {
        log.debug("[{}]deferredResult remove...",oriMsgID);
        this.deferredResultMap.remove(oriMsgID);
        log.debug("mappingCount:{}",deferredResultMap.mappingCount());
    }
}
