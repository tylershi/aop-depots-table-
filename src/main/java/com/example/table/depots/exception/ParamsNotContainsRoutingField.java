package com.example.table.depots.exception;

import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description 入参中没有包含路由字段异常
 */
public class ParamsNotContainsRoutingField extends BizException {

  public ParamsNotContainsRoutingField() {
    super(SystemEvent.PARAMS_NOT_CONTAINS_ROUTING_FIELD.getId(),
        SystemEvent.PARAMS_NOT_CONTAINS_ROUTING_FIELD.getDetails());

  }
}
