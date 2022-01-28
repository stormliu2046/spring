package com.robert.config;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robert
 * @date 2022/1/27
 */
@Order(2)
public class BOrder { // implements Ordered
//    public int getOrder() {
//        return 2;
//    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public static void main(String[] args) {
        AOrder aOrder = new AOrder();
        BOrder bOrder = new BOrder();

//        OrderComparator comparator = new OrderComparator();
        AnnotationAwareOrderComparator comparator = new AnnotationAwareOrderComparator();
        System.out.println(comparator.compare(aOrder, bOrder));

        List<Object> list = new ArrayList<>();
        list.add(aOrder);
        list.add(bOrder);
        list.sort(comparator);

        System.out.println(list);
    }
}
