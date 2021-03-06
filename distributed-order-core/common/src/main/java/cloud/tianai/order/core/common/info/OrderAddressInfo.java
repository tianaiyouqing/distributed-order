package cloud.tianai.order.core.common.info;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:58
 * @Description: 地址信息
 */
@Data
@Accessors(chain = true)
public class OrderAddressInfo {
    /** 买家名称. */
    private String buyerName;
    /** 买家手机号. */
    private String buyerPhone;

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
