package com.example.table.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author tyler.shi
 * @Date 2020/1/6 17:49
 * @Version 1.0
 * @Description
 */
@ApiModel
@Data
public class ListOrderCondition {

  @ApiModelProperty(value = "订单id集合")
  private List<Long> orderIds;

  @ApiModelProperty(value = "用户id集合")
  private List<Long> userIds;

}
