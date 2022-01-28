package com.robert.config;

import com.robert.service.UserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author robert
 * @date 2022/1/27
 */
@Component
public class RobertFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        UserService userService = new UserService();
        return userService;
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }
}
