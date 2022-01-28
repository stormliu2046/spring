package com.robert.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author robert
 * @date 2022/1/27
 */
@Order(3)
public class AOrder { //implements Ordered
//    public int getOrder() {
//        return 3;
//    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
