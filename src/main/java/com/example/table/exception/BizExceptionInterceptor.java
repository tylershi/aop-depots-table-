package com.example.table.exception;

import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @Author tyler.shi
 * @Date 2020/1/6 9:08
 * @Version 1.0
 * @Description
 */
@ResponseBody
@ControllerAdvice(basePackages = "com.example.table.web.controller")
public class BizExceptionInterceptor extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(BizExceptionInterceptor.class);

  /**
   * Hibernate validate 失败的异常.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    final BindingResult bindingResult = ex.getBindingResult();

    return ResponseEntity.badRequest().body(
        new BaseResponse<>(SystemEvent.INVALID_PARAM.getId(),
            getErrors(bindingResult).toString()));
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
        .body(new BaseResponse<>(SystemEvent.INVALID_PARAM.getId(), message));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    SystemEvent event = SystemEvent.SYSTEM_ERROR;
    LOGGER.error(exception.getMessage(), exception);
    String msg = exception.getMessage() + "; Caused by: " + exception.getCause().getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new BaseResponse<>(event.getId(), msg));
  }

  /**
   * 业务主动失败异常. 所有抛出BizException都会被拦截.
   */
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(BizException.class)
  @ResponseBody
  public BaseResponse<Object> bizException(BizException exception) {
    return new BaseResponse<>(exception.getCode(), exception.getMessage());
  }


  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public BaseResponse<Object> otherException(Exception exception) {
    SystemEvent event = SystemEvent.SYSTEM_ERROR;
    LOGGER.error(exception.getMessage(), event, exception);
    String msg = exception.getMessage();
    if (exception.getCause() != null) {
      msg = msg + "; Caused by: " + exception.getCause().getMessage();
    }

    return new BaseResponse<>(event.getId(), msg);
  }


  private Map<String, String> getErrors(BindingResult result) {

    return result.getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField,
            DefaultMessageSourceResolvable::getDefaultMessage, (o1, o2) -> o1));
  }
}
