package cloud.tianai.order.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/30 18:04
 * @Description: list相关 工具类
 */
public class ListUtils {

    /**
     * collection 转 list
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> List<T> collectionToList(Collection<T> collection) {
        if(collection == null) {
            return Collections.emptyList();
        }
        if(collection instanceof List) {
            return (List<T>) collection;
        }
        return new ArrayList<>(collection);

    }
}
