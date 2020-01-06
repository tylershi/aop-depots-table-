package com.example.table.depots.exception;


import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description 加载路由策略和配置配置文件不匹配
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoadRoutingStrategyUnMatch extends BizException {

  public LoadRoutingStrategyUnMatch() {
    super(SystemEvent.LOADING_STRATEGY_UN_MATCH.getId(),
        SystemEvent.LOADING_STRATEGY_UN_MATCH.getDetails());
  }
}
