package com.example.table.depots.core;

import com.example.table.depots.exception.FormatTableSuffixException;
import com.example.table.depots.exception.LoadRoutingStrategyUnMatch;
import com.example.table.depots.exception.RoutingFiledArgsIsNull;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:22
 * @Version 1.0
 * @Description 路由抽象行为定义
 */
public interface IRouting {

  /**
   * 根据规则计算出
   *
   * @param routingFiled
   * @return
   */
  String calDataSourceKey(String routingFiled)
      throws LoadRoutingStrategyUnMatch, RoutingFiledArgsIsNull;


  /**
   * 计算routingFiled字段的 hashcode值
   *
   * @param routingFiled
   * @return
   */
  Integer getRoutingFileHashCode(String routingFiled);

  /**
   * 计算一个库所在表的索引值
   *
   * @param routingFiled
   * @return
   */
  String calTableKey(String routingFiled) throws LoadRoutingStrategyUnMatch, RoutingFiledArgsIsNull;

  String getFormatTableSuffix(Integer tableIndex) throws FormatTableSuffixException;
}
