package com.example.table.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tyler.shi
 * @Date 2020/1/6 13:49
 * @Version 1.0
 * @Description
 */
@Data
@Builder
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

  @ApiModelProperty(name = "订单id")
  private Long orderId;

  @ApiModelProperty(name = "用户id")
  private Long userId;

  @ApiModelProperty(name = "订单金额")
  private Double money;
}
