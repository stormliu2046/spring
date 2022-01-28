package com.robert.spring;

import com.robert.AppConfig;
import com.sun.xml.internal.ws.util.StringUtils;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

/**
 * @author robert
 * @date 2022/1/27
 */
public class RobertApplicationContext {

    private Class<?> configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> singletonObjectsMap = new HashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public RobertApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        // 扫描
        scan(configClass);

        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanName, beanDefinition);
                singletonObjectsMap.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getType();
        Object instance = null;

        try {
            // 先简单的无参构造
            instance = clazz.getConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    private void scan(Class<?> configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value();
            path = path.replace(".", "/");
            ClassLoader classLoader = RobertApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            scanFile(classLoader, file);
        }
    }

    private void scanFile(ClassLoader classLoader, File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                analysis(classLoader, f);
            }
        }
    }

    private void analysis(ClassLoader classLoader, File f) {
        String absolutePath = f.getAbsolutePath();
        System.out.println(absolutePath);

        absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.lastIndexOf(".class"));
        absolutePath = absolutePath.replace("\\", ".");
        System.out.println(absolutePath);

        try {
            Class<?> clazz = classLoader.loadClass(absolutePath);
            if (clazz.isAnnotationPresent(Component.class)) {
                if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                    BeanPostProcessor instance = (BeanPostProcessor) clazz.getConstructor().newInstance();
                    beanPostProcessorList.add(instance);
                }
                Component componentAnnotation = clazz.getAnnotation(Component.class);
                String beanName = componentAnnotation.value();
                if ("".equals(beanName)) {
                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                }
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setType(clazz);
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                    beanDefinition.setScope(scopeAnnotation.value());
                } else {
                    beanDefinition.setScope("singleton");
                }
                beanDefinitionMap.put(beanName, beanDefinition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
        // 如果beanDefinition没有，则表示不存在这个bean
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new NullPointerException();
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if ("singleton".equals(beanDefinition.getScope())) {
            Object singletonBean = singletonObjectsMap.get(beanName);
            if (Objects.isNull(singletonBean)) {
                singletonBean = createBean(beanName, beanDefinition);
                singletonObjectsMap.put(beanName, beanDefinition);
            }
            return singletonBean;
        } else {
            return createBean(beanName, beanDefinition);
        }
    }
}
