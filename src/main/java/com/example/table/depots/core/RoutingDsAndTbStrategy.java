package com.example.table.depots.core;

import com.example.table.depots.datasource.MultiDataSourceHolder;
import com.example.table.depots.exception.LoadRoutingStrategyUnMatch;
import com.example.table.depots.exception.RoutingFiledArgsIsNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:22
 * @Version 1.0
 * @Description 多库多表策略
 */
@Slf4j
public class RoutingDsAndTbStrategy extends AbstractRouting {

  @Override
  public String calDataSourceKey(String routingFiled)
      throws LoadRoutingStrategyUnMatch, RoutingFiledArgsIsNull {

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

    Integer routingFiledHashCode = getRoutingFileHashCode(routingFiled);

    Integer tbIndex = routingFiledHashCode % getDsRoutingSetProperties().getTableNum();

    String tableSuffix = getFormatTableSuffix(tbIndex);

    MultiDataSourceHolder.setTableIndex(tableSuffix);

    return tableSuffix;
  }


}
