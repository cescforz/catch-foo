package cn.cescforz.foo.component.mq.impl;

import cn.cescforz.foo.bean.domain.ErrorLog;
import cn.cescforz.foo.bean.model.ConsumerEvent;
import cn.cescforz.foo.component.mq.Consumer;
import cn.cescforz.foo.constant.SystemConstants;
import cn.cescforz.foo.dao.ErrorLogDao;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>©2019 Cesc. All Rights Reserved.</p>
 * <p>Description: RocketMQ消费者处理类</p>
 *
 * @author cesc
 * @version v1.0
 * @date Create in 2019-01-03 15:02
 */
@Slf4j
@Component
public class ConsumerHandler extends Consumer {

    private ErrorLogDao errorLogDao;

    @Autowired
    public ConsumerHandler(ErrorLogDao errorLogDao) {
        this.errorLogDao = errorLogDao;
    }

    @Override
    @EventListener(condition = "#event.msgs[0].topic=='CATCH_FOO_TOPIC' && #event.msgs[0].tags=='HANDLE_EXCEPTION_TAG'")
    public void handleExceptionListener(ConsumerEvent event) {
        try {
            List<MessageExt> msgs = event.getMsgs();
            log.info("消息条数:{}",msgs.size());
            for (MessageExt msg : msgs) {
                String body = new String(msg.getBody(), SystemConstants.CHARSET_UTF_8);
                log.info("消费消息：{}",body);
                ErrorLog errorLog = JSON.parseObject(body, ErrorLog.class);
                errorLogDao.insert(errorLog);
            }
        } catch (Exception e) {
            log.error("消费消息出错:",e);
        }
    }

}
