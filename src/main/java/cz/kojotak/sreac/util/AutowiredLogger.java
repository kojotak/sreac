/**
 * 
 */
package cz.kojotak.sreac.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * Autowires SLF4J logger.
 */
public class AutowiredLogger implements BeanPostProcessor {

	@Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (Logger.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Autowired.class) ) {
                Logger logger = LoggerFactory.getLogger(bean.getClass());
            	ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, logger);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
