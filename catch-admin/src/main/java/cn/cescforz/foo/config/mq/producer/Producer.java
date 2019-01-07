package cn.cescforz.foo.config.mq.producer;



/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: RocketMQ生产者消息处理抽象类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-01 12:42
 */
public abstract class Producer<T> {


     public abstract void sendMsg(T t,String topic, String tag, String key);

     public abstract void sendTxMsg(T t,String topic, String tag, String key);

}

