package cn.cncc.caos.common.core.aspect;


import cn.cncc.caos.common.core.request.BapRequest;
import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
public abstract class BaseTimeCostAspect {
  public abstract boolean isLogSwitch();

  public abstract String[] getIgnoreURIs();

  private boolean isIgnore(String uri) {
    String[] ignoreURIs = getIgnoreURIs();
    if (ignoreURIs == null) return false;
    for (String ignoreURI : ignoreURIs) {
      if (uri.endsWith(ignoreURI)) {
        return true;
      }
    }
    return false;
  }

  public Object around(ProceedingJoinPoint point) throws Throwable {
    //开始时间
    long start = System.currentTimeMillis();
    //执行方法
    Object result = point.proceed();
    long end = System.currentTimeMillis();
    int cost = (int) (end - start);
    if (result instanceof BaseResponse) {
      ((BaseResponse<?>) result).setCost(cost);
    }
    if (isLogSwitch()) {
      HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
      if (!isIgnore(request.getRequestURI())) {
        try {
          String params = "";
          if (point.getArgs() != null) {
            Object[] args = point.getArgs();
            for (Object arg : args) {
              if (arg instanceof BapRequest) {
                params = JacksonUtil.objToJson(arg);
              }
            }
          }
          log.info("Request: {}, {}, {}, {}", request.getRequestURI(), request.getMethod(), params, cost);
        } catch (Exception e) {
          log.error("", e);
        }
      }
    }
    return result;
  }
}