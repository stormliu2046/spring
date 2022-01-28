package com.robert.service;

import com.robert.spring.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @author robert
 * @date 2022/1/27
 */
public class RobertValueBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(RobertValue.class)) {
                RobertValue robertValueAnnotation = field.getAnnotation(RobertValue.class);
                field.setAccessible(true);
                field.set(bean, robertValueAnnotation.value());
            }
        }
        return bean;
    }
}
