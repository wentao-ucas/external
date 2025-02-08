package cn.cncc.caos.uaa.exception;
/*
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class AuthCenterExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class) //该注解声明异常处理方法
    public JwResponseData<String> exceptionHandler(HttpServletRequest request, Exception e){


        log.info("+++++++"+ e.toString());


        //对于自定义异常的处理
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return JwResponseData.error(ex.getCm(), ExceptionDetailUtil.getExceptionDetail(e));
        }else if(e instanceof org.springframework.security.access.AccessDeniedException){
            return JwResponseData.error(ResponseCode.ACCESS_DENIED_ERROR, ExceptionDetailUtil.getExceptionDetail(e));
        }else if(e instanceof org.springframework.security.oauth2.common.exceptions.InvalidTokenException){
            return JwResponseData.error(ResponseCode.ACCESS_DENIED_ERROR, ExceptionDetailUtil.getExceptionDetail(e));
        }
        else {
            JwResponseData<String> result = super.exceptionHandler(request,e);
            log.info("+++++++"+ result.getMsg());
            return result;
        }
    }
}

*/