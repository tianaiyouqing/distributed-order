package cloud.tianai.order.core.business.listener.sync.mq;

import cloud.tianai.order.core.business.listener.sync.AbstractBusinessOrderSyncListener;
import cloud.tianai.order.core.business.listener.sync.AbstractCanalBusinessOrderSyncListener;
import cloud.tianai.order.core.util.canal.CanalResultData;
import cloud.tianai.order.core.util.canal.CanalUtils;
import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
@EnableConfigurationProperties(RocketMqConfigProperties.class)
@ConditionalOnProperty(
        prefix = "order.sync.mq",
        name = "type",
        havingValue = "ons",
        matchIfMissing = false)
public class OnsMqBusinessOrderSyncListener extends AbstractCanalBusinessOrderSyncListener
        implements MessageListener {

    @Autowired
    private RocketMqConfigProperties orderSyncProp;

    private ConsumerBean consumer;

    @PostConstruct
    public void init() {
        consumer = new ConsumerBean();
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, orderSyncProp.getConsumerGroup());
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        // 最大线程数
        properties.put(PropertyKeyConst.ConsumeThreadNums, 100);
        properties.put(PropertyKeyConst.AccessKey, orderSyncProp.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, orderSyncProp.getSecretKey());
        // TCP接入点
        properties.put(PropertyKeyConst.NAMESRV_ADDR, orderSyncProp.getNameServer());
        consumer.setProperties(properties);
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>(1);
        // --------------------------过滤器--------------------------------
        Subscription subscription = new Subscription();
        // 监听的topic
        subscription.setTopic(orderSyncProp.getTopic());
        // 监听的 tag
        subscription.setExpression(orderSyncProp.getTag());
        // --------------------------过滤器--------------------------------
        subscriptionTable.put(subscription, this);
        consumer.setSubscriptionTable(subscriptionTable);

        consumer.start();
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
        byte[] body = message.getBody();
        String msgID = message.getMsgID();
        String key = message.getKey();
        String bodyJson;
        try {
            bodyJson = new String(body, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        CanalResultData data = CanalUtils.converterForJson(bodyJson);
        try {
            consume(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
}
