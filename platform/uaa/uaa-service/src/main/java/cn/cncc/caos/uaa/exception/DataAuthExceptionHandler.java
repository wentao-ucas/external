package cn.cncc.caos.uaa.exception;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.uaa.utils.ExceptionDetailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
@Slf4j
public class DataAuthExceptionHandler extends UaaExceptionHandler {

  @ExceptionHandler(value = Exception.class) //该注解声明异常处理方法
  public JwResponseData<String> exceptionHandler(HttpServletRequest request, Exception e) {

    log.error("", e);
    log.info("-----" + e.toString());
    //对于自定义异常的处理
    if (e instanceof GlobalException) {
      GlobalException ex = (GlobalException) e;
      return JwResponseData.error(ex.getCm(), ex.getExMsg().toString().length() != 0 ? ex.getExMsg().toString() : ExceptionDetailUtil.getExceptionDetail(e));
    } else if (e instanceof org.springframework.security.access.AccessDeniedException) {
      return JwResponseData.error(JwResponseCode.ACCESS_DENIED_ERROR, ExceptionDetailUtil.getExceptionDetail(e));
    } else if (e instanceof DuplicateKeyException) {
      return JwResponseData.error(JwResponseCode.DuplicateKeyException, ExceptionDetailUtil.getExceptionDetail(e));
    } else {
      JwResponseData<String> result = super.exceptionHandler(request, e);
      log.info("-----" + result.getMsg());
      return result;
    }
  }
}

