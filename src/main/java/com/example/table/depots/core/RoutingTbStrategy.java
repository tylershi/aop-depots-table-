package com.example.table.depots.core;

import com.example.table.depots.datasource.MultiDataSourceHolder;


/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:22
 * @Version 1.0
 * @Description 一库多表策略
 */
public class RoutingTbStrategy extends AbstractRouting {

  private static final String ROUTING_DS_STRATEGY_TABLE_SUFFIX = "dataSource00";


  @Override
  public String calDataSourceKey(String routingFiled) {
    MultiDataSourceHolder.setDataSourceKey(ROUTING_DS_STRATEGY_TABLE_SUFFIX);
    return ROUTING_DS_STRATEGY_TABLE_SUFFIX;
  }

  @Override
  public String calTableKey(String routingFiled) {
    //前置检查
    Integer routingFiledHashCode = getRoutingFileHashCode(routingFiled);

    Integer tbIndex = routingFiledHashCode % getDsRoutingSetProperties().getTableNum();

    String tableSuffix = getFormatTableSuffix(tbIndex);

    MultiDataSourceHolder.setTableIndex(tableSuffix);

    return tableSuffix;
  }
}
