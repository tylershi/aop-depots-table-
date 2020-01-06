package com.example.table.entity.base;

import javax.persistence.Table;
import lombok.Data;

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
      tableName = annotation.name();
    } else {
      tableName = clazz.getSimpleName();
    }
    return tableName + tableSuffix;
  }

}
