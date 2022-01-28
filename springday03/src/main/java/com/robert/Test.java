package com.robert;

import com.robert.config.StringToUserConverter;
import com.robert.config.StringToUserPropertyEditor;
import com.robert.entity.User;
import com.robert.service.UserService;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * @author robert
 * @date 2022/1/27
 */
public class Test {

    public static void main(String[] args) throws IOException {

        SimpleMetadataReaderFactory simpleMetadataReaderFactory = new SimpleMetadataReaderFactory();

        MetadataReader metadataReader = simpleMetadataReaderFactory.getMetadataReader("com.robert.service.UserService");

        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        System.out.println(classMetadata.getClassName());

        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        for (String annotationType : annotationMetadata.getAnnotationTypes()) {
            System.out.println(annotationType);
        }

//        testTypeConverter();

//        testConversionService();

//        convertStringToUser();

        publicEvent();

//        getEnvironmentProperties();

//        getResource();
//        messageResourceGet();
//        defaultListableBeanFactoryRegister();

//        classpathBeanDefinitionReaderScan();
//        xmlBeanDefinitionReaderLoad();
//
//        annotationBeanDefinitionReaderRegister();
//        registerBeanDefinition();
    }

    private static void testTypeConverter() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        typeConverter.registerCustomEditor(User.class, new StringToUserPropertyEditor());
        typeConverter.setConversionService((ConversionService) context.getBean("conversionService"));
        User user = typeConverter.convertIfNecessary("1", User.class);
        System.out.println(user);
    }

    private static void testConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToUserConverter());
        User user = conversionService.convert("1", User.class);
        System.out.println(user);
    }

    private static void convertStringToUser() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        userService.test();

//        StringToUserPropertyEditor propertyEditor = new StringToUserPropertyEditor();
//        propertyEditor.setAsText("1");
//        User value = (User) propertyEditor.getValue();
//        System.out.println(value);
    }

    private static void publicEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean("userService", UserService.class);
//        UserService userService = context.getBean("robertFactoryBean", UserService.class);
        System.out.println(context.getBean("&robertFactoryBean"));

        userService.test();
    }

    private static void getEnvironmentProperties() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Map<String, Object> systemEnvironment = context.getEnvironment().getSystemEnvironment();
        System.out.println(systemEnvironment);

        Map<String, Object> systemProperties = context.getEnvironment().getSystemProperties();
        System.out.println(systemProperties);

        MutablePropertySources propertySources = context.getEnvironment().getPropertySources();
        System.out.println(propertySources);

        System.out.println(context.getEnvironment().getProperty("NO_PROXY"));
        System.out.println(context.getEnvironment().getProperty("sun.jnu.encoding"));
        System.out.println(context.getEnvironment().getProperty("robert"));
    }

    private static void getResource() throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Resource resource = context.getResource("file://D:\\Workplace\\Spring\\spring0124\\src\\main\\java\\com\\robert\\AppConfig.java");

        System.out.println(resource.contentLength());
    }

    private static void messageResourceGet() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println(context.getMessage("test", null, new Locale("")));
    }

    private static void defaultListableBeanFactoryRegister() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(User.class);

        beanFactory.registerBeanDefinition("user", beanDefinition);

        System.out.println(beanFactory.getBean("user"));
    }

    // TODO 未跑通，但其他项目可以跑通
    private static void classpathBeanDefinitionReaderScan() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        context.refresh();

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
        scanner.scan("com.robert");

        System.out.println(context.getBean("userService"));
    }

    private static void xmlBeanDefinitionReaderLoad() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(context);
        int i = xmlBeanDefinitionReader.loadBeanDefinitions("spring.xml");

        System.out.println(context.getBean("user"));
    }

    private static void annotationBeanDefinitionReaderRegister() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(context);

        //将User.class解析成BeanDefinition
        annotatedBeanDefinitionReader.register(User.class);
        System.out.println(context.getBean("user"));
    }

    private static void registerBeanDefinition() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(User.class);
        beanDefinition.setScope("prototype"); //设置作用域
        beanDefinition.setInitMethodName("init"); //设置初始化方法
        beanDefinition.setLazyInit(true); //设置懒加载

        context.registerBeanDefinition("user", beanDefinition);

        System.out.println(context.getBean("user"));
    }


}
