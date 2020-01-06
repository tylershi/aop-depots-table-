package com.example.table.depots.exception;


import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description 路由key 为空异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoutingFiledArgsIsNull extends BizException {

  public RoutingFiledArgsIsNull() {
    super(SystemEvent.ROUTING_FIELD_ARGS_ISNULL.getId(),
        SystemEvent.ROUTING_FIELD_ARGS_ISNULL.getDetails());

  }
}
