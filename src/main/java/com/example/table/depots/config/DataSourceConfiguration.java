package com.example.table.depots.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.table.depots.datasource.MultiDataSource;
import com.example.table.depots.support.DruidProperties;
import com.example.table.depots.support.DsRoutingSetProperties;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 10:22
 * @Version 1.0
 * @Description 数据源配置
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({DsRoutingSetProperties.class, DruidProperties.class})
@MapperScan(basePackages = "com.example.table.dao")
public class DataSourceConfiguration {

  private final DsRoutingSetProperties dsRoutingSetProperties;

  private final DruidProperties druidProperties;

  @Autowired
  public DataSourceConfiguration(
      DsRoutingSetProperties dsRoutingSetProperties,
      DruidProperties druidProperties) {
    this.dsRoutingSetProperties = dsRoutingSetProperties;
    this.druidProperties = druidProperties;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.druid00")
  public DataSource dataSource00() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUsername(druidProperties.getDruid00username());
    dataSource.setPassword(druidProperties.getDruid00passwrod());
    dataSource.setUrl(druidProperties.getDruid00jdbcUrl());
    dataSource.setDriverClassName(druidProperties.getDruid00driverClass());
    return dataSource;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.druid01")
  public DataSource dataSource01() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUsername(druidProperties.getDruid01username());
    dataSource.setPassword(druidProperties.getDruid01passwrod());
    dataSource.setUrl(druidProperties.getDruid01jdbcUrl());
    dataSource.setDriverClassName(druidProperties.getDruid01driverClass());
    return dataSource;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.druid02")
  public DataSource dataSource02() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setUsername(druidProperties.getDruid02username());
    dataSource.setPassword(druidProperties.getDruid02passwrod());
    dataSource.setUrl(druidProperties.getDruid02jdbcUrl());
    dataSource.setDriverClassName(druidProperties.getDruid02driverClass());
    return dataSource;
  }

  @Bean("multiDataSource")
  public MultiDataSource dataSource() {
    MultiDataSource multiDataSource = new MultiDataSource();

    Map<Object, Object> targetDataSources = new HashMap<>();
    targetDataSources.put("dataSource00", dataSource00());
    targetDataSources.put("dataSource01", dataSource01());
    targetDataSources.put("dataSource02", dataSource02());

    //把多个数据有和多数据源进行关联
    multiDataSource.setTargetDataSources(targetDataSources);

    multiDataSource.setDefaultTargetDataSource(dataSource00());

    Map<Integer, String> setMappings = new HashMap<>();
    setMappings.put(0, "dataSource00");
    setMappings.put(1, "dataSource01");
    setMappings.put(2, "dataSource02");
    dsRoutingSetProperties.setDataSourceKeysMapping(setMappings);

    return multiDataSource;

  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(
      @Qualifier("multiDataSource") MultiDataSource multiDataSource)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(multiDataSource);
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)
      throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  @Bean
  public DataSourceTransactionManager transactionManager(
      @Qualifier("multiDataSource") MultiDataSource multiDataSource) {
    return new DataSourceTransactionManager(multiDataSource);
  }

}
