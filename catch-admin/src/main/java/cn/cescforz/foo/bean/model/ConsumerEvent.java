package cn.cescforz.foo.bean.model;

import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: </p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-03 09:52
 */
@Data
public class ConsumerEvent extends ApplicationEvent {
    private static final long serialVersionUID = -1475034100754784869L;

    private DefaultMQPushConsumer consumer;
    private List<MessageExt> msgs;

    public ConsumerEvent(List<MessageExt> msgs, DefaultMQPushConsumer consumer) throws Exception {
        super(msgs);
        this.consumer = consumer;
        this.setMsgs(msgs);
    }
}
