package com.example.table.depots.config;

import com.example.table.depots.core.IRouting;
import com.example.table.depots.core.RoutingDsAndTbStrategy;
import com.example.table.depots.core.RoutingDsStrategy;
import com.example.table.depots.core.RoutingTbStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:22
 * @Version 1.0
 * @Description 路由策略配置
 */
@Configuration
public class RoutingStrategyConfig {

  @Bean
  @ConditionalOnProperty(prefix = "example.dsroutingset", name = "routingStrategy",
      havingValue = "ROUTING_DS_TABLE_STRATEGY")
  public IRouting routingDsAndTbStrategy() {
    return new RoutingDsAndTbStrategy();
  }

  @Bean
  @ConditionalOnProperty(prefix = "example.dsroutingset", name = "routingStrategy",
      havingValue = "ROUTING_DS_STRATEGY")
  public IRouting routingDsStrategy() {
    return new RoutingDsStrategy();
  }

  @Bean
  @ConditionalOnProperty(prefix = "example.dsroutingset", name = "routingStrategy",
      havingValue = "ROUTING_TABLE_STRATEGY")
  public IRouting routingTbStrategy() {
    return new RoutingTbStrategy();
  }
}
