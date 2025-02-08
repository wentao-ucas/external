package cn.cncc.caos.common.core.validator;

import cn.cncc.caos.common.core.response.FailResponse;
import cn.cncc.caos.common.core.response.IResponse;
import cn.cncc.caos.common.core.utils.CAOSUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ResponseBodyAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<FailResponse> handleBindingErrors(Exception ex) {
    MethodArgumentNotValidException exception = MethodArgumentNotValidException.class.cast(ex);
    List<ObjectError> errors = exception.getBindingResult().getAllErrors();
    StringBuilder message = CAOSUtil.buildErrorMessage(errors);
    FailResponse response = new FailResponse(IResponse.ERR_PARAMS, message.toString());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
