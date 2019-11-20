package cloud.tianai.order.core.pay.test;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.sdk.UserService;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TestUserService implements UserService {
    @Override
    public ApiResponse<BasicUserInfoDTO> getBasicUserInfo(String uid) {
        BasicUserInfoDTO basicUserInfoDTO = new BasicUserInfoDTO();
        basicUserInfoDTO.setUid(uid);
        return ApiResponse.ofSuccess(basicUserInfoDTO);
    }

    @Override
    public ApiResponse<BasicBusinessInfoDTO> getBasicBusinessInfo(String bid) {
        BasicBusinessInfoDTO basicBusinessInfoDTO = new BasicBusinessInfoDTO();
        basicBusinessInfoDTO.setBid(bid);
        return ApiResponse.ofSuccess(basicBusinessInfoDTO);
    }
}
