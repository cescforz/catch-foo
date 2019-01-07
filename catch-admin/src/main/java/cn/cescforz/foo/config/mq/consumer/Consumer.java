package cn.cescforz.foo.config.mq.consumer;

import cn.cescforz.foo.bean.model.ConsumerEvent;


/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: RocketMQ消费者处理抽象类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-01 12:45
 */
public abstract class Consumer {

    /**
     * <p>Description: 处理异常的消费者</p>
     * @param event
     * @return void
     */
   public abstract void handleExceptionListener(ConsumerEvent event);
}

