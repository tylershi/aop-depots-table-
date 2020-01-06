package com.example.table.depots.exception;


import com.example.table.exception.BizException;
import com.example.table.exception.SystemEvent;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 13:31
 * @Version 1.0
 * @Description 格式化表后缀名称异常
 */
public class FormatTableSuffixException extends BizException {

  public FormatTableSuffixException() {
    super(SystemEvent.FORMAT_TABLE_SUFFIX_ERROR.getId(),
        SystemEvent.FORMAT_TABLE_SUFFIX_ERROR.getDetails());
  }
}
