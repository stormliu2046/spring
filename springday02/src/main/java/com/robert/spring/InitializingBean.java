package com.robert.spring;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
