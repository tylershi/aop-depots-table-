package com.example.table.depots.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description 分库分表信息持有器
 */
@Slf4j
public class MultiDataSourceHolder {

  private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<>();

  private static final ThreadLocal<String> tableIndexHolder = new ThreadLocal<>();

  /**
   * @Description 保存数据源的key
   */
  public static void setDataSourceKey(String dsKey) {
    dataSourceHolder.set(dsKey);
  }

  /**
   * @Description 从threadLocal中取出key
   */
  public static String getDataSourceKey() {
    return dataSourceHolder.get();
  }

  /**
   * @Description 清除key
   */
  public static void clearDataSourceKey() {
    dataSourceHolder.remove();
  }

  public static String getTableIndex() {
    return tableIndexHolder.get();
  }

  public static void setTableIndex(String tableIndex) {
    tableIndexHolder.set(tableIndex);
  }

  public static void clearTableIndex() {
    tableIndexHolder.remove();
  }
}
