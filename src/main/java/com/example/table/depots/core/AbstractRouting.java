package com.example.table.depots.core;

import com.example.table.depots.constant.DepotsConstant;
import com.example.table.depots.constant.MultiDsErrorEnum;
import com.example.table.depots.exception.FormatTableSuffixException;
import com.example.table.depots.exception.LoadRoutingStrategyUnMatch;
import com.example.table.depots.support.DsRoutingSetProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description 路由规则抽象类
 */
@Slf4j
@EnableConfigurationProperties(value = {DsRoutingSetProperties.class})
@Data
public abstract class AbstractRouting implements IRouting, InitializingBean {

  @Autowired
  private DsRoutingSetProperties dsRoutingSetProperties;

  /**
   * @Description 获取路由key的hash值
   */
  public Integer getRoutingFileHashCode(String routingFiled) {
    return Math.abs(routingFiled.hashCode());
  }

  /**
   * @Description 获取表的后缀
   */
  @Override
  public String getFormatTableSuffix(Integer tableIndex) {
    StringBuffer stringBuffer = new StringBuffer(
        dsRoutingSetProperties.getTableSuffixConnect());

    try {
      stringBuffer.append(
          String.format(getDsRoutingSetProperties().getTableSuffixStyle(), tableIndex));
    } catch (Exception e) {
      log.error("格式化表后缀异常:{}", getDsRoutingSetProperties().getTableSuffixStyle());
      throw new FormatTableSuffixException();
    }
    return stringBuffer.toString();
  }


  /**
   * @Description 工程在启动的时候 检查配置路由参数和 策略是否相匹配
   */
  @Override
  public void afterPropertiesSet() throws LoadRoutingStrategyUnMatch {
    switch (getDsRoutingSetProperties().getRoutingStrategy()) {
      case DepotsConstant.ROUTING_DS_TABLE_STRATEGY:
        checkRoutingDsTableStrategyConfig();
        break;
      case DepotsConstant.ROUTING_DS_STRATEGY:
        checkRoutingDsStrategyConfig();
        break;
      case DepotsConstant.ROUTING_TABLE_STRATEGY:
        checkRoutingTableStrategyConfig();
        break;
    }
  }

  /**
   * @Description 检查多库 多表配置
   */
  private void checkRoutingDsTableStrategyConfig() {
    if (dsRoutingSetProperties.getTableNum() <= 1
        || dsRoutingSetProperties.getDataSourceNum() <= 1) {
      log.error("你的配置项routingStategy:{}是多库多表配置,数据库个数>1," +
              "每一个库中表的个数必须>1,您的配置:数据库个数:{},表的个数:{}",
          dsRoutingSetProperties.getRoutingStrategy(),
          dsRoutingSetProperties.getDataSourceNum(),
          dsRoutingSetProperties.getTableNum());
      throw new LoadRoutingStrategyUnMatch();
    }
  }

  /**
   * @Description 检查多库一表的路由配置项
   */
  private void checkRoutingDsStrategyConfig() {
    if (dsRoutingSetProperties.getTableNum() != 1
        || dsRoutingSetProperties.getDataSourceNum() <= 1) {
      log.error("你的配置项routingStategy:{}是多库一表配置,数据库个数>1," +
              "每一个库中表的个数必须=1,您的配置:数据库个数:{},表的个数:{}",
          dsRoutingSetProperties.getRoutingStrategy(),
          dsRoutingSetProperties.getDataSourceNum(),
          dsRoutingSetProperties.getTableNum());
      throw new LoadRoutingStrategyUnMatch();
    }
  }

  /**
   * @Description 检查一库多表的路由配置项
   */
  private void checkRoutingTableStrategyConfig() {
    if (dsRoutingSetProperties.getTableNum() <= 1
        || dsRoutingSetProperties.getDataSourceNum() != 1) {
      log.error("你的配置项routingStategy:{}是一库多表配置,数据库个数=1," +
              "每一个库中表的个数必须>1,您的配置:数据库个数:{},表的个数:{}",
          dsRoutingSetProperties.getRoutingStrategy(),
          dsRoutingSetProperties.getDataSourceNum(),
          dsRoutingSetProperties.getTableNum());
      throw new LoadRoutingStrategyUnMatch();
    }
  }

}
