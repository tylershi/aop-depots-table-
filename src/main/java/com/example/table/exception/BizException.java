package com.example.table.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author tyler.shi
 * @Date 2020/1/6 9:05
 * @Version 1.0
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

  private int code;

  public BizException(Integer code, String message) {
    super(message);
    this.code = code;
  }

  public BizException(SystemEvent event) {
    super(event.getDetails());
    this.code = event.getId();
  }


}
