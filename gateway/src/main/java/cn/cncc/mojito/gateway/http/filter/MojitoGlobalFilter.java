package cn.cncc.mojito.gateway.http.filter;

import cn.cncc.mojito.common.constant.MojitoConstants;
import cn.cncc.mojito.gateway.http.RequestNameSpaceInfoService;
import cn.cncc.mojito.gateway.http.response.ResponseResult;
import cn.cncc.mojito.rpc.common.common.Header;
import cn.cncc.mojito.rpc.common.constant.Constants;
import cn.cncc.mojito.rpc.common.context.RpcInvokeContext;
import cn.cncc.mojito.rpc.common.web.HttpMethod;
import cn.cncc.mojito.rpc.invoker.handler.InvokerUtils;
import cn.cncc.mojito.rpc.invoker.http.HttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.isAlreadyRouted;

/**
 * SpringCloud GlobalFilter
 *
 * @author yangzhang01
 * @author yangshaobo
 * @since 0.1.0
 */
public class MojitoGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(MojitoGlobalFilter.class);

    private final static List<String> defaultRouter = Arrays.asList("http", "https", "lb", "ws");

    private final static String CONTENT_LENGTH = "content-length";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RequestNameSpaceInfoService requestNameSpaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final URI requestUrl = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        final String scheme = requestUrl.getScheme();
        if (isAlreadyRouted(exchange) || defaultRouter.contains(scheme)) {
            return chain.filter(exchange);
        }
        if (!MojitoConstants.MOJITO_SCHEMA_MOJITO.equals(scheme) && !MojitoConstants.MOJITO_SCHEMA_MVC.equals(scheme)) {
            return Mono.defer(() -> {
                ServerHttpResponse response = exchange.getResponse();
                return response.writeWith(Flux.create(sink -> {
                    response.setStatusCode(HttpStatus.BAD_GATEWAY);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    byte[] bt = genErrBody(HttpStatus.BAD_GATEWAY.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase());

                    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                    DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bt);
                    // next
                    sink.next(dataBuffer);
                    sink.complete();
                }));
            });
        }
        ServerWebExchangeUtils.setAlreadyRouted(exchange);
        final HttpMethod httpMethod = trans(exchange.getRequest().getMethod());
        final Map<String, String> headerMap = new HashMap<>(exchange.getRequest().getHeaders().toSingleValueMap());

        final Map<String, String> httpParam = exchange.getRequest().getQueryParams().toSingleValueMap();
        LinkedHashMap<Object, Object> cookieMap = Maps.newLinkedHashMap();
        exchange.getRequest().getCookies().toSingleValueMap().forEach((key, value) -> {
            cookieMap.put(key, value.getValue());
        });

        final String serviceName = requestUrl.getAuthority();
        return chain.filter(exchange)
                .then(Mono.defer(() -> {
                    ServerHttpResponse response = exchange.getResponse();
                    return response.writeWith(Flux.create(sink -> {
                        Object[] body = new Object[]{getRequestBody(exchange).get()};

                        byte[] bt;
                        try {
                            RpcInvokeContext.getContext().put(Constants.REQUEST_PARAMS, httpParam);
                            RpcInvokeContext.getContext().put(Constants.COOKIE_PARAMS, cookieMap);
                            RpcInvokeContext.getContext().put(Constants.REQUEST_PATH, requestUrl.getPath());
                            if (MojitoConstants.MOJITO_SCHEMA_MVC.equals(scheme)) {
                                RpcInvokeContext.getContext().put(MojitoConstants.MOJITO_SCHEMA_TYPE, MojitoConstants.MOJITO_SCHEMA_MVC);
                            }
                            // 获取namespace
                            String targetNameSpace = requestNameSpaceInfoService.obtainNs(exchange);
                            // 发起服务调用
                            Object back = InvokerUtils.invoke(body, headerMap, serviceName, httpMethod, targetNameSpace);
                            if (back instanceof HttpResponse) {
                                HttpResponse map = (HttpResponse) back;
                                response.getHeaders().putAll(parseHeaders(map.getHeader()));

                                HttpStatus status = HttpStatus.resolve(map.getStatus());
                                if (status == null) {
                                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                                }
                                response.setStatusCode(status);
                                bt = map.getBody().asByteArray();
                            } else if (back instanceof CompletableFuture) {
                                CompletableFuture future = (CompletableFuture) back;
                                Object result = future.get();
                                // 仅对TCP做处理(对外特性不变)，后续支持http需要分开处理
                                bt = tcpResponseProc(result, response);
                            } else {
                                // tcp 请求结果需要包装
                                bt = tcpResponseProc(back, response);
                            }
                        } catch (Throwable t) {

                            String s = t.getMessage();
                            if (s == null) {
                                s = "";
                            }
                            if (s.indexOf("flow.FlowException") > -1) {
                                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                                bt = genErrBody(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                            } else if (s.indexOf("degrade.DegradeException") > -1) {
                                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                                bt = genErrBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                            } else if (s.indexOf("authority.AuthorityException") > -1) {
                                response.setStatusCode(HttpStatus.FORBIDDEN);
                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                                bt = genErrBody(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
                            } else {
                                log.error("http gateway invoke err", t);
                                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                                bt = genErrBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                            }

                        } finally {
                            RpcInvokeContext.getContext().removeContext();
                        }

                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                        DataBuffer dataBuffer = nettyDataBufferFactory.wrap(bt);
                        // 将数据进行发射到下一个过滤器
                        sink.next(dataBuffer);
                        sink.complete();
                    }));

                })).doOnError(e ->
                        log.error("cache body error, request method: {}, uri: {}", exchange.getRequest().getMethod()
                                , exchange.getRequest().getURI().toString(), e));
    }

    private byte[] tcpResponseProc(Object back, ServerHttpResponse response) {
        ResponseResult responseResult = ResponseResult.success(back);
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] result = getJsonBytes(responseResult);
        return result;
    }

    private byte[] genErrBody(int code, String msg) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);

        byte[] bt = getJsonBytes(map);
        return bt;
    }

    private Map<String, List<String>> parseHeaders(Header headers) {
        HashMap<String, List<String>> newHeaders = Maps.newHashMap();
        headers.getHeaderMap().forEach((k, v) -> {
            if (!k.toLowerCase().trim().equals(CONTENT_LENGTH)) {
                newHeaders.put(k, Arrays.asList((String) v));
            }
        });
        return newHeaders;
    }

    /**
     * 获取客户端请求的数据，body体
     *
     * @param exchange
     */
    private AtomicReference getRequestBody(ServerWebExchange exchange) {
        Flux<DataBuffer> flux = exchange.getRequest().getBody();
        AtomicReference<byte[]> bodyRef = new AtomicReference<>();
        flux.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            bodyRef.set(bytes);
            DataBufferUtils.release(buffer);
        });
        return bodyRef;
    }

    private byte[] getJsonBytes(HashMap map) {
        JavaType javaType = objectMapper.getTypeFactory().constructType(HashMap.class);
        byte[] bt = new byte[0];
        try {
            bt = objectMapper.writerFor(javaType).writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            log.error("json err", e);

        }
        return bt;
    }

    private HttpMethod trans(org.springframework.http.HttpMethod method) {
        switch (method) {
            case GET:
                return HttpMethod.GET;
            case POST:
                return HttpMethod.POST;
            case PATCH:
                return HttpMethod.PATCH;
            case PUT:
                return HttpMethod.PUT;
            case HEAD:
                return HttpMethod.HEAD;
            case TRACE:
                return HttpMethod.TRACE;
            case DELETE:
                return HttpMethod.DELETE;
            case OPTIONS:
                return HttpMethod.OPTIONS;
            default:
                return HttpMethod.POST;
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
