package com.example.table.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 9:18
 * @Version 1.0
 * @Description
 */
@Data
@ApiModel
public class OrderRequest {

  @Min(value = 1, message = "订单id不合法")
  @ApiModelProperty(name = "订单id")
  private Long orderId;

  @Min(value = 1, message = "用户id不合法")
  @ApiModelProperty(name = "用户id")
  private Long userId;

  @Min(value = 0, message = "订单金额不合法")
  @ApiModelProperty(name = "订单金额")
  private Double money;

}
