package cloud.tianai.order.core.interceptor;

import cloud.tianai.order.api.interceptor.BuyerNameOrderCreateInterceptor;
import cloud.tianai.order.api.interceptor.UidOrderCreateInterceptor;
import cloud.tianai.order.core.holder.ApplicationContextHolder;
import cloud.tianai.order.core.util.ListUtils;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.context.ApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 18:10
 * @Description: 拦截器持有者
 */
public class OrderInterceptorChainHolder {


    private static Map<Class<? extends OrderInterceptor>, OrderInterceptorChain> cache = new HashMap<>(16);

    /** 空的拦截器链. */
    private static OrderInterceptorChain EMPTY = new OrderInterceptorChain(new ArrayList<>(0));

    public static <T,R> OrderInterceptorChain<T,R> getChain(Class<? extends OrderInterceptor> clazz) {
        OrderInterceptorChain cacheResult = cache.get(clazz);
        if(Objects.nonNull(cacheResult)) {
            return (OrderInterceptorChain<T,R>) cacheResult;
        }
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        Map<String, ? extends OrderInterceptor> beansOfType = applicationContext.getBeansOfType(clazz);
        if(CollectionUtils.isEmpty(beansOfType)) {
            return EMPTY;
        }
        Collection<? extends OrderInterceptor> values = beansOfType.values();

        List<? extends OrderInterceptor> orderInterceptors = ListUtils.collectionToList(values);
        orderInterceptors.sort(new AnnotationAwareOrderComparator());
        try {
            final OrderInterceptorChain<T,R> result = new OrderInterceptorChain<T,R>();
            orderInterceptors.forEach(result::addInterceptor);
            cache.put(clazz, result);
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("组装拦截器失败， 传入的泛型不能和获取到的泛型相匹配");
        }
    }
}
