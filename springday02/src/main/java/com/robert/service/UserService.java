package com.robert.service;

import com.robert.spring.*;

/**
 * @author robert
 * @date 2022/1/27
 */
@Component("userService")
@Scope("singleton")
public class UserService implements UserInterface, BeanNameAware, InitializingBean {

    @Autowired
    private OrderService orderService;
    @RobertValue("xxx")
    private String test;

    private String beanName;

    public void test() {
        System.out.println(orderService);
        System.out.println(test);
        System.out.println("test");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("正在初始化");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
