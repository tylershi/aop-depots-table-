package com.example.table.depots.support;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author tyler.shi
 * @Date 2020/1/4 15:20
 * @Version 1.0
 * @Description
 */
@Data
@Component
@ConfigurationProperties(prefix = "routing")
public class RoutingFieldProperties {

  private Map<String, String> routingFields;

}
