package com.example.table.depots.core;

import com.example.table.depots.datasource.MultiDataSourceHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:22
 * @Version 1.0
 * @Description 多库 一表策略
 */
@Slf4j
public class RoutingDsStrategy extends AbstractRouting {

  private static final String ROUTING_DS_STRATEGY_TABLE_SUFFIX = "_0000";

  @Override
  public String calDataSourceKey(String routingFiled) {

    Integer routingFiledHashCode = getRoutingFileHashCode(routingFiled);
    //定位库的索引值
    Integer dsIndex = routingFiledHashCode % getDsRoutingSetProperties().getDataSourceNum();

    //根据库的索引值定位 数据源的key
    String dataSourceKey = getDsRoutingSetProperties().getDataSourceKeysMapping().get(dsIndex);

    //放入线程变量
    MultiDataSourceHolder.setDataSourceKey(dataSourceKey);

    return dataSourceKey;
  }

  @Override
  public String calTableKey(String routingFiled) {
    MultiDataSourceHolder.setTableIndex(ROUTING_DS_STRATEGY_TABLE_SUFFIX);
    return ROUTING_DS_STRATEGY_TABLE_SUFFIX;
  }

}
