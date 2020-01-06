package com.example.table.depots.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 13:31
 * @Version 1.0
 * @Description 数据源配置
 */
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidProperties {

  private String druid00username;

  private String druid00passwrod;

  private String druid00jdbcUrl;

  private String druid00driverClass;

  private String druid01username;

  private String druid01passwrod;

  private String druid01jdbcUrl;

  private String druid01driverClass;

  private String druid02username;

  private String druid02passwrod;

  private String druid02jdbcUrl;

  private String druid02driverClass;
}
