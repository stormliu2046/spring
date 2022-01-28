package com.robert;

import com.robert.service.UserInterface;
import com.robert.service.UserService;
import com.robert.spring.RobertApplicationContext;

/**
 * @author robert
 * @date 2022/1/27
 */
public class Test {

    public static void main(String[] args) {

        RobertApplicationContext context = new RobertApplicationContext(AppConfig.class);
//        UserService userService = (UserService) context.getBean("userService");
//        UserService userService2 = (UserService) context.getBean("userService");
//        UserService userService3 = (UserService) context.getBean("userService");
//
//        System.out.println(userService);
//        System.out.println(userService2);
//        System.out.println(userService3);
        UserInterface userService = (UserInterface) context.getBean("userService");
        userService.test();
    }
}
