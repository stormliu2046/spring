package com.robert.entity;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @author robert
 * @date 2022/1/27
 */
@Scope
@Lazy
public class User {

    private String name;

    public void init() {
        System.out.println("init...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
