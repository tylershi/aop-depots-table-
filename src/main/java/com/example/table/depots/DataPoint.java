package com.example.table.depots;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tyler.shi
 * @Date 2020/1/7 10:10
 * @Version 1.0
 * @Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataPoint {

  private String dataSource;

  private String tableSuffix;

}
