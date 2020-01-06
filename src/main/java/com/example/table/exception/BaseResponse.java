package com.example.table.exception;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:05
 * @Version 1.0
 * @Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

  private static final int SUCCESS = 200;

  private static final String OK = "OK";

  @ApiModelProperty(value = "返回码")
  private int retCode;

  @ApiModelProperty(value = "业务信息")
  private String message;

  @ApiModelProperty(value = "业务数据")
  private T data;

  private BaseResponse(T data) {
    this.retCode = SUCCESS;
    this.message = OK;
    this.data = data;
  }

  BaseResponse(int retCode, String message) {
    this.retCode = retCode;
    this.message = message;
    this.data = null;
  }

  private BaseResponse(SystemEvent systemEvent) {
    this.retCode = systemEvent.getId();
    this.message = systemEvent.getDetails();
  }

  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<>(data);
  }

  public static <T> BaseResponse<T> error(int code, String message) {
    return new BaseResponse<>(code, message);
  }

  public static void error(SystemEvent event) {
    throw new BizException(event.getId(), event.getDetails());
  }

}
