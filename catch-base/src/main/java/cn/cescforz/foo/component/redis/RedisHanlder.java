package cn.cescforz.foo.component.redis;

import cn.cescforz.foo.properties.SystemProperties;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: redis处理类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-09 08:55
 */
@Component
@EnableScheduling //开启定时器功能
public class RedisHanlder<K,V> {


    @Resource
    private RedisTemplate<K, V> redisTemplate;
    @Resource
    private SystemProperties systemProperties;

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;




    public boolean existsKey(K key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     * @param oldKey
     * @param newKey
     */
    public void renameKey(K oldKey, K newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(K oldKey, K newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除key
     * @param key
     */
    public void deleteKey(K key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     * @param keys
     */
    public void deleteKey(K... keys) {
        Set<K> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     * @param keys
     */
    public void deleteKey(Collection<K> keys) {
        Set<K> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(K key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     * @param key
     * @param date
     */
    public void expireKeyAt(K key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(K key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     * @param key
     */
    public void persistKey(K key) {
        redisTemplate.persist(key);
    }


    /**
     * <p>Description: 向redis消息队列频道发布消息</p>
     * @param message 消息
     */
    public void sendMessage(Object message){
        redisTemplate.convertAndSend(systemProperties.getRedisAisle(),message);
    }



    /**
     * 对hash类型的数据操作
     * @return HashOperations<K, HK, HV>
     */
    public <HK, HV> HashOperations<K, HK, HV> handleHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     * @return ValueOperations<K, V>
     */
    public ValueOperations<K, V> handleStr() {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     * @return ListOperations<K, V>
     */
    public ListOperations<K, V> handleList() {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     * @return SetOperations<K, V>
     */
    public SetOperations<K, V> handleSet() {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     * @return ZSetOperations<K, V>
     */
    public ZSetOperations<K, V> handleZSet() {
        return redisTemplate.opsForZSet();
    }
}
