package com.robert;

import com.robert.config.StringToUserConverter;
import com.robert.config.StringToUserPropertyEditor;
import com.robert.entity.User;
import com.robert.service.UserService;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author robert
 * @date 2022/1/27
 */
//@ComponentScan(value = "com.robert", excludeFilters = {@ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE,
//        classes = UserService.class)})
//@ComponentScan(value = "com.robert", includeFilters = {@ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE,
//        classes = UserService.class)})
@ComponentScan("com.robert")
public class AppConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    public ApplicationListener applicationListener() {
        return new ApplicationListener() {
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println("接收到了一个事件" + event);
            }
        };
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> propertyEditorMap = new HashMap<Class<?>, Class<? extends PropertyEditor>>();

        propertyEditorMap.put(User.class, StringToUserPropertyEditor.class);
        customEditorConfigurer.setCustomEditors(propertyEditorMap);
        return customEditorConfigurer;
    }

    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(Collections.singleton(new StringToUserConverter()));
        return conversionServiceFactoryBean;
    }
}
