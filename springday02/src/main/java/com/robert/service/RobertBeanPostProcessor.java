package com.robert.service;

import com.robert.spring.BeanPostProcessor;
import com.robert.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author robert
 * @date 2022/1/27
 */
@Component
public class RobertBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        System.out.println("初始化后" + bean);
        if (beanName.equals("userService")) {
            Object proxyInstance = Proxy.newProxyInstance(RobertBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 切面逻辑
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
