package cloud.tianai.order.core.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:00
 * @Description: 订单详情信息
 */
@Data
@TableName("order_detail")
public class OrderDetailDO {
    /** 订单详情ID. */
    @TableId
    private String orderDetailId;
    /** 订单ID. */
    private String oid;
    /** 商品ID. */
    private String spuId;
    /** skuID. */
    private String skuId;
    /** 商品SKU. */
    private String sku;
    /** 商品SKU描述. */
    private String skuDesc;
    /** 商品名称. */
    private String productName;
    /** 商品价格. */
    private Long productPrice;
    /** 商品数量. */
    private Integer productQuantity;
    /** 商品图标. */
    private String productIcon;
    /** 商品商品条形码. */
    private String productBarcode;
    /** 创建时间. */
    private Date createTime;
    /** 更新时间. */
    private Date updateTime;
}
