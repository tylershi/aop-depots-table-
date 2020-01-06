package com.example.table.config.interceptor;

import com.example.table.exception.BaseResponse;
import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:22
 * @Version 1.0
 * @Description 定义全局异常拦截
 */
@RestControllerAdvice(basePackages = "com.example.service.web.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * MethodArgument validate 失败的异常.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    final BindingResult bindingResult = ex.getBindingResult();

    return ResponseEntity.badRequest().body(
        BaseResponse
            .error(SystemEvent.INVALID_PARAM.getId(), getErrors(bindingResult).toString()));
  }


  /**
   * Json格式错误异常.
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    String message = ex.getMessage();
    final Throwable cause = ex.getCause();
    if (cause != null) {
      message = message + "; Caused by:" + cause.getMessage();
    }
    return ResponseEntity.badRequest()
        .body(BaseResponse.error(SystemEvent.INVALID_PARAM.getId(), message));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    SystemEvent event = SystemEvent.SYSTEM_ERROR;
    logger.error(exception.getMessage(), exception);
    String msg = exception.getMessage() + "; Caused by: " + exception.getCause().getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(event.getId(), msg));
  }


  /**
   * 业务主动失败异常. 所有抛出BizException都会被拦截.
   */
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(BizException.class)
  public BaseResponse<Object> bizException(BizException exception) {
    return BaseResponse.error(exception.getCode(), exception.getMessage());
  }


  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public BaseResponse<Object> otherException(Exception exception) {
    SystemEvent event = SystemEvent.SYSTEM_ERROR;
    logger.error(exception.getMessage(), event, exception);
    String msg = exception.getMessage();
    if (exception.getCause() != null) {
      msg = msg + "; Caused by: " + exception.getCause().getMessage();
    }

    return BaseResponse.error(event.getId(), msg);
  }


  private Map<String, String> getErrors(BindingResult result) {

    Map<String, String> map = new HashMap<>();
    for (FieldError fieldError : result.getFieldErrors()) {
      map.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return map;
  }

  /*private String getErrorsInfo(BindingResult result) {
    StringBuilder sb = new StringBuilder();
    for (FieldError fieldError : result.getFieldErrors()) {
      sb.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(";");
    }
    return sb.toString();
  }*/


}
