package cloud.tianai.order.core.holder;

import lombok.experimental.Accessors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 17:45
 * @Description: applicationContext 持有者
 */
@Component
@Accessors
public class ApplicationHolder implements
        EnvironmentAware,
        BeanFactoryAware,
        ResourceLoaderAware,
        ServletContextAware,
        ApplicationContextAware,
        NotificationPublisherAware,
        ApplicationEventPublisherAware {

    private static ApplicationContext applicationContext;
    private static BeanFactory beanFactory;
    private static Environment environment;
    private static ResourceLoader resourceLoader;
    private static NotificationPublisher notificationPublisher;
    private static ServletContext servletContext;
    private static ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        applicationContext = app;
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        beanFactory = bf;
    }

    @Override
    public void setEnvironment(Environment env) {
        environment = env;
    }

    @Override
    public void setResourceLoader(ResourceLoader rl) {
        resourceLoader = rl;
    }


    @Override
    public void setNotificationPublisher(NotificationPublisher nfp) {
        notificationPublisher = nfp;
    }

    @Override
    public void setServletContext(ServletContext sc) {
        servletContext = sc;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        applicationEventPublisher = eventPublisher;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static NotificationPublisher getNotificationPublisher() {
        return notificationPublisher;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }
}
