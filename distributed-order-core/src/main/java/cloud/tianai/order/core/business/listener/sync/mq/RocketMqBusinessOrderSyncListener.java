package cloud.tianai.order.core.business.listener.sync.mq;

import cloud.tianai.order.core.business.listener.sync.AbstractBusinessOrderSyncListener;
import cloud.tianai.order.core.util.canal.CanalResultData;
import cloud.tianai.order.core.util.canal.CanalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableConfigurationProperties(RocketMqConfigProperties.class)
@ConditionalOnProperty(prefix = "order.sync.mq", name = "type", havingValue = "rocketMQ", matchIfMissing = true)
public class RocketMqBusinessOrderSyncListener extends AbstractBusinessOrderSyncListener implements MessageListenerConcurrently {

    @Autowired
    private RocketMqConfigProperties properties;

    private DefaultMQPushConsumer consumer;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(properties.getConsumerGroup());
        consumer.setNamesrvAddr(properties.getNameServer());
        // 订阅topic
        // 要是匹配所有tag 则写 * ， 匹配多个tag 中间用 || 隔开， 例如 tagA || tagB || tagC
        consumer.subscribe(properties.getTopic(), properties.getTag());
        // 设置消费位点
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeThreadMin(properties.getConsumerThreadMin());
        consumer.setConsumeThreadMax(properties.getConsumerThreadMax());
        // 设置为集群消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置消费失败时最大重试次数
        consumer.setMaxReconsumeTimes(16);
        consumer.registerMessageListener(this);
        // 顺序消息每次的重试时间
        consumer.setSuspendCurrentQueueTimeMillis(2000);
        consumer.start();
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        MessageExt message = msgs.get(0);
        String msgId = message.getMsgId();
        byte[] bodyByte = message.getBody();
        String bodyJson;
        try {
            bodyJson = new String(bodyByte, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        CanalResultData data = CanalUtils.converterForJson(bodyJson);
        Map<String, String> updateData = data.getData().get(0);
        String oid = updateData.get("oid");
        log.info("msgId: {}, oid: {}", msgId, oid);
        try {
            super.onListener(oid);
        } catch (Exception e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
