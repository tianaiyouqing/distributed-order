package cloud.tianai.order.core.util.enums;

/**
 * @Author: 天爱有情
 * @Date: 2019/9/3 12:24
 * @Description: 枚举工具包
 */
public class EnumUtils {

    /**
     * 通过code获取枚举
     *
     * @param code      code码
     * @param enumClass 对应的枚举的class
     * @param <T>
     * @return 返回对应的枚举
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
