package cloud.tianai.order.core.listener.businessordersync.mq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rocketmq.order-sync")
public class RocketMqConfigProperties {

    /** rocketMQ 地址. */
    private String nameServer;
    /** 消费者组名称. */
    private String consumerGroup;
    /** 订阅topic. */
    private String topic;
    /** 最小消费线程数. */
    private Integer consumerThreadMin;
    /** 最大消费线程数. */
    private Integer consumerThreadMax;
    /** tag标签 要是匹配所有tag 则写 * ， 匹配多个tag 中间用 || 隔开， 例如 tagA || tagB || tagC . */
    private String tag;

    /**商业版的话需要填accessKey 和securityKey*/
    private String accessKey;
    private String secretKey;
}
