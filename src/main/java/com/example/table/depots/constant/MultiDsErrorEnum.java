package com.example.table.depots.constant;

import lombok.Getter;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:22
 * @Version 1.0
 * @Description
 */
@Getter
public enum MultiDsErrorEnum {

  ROUTING_FIELD_ARGS_ISNULL(0, "多数据源路由键为空"),

  LOADING_STRATEGY_UN_MATCH(1, "路由组件加载和配置文件不匹配"),

  FORMAT_TABLE_SUFFIX_ERROR(2, "格式化表后缀异常"),

  PARAMS_NOT_CONTAINS_ROUTING_FIELD(3, "入参中不包含路由字段");

  private Integer code;

  private String msg;

  MultiDsErrorEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
