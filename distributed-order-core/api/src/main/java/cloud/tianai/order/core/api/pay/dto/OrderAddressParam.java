package cloud.tianai.order.core.api.pay.dto;


import lombok.Data;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 18:45
 * @Description: 订单地址参数
 */
@Data
public class OrderAddressParam {
    /** 省. */
    private String province;
    /** 市. */
    private String city;
    /** 区. */
    private String area;
    /** 街道. */
    private String street;
    /** 地址详情. */
    private String addressDesc;
}
