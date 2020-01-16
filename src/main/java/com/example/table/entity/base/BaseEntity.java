package com.example.table.entity.base;

import javax.persistence.Table;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:19
 * @Version 1.0
 * @Description
 */
@Data
public class BaseEntity {

  /**
   * 表名后缀
   */
  private String tableSuffix;
  /**
   * 实体表名字 = 逻辑表名 + 表名后缀
   */
  private String realTableName;

  public String getRealTableName() {
    Class clazz = this.getClass();
    Table annotation = (Table) clazz.getAnnotation(Table.class);
    String tableName;
    if (annotation != null) {
      String name = annotation.name();
      if (StringUtils.isNotBlank(name)) {
        tableName = name;
      } else {
        tableName = clazz.getSimpleName().toLowerCase();
      }
    } else {
      tableName = clazz.getSimpleName().toLowerCase();
    }
    return tableName + tableSuffix;
  }

}
