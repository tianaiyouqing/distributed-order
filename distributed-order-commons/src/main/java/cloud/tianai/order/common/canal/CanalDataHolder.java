package cloud.tianai.order.common.canal;

import org.springframework.core.NamedThreadLocal;

import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 11:45
 * @Description: 用来存储canal传来的data数据
 */
public class CanalDataHolder {

    private static NamedThreadLocal<CanalResultData> namedThreadLocal = new NamedThreadLocal<>("canal resultDATA ThreadLocal");


    public static void set(CanalResultData canalResultData) {
        if(Objects.isNull(canalResultData)) {
            remove();
        }else {
            namedThreadLocal.set(canalResultData);
        }
    }

    public static CanalResultData get() {
        return namedThreadLocal.get();
    }

    public static void remove() {
        namedThreadLocal.remove();
    }
}
