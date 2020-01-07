package com.example.table.service;

import com.example.table.request.OrderRequest;
import com.example.table.response.OrderVo;
import java.util.List;

/**
 * @Author tyler.shi
 * @Date 2020/1/5 11:08
 * @Version 1.0
 * @Description
 */
public interface OrderService {

  void saveOrder(OrderRequest request);

  void deleteOrder(Long id);

  void updateOrder(OrderRequest request);

  OrderVo findOrder(Long id);

  /**
   * @Description 含有路由字段的批处理
   */
  List<OrderVo> findOrderByOrderIdsV1(List<Long> orderIds);

  /**
   * @Description 含有路由字段的批处理
   */
  List<OrderVo> findOrderByOrderIdsV2(List<Long> orderIds);

  /**
   * @Description 含有路由字段的批处理
   */
  List<OrderVo> findOrderByOrderIdsV3(List<Long> orderIds);

  /**
   * @Description 不含路由字段的单操作
   */
  List<OrderVo> findOrderByUserIdV1(Long userId);

  /**
   * @Description 不含路由字段的单操作
   */
  List<OrderVo> findOrderByUserIdV2(Long userId);

  /**
   * @Description 不含路有字段的批处理
   */
  List<OrderVo> findOrderByUserIdsAndPage(List<Long> userIds, int pageNum, int pageSize);


}
