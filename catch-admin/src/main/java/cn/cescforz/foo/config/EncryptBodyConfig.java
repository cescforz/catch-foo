package cn.cescforz.foo.config;

import cn.cescforz.foo.constant.SystemConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 加密数据配置读取类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 21:49
 */
@Data
@Component
@ConfigurationProperties(prefix = "api.key")
public class EncryptBodyConfig {


    private String desKey;
    private String aesKey;
    private String encoding = SystemConstants.CHARSET_STR_UTF_8;
}
