package cloud.tianai.order.core.pay.interceptor;

import cloud.tianai.order.common.holder.ApplicationContextHolder;
import cloud.tianai.order.common.util.ListUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/10 14:00
 * @Description: 订单创建时的拦截器操作
 */
@Component
public class OrderCreateInterceptorHolder implements ApplicationContextAware, InitializingBean {

    private List<OrderCreateInterceptor> interceptors;
    private ApplicationContext applicationContext;

    public List<OrderCreateInterceptor> getInterceptors() {
        return this.interceptors;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, OrderCreateInterceptor> readInterceptors = this.applicationContext.getBeansOfType(OrderCreateInterceptor.class);
        if(CollectionUtils.isEmpty(readInterceptors)) {
            this.interceptors = Collections.emptyList();
        } else {
            Collection<OrderCreateInterceptor> values = readInterceptors.values();
            List<OrderCreateInterceptor> valuesList = ListUtils.collectionToList(values);
            // 排序
            valuesList.sort(new AnnotationAwareOrderComparator());
            this.interceptors = valuesList;
        }

    }
}
