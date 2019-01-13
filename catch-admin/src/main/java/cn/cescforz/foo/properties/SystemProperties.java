package cn.cescforz.foo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-11 17:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "system-config")
public class SystemProperties {

    private String redisAisle;
}
