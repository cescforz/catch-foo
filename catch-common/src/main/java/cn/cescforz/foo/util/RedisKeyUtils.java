package cn.cescforz.foo.util;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: redis主键生成策略工具类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-09 09:12
 */
public final class RedisKeyUtils {


    private RedisKeyUtils(){throw new AssertionError();}


    public static String keyGenerator(String tableName,String majorKey,String majorKeyValue,String column){
        return StringCustomUtils.assembleStr(tableName,":",majorKey,":",majorKeyValue,":",column);
    }


    public static String keyGenerator(String tableName,String majorKey,String majorKeyValue){
        return StringCustomUtils.assembleStr(tableName,":",majorKey,":",majorKeyValue);
    }

}
