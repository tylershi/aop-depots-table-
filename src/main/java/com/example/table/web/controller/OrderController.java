package com.example.table.web.controller;

import com.example.table.exception.BaseResponse;
import com.example.table.exception.SystemEvent;
import com.example.table.request.ListOrderCondition;
import com.example.table.request.OrderRequest;
import com.example.table.response.OrderVo;
import com.example.table.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 18:14
 * @Version 1.0
 * @Description
 */
@Api(value = "OrderController", tags = "订单相关接口")
@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @ApiOperation("保存订单")
  @PostMapping("/save")
  public BaseResponse<String> saveOrder(@Valid @RequestBody OrderRequest request) {
    orderService.saveOrder(request);
    return BaseResponse.success("保存订单成功");
  }

  @ApiOperation("删除订单")
  @GetMapping("/delete/{orderId}")
  public BaseResponse<String> deleteOrder(@Valid @PathVariable Long orderId) {
    if (orderId < 1) {
      BaseResponse.error(SystemEvent.ORDER_ID_IS_INVALID);
    }
    orderService.deleteOrder(orderId);
    return BaseResponse.success("删除订单成功");
  }

  @ApiOperation("更新订单")
  @PostMapping("/update")
  public BaseResponse<String> updateOrder(@Valid @RequestBody OrderRequest request) {
    orderService.updateOrder(request);
    return BaseResponse.success("更新订单成功");
  }

  @ApiOperation("查找订单")
  @PostMapping("/find/{orderId}")
  public BaseResponse<OrderVo> findOrder(@Valid @PathVariable Long orderId) {
    if (orderId < 1) {
      BaseResponse.error(SystemEvent.ORDER_ID_IS_INVALID);
    }
    OrderVo order = orderService.findOrder(orderId);
    return BaseResponse.success(order);
  }

  @ApiOperation("根据批量订单id查找订单")
  @PostMapping("/list/orderIds/v1")
  public BaseResponse<List<OrderVo>> findOrderByOrderIdsV1(
      @RequestBody ListOrderCondition condition) {
    List<Long> orderIds = condition.getOrderIds();
    if (CollectionUtils.isEmpty(orderIds)) {
      return null;
    }
    List<OrderVo> data = orderService.findOrderByOrderIdsV1(orderIds);
    return BaseResponse.success(data);
  }

  @ApiOperation("根据批量订单id查找订单")
  @PostMapping("/list/orderIds/v2")
  public BaseResponse<List<OrderVo>> findOrderByOrderIdsV2(
      @RequestBody ListOrderCondition condition) {
    List<Long> orderIds = condition.getOrderIds();
    if (CollectionUtils.isEmpty(orderIds)) {
      return null;
    }
    List<OrderVo> data = orderService.findOrderByOrderIdsV2(orderIds);
    return BaseResponse.success(data);
  }

  @ApiOperation("根据批量订单id查找订单")
  @PostMapping("/list/orderIds/v3")
  public BaseResponse<List<OrderVo>> findOrderByOrderIdsV3(
      @RequestBody ListOrderCondition condition) {
    List<Long> orderIds = condition.getOrderIds();
    if (CollectionUtils.isEmpty(orderIds)) {
      return null;
    }
    List<OrderVo> data = orderService.findOrderByOrderIdsV3(orderIds);
    return BaseResponse.success(data);
  }


  @ApiOperation("根据用户id查找订单")
  @PostMapping("/list/userId/{userId}")
  public BaseResponse<List<OrderVo>> findOrderByUserIdsV1(
      @PathVariable(value = "userId") Long userId) {
    if (userId < 1) {
      return BaseResponse.success(null);
    }
    List<OrderVo> data = orderService.findOrderByUserIdV1(userId);
    return BaseResponse.success(data);
  }

}
