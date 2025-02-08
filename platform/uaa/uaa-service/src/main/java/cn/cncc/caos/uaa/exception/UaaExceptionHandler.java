package cn.cncc.caos.uaa.exception;

import cn.cncc.caos.common.core.response.jw.JwResponseCode;
import cn.cncc.caos.common.core.response.jw.JwResponseData;
import cn.cncc.caos.common.core.utils.JacksonUtil;
import cn.cncc.caos.uaa.utils.ExceptionDetailUtil;
import cn.cncc.caos.uaa.utils.KDUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
public class UaaExceptionHandler {

    @ExceptionHandler(value=Exception.class) //该注解声明异常处理方法
    public JwResponseData<String> exceptionHandler(HttpServletRequest request, Exception e){
        log.error("", e);
        log.error("api={}, requestMethod={}, queryString={}", request.getRequestURI(), request.getMethod(),request.getQueryString());
        try {
            log.error(JacksonUtil.objToJson(request.getParameterMap()));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        //对于自定义异常的处理
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return JwResponseData.error(ex.getCm(), ex.getExMsg().toString().length()!=0?ex.getExMsg().toString(): ExceptionDetailUtil.getExceptionDetail(e));
            //对于绑定异常的处理，使用jsr303中的自定义注解抛出的异常属于绑定异常
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            StringBuilder stringBuilder = KDUtil.buildErrorMessage(errors);
            return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(stringBuilder.toString()));
//            ObjectError error = errors.get(0);
//            String msg = error.getDefaultMessage();
//            return JwResponseData.error(JwResponseCode.BIND_ERROR.fillArgs(msg),ExceptionDetailUtil.getExceptionDetail(e));
        } else {
            return JwResponseData.error(JwResponseCode.SERVER_ERROR);
//            return JwResponseData.error(JwResponseCode.SERVER_ERROR, ExceptionDetailUtil.getExceptionDetail(e));
        }
    }

}

