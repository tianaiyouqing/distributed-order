package cloud.tianai.order.core.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 17:45
 * @Description: applicationContext 持有者
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        applicationContext = app;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
