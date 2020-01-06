package com.example.table.depots.datasource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 13:01
 * @Version 1.0
 * @Description 数据源选择
 */
@Slf4j
public class MultiDataSource extends AbstractRoutingDataSource {

  @Nullable
  @Override
  protected Object determineCurrentLookupKey() {
    return MultiDataSourceHolder.getDataSourceKey();
  }


}
