package cn.cescforz.foo.util;

import cn.cescforz.foo.exception.encrypt.KeyNotConfiguredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>©2018 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2018-12-24 23:41
 */
public final class CheckUtils {

    private CheckUtils(){throw new AssertionError();}

    private static final Logger LOG = LoggerFactory.getLogger(CheckUtils.class);

    public static String checkAndGetKey(String k1,String k2,String keyName){
        if(StringCustomUtils.isBlank(k1) && StringCustomUtils.isBlank(k2)){
            throw new KeyNotConfiguredException(String.format("%s is not configured (未配置%s)", keyName,keyName));
        }
        if(k1==null) {
            return k2;
        }
        return k1;
    }
}
