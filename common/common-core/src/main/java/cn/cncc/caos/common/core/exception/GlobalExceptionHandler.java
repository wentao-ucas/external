package cn.cncc.caos.common.core.exception;

import cn.cncc.caos.common.core.response.BaseResponse;
import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.IResponse;
import cn.cncc.caos.common.core.utils.BapUtil;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class) //该注解声明异常处理方法
  public BaseResponse<String> exceptionHandler(HttpServletRequest request, Exception e) {
    log.error("", e);
    log.error("api={}, requestMethod={}, queryString={}", request.getRequestURI(), request.getMethod(), request.getQueryString());
    try {
      log.error(JacksonUtil.objToJson(request.getParameterMap()));
    } catch (JsonProcessingException e1) {
      log.error("", e1);
    }
    //对于自定义异常的处理
    if (e instanceof BapException) {
      return FailResponse.getResponse(e);
      //对于绑定异常的处理，使用jsr303中的自定义注解抛出的异常属于绑定异常
    } else if (e instanceof BindException) {
      BindException ex = (BindException) e;
      List<ObjectError> errors = ex.getAllErrors();
      StringBuilder stringBuilder = BapUtil.buildErrorMessage(errors);
      return new FailResponse<>(IResponse.ERR_PARAMS, stringBuilder.toString());
    } else {
      return new FailResponse<>(IResponse.ERR_CODE);
    }
  }

}

