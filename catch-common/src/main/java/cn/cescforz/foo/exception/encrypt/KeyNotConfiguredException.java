package cn.cescforz.foo.exception.encrypt;

import lombok.NoArgsConstructor;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: 未配置KEY运行时异常</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-23 21:00
 */
@NoArgsConstructor
public class KeyNotConfiguredException extends RuntimeException{

    public KeyNotConfiguredException(String message) {
        super(message);
    }
}
