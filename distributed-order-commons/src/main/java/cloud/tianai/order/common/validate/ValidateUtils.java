package cloud.tianai.order.common.validate;

import cloud.tianai.order.common.holder.ApplicationContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/20 16:38
 * @Description: 提取出Spring的校验框架， 用于在非Controller时使用
 */
public class ValidateUtils {

    private static LocalValidatorFactoryBean validator;

    private static LocalValidatorFactoryBean getValidator() {
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        if (validator == null) {
            synchronized (applicationContext) {
                if (validator == null) {
                    try {
                        validator = applicationContext.getBean(LocalValidatorFactoryBean.class);
                    } catch (BeansException e) {
                        // bean不存在, 手动创建一个
                        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
                        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
                        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
                        factoryBean.setApplicationContext(applicationContext);
                        // 调用初始化方法
                        factoryBean.afterPropertiesSet();
                        if (applicationContext instanceof GenericApplicationContext) {
                            ConfigurableListableBeanFactory beanFactory = ((GenericApplicationContext) applicationContext).getBeanFactory();
                            beanFactory.registerSingleton("validater", factoryBean);
                        }
                        validator = factoryBean;
                    }
                    return validator;
                }

            }
        }
        return validator;
    }

    /**
     * 校验参数
     *
     * @param obj
     * @param groups
     * @return
     */
    public static BindingResult validate(Object obj, Class<?>... groups) {
        LocalValidatorFactoryBean validator = getValidator();
        if (validator == null) {
            throw new IllegalArgumentException("validate 未找到， 查看是否初始化");
        }
        BindingResult bindingResult = new DirectFieldBindingResult(obj, obj.getClass().getSimpleName());

        validator.validate(obj, bindingResult, groups);

        return bindingResult;
    }
}
