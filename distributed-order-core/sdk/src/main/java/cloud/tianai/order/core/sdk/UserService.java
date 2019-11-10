package cloud.tianai.order.core.sdk;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 20:27
 * @Description: 用户相关sdk接口
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    ApiResponse<BasicUserInfoDTO> getBasicUserInfo(String uid);

    /**
     * 获取商户信息
     * @param bid
     * @return
     */
    ApiResponse<BasicBusinessInfoDTO> getBasicBusinessInfo(String bid);
}
