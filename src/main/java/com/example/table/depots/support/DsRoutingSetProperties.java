package com.example.table.depots.support;

import com.example.table.depots.constant.DepotsConstant;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @Author tyler.shi
 * @Date 2020/1/5 13:31
 * @Version 1.0
 * @Description 该类用于配置分库的个数  以及分表的个数,以及配置路由key
 */
@Data
@ConfigurationProperties(prefix = "example.dsroutingset")
public class DsRoutingSetProperties {

  /**
   * 默认是一个数据库 默认一个
   */
  private Integer dataSourceNum = 1;

  /**
   * 每一个库对应表的个数 默认是一个
   */
  private Integer tableNum = 1;

  /**
   * 所有生产写库数据有的名称
   */
  private Map<Integer, String> dataSourceKeysMapping;

  /**
   * 表的后缀连接风格 比如order_
   */
  private String tableSuffixConnect = "_";

  /**
   * 表的索引值 格式化为四位 不足左补零   1->0001 然后在根据tableSuffixConnect属性拼接成 成一个完整的表名  比如 order表 所以为1  那么数据库表明为
   * order_0001
   */
  private String tableSuffixStyle = "%04d";


  /**
   * 默认的路由策略
   */
  private String routingStrategy = DepotsConstant.ROUTING_DS_TABLE_STRATEGY;


}
