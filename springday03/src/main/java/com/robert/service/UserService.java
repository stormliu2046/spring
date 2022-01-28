package com.robert.service;

import com.robert.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author robert
 * @date 2022/1/27
 */
//@Component("userService")
public class UserService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("xxx")
    private User user;

    public void test() {
        applicationContext.publishEvent("kkk");
        System.out.println("test");
        System.out.println(user);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
