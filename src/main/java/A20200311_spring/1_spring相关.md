
## 1.@SpringBootApplication的运行机制？

    @SpringBootApplication是一个复合的注解，但实际上对于SpringBoot应用来说，重要的只有三个
        @Configuration           --配置
            标识一个类为配置类
        @ComponentScan           --组件扫描
            扫描指定包下需要装配的组件，注入容器
        @EnableAutoConfiguration --自动装配
            -@AutoConfigurationPackage                          将主配置类所在包作为自动配置的包进行管理
            -@Import({AutoConfigurationImportSelector.class})   导入类到ioc容器中，导入的类是根据meta-inf下面的spring.factories的配置进行导入
    配置文件定义属性 application.yml
    自动装配到所属依赖，通过ioc注入spring
    组件扫描则是指定扫描指定包下的 组件注解去自动注入，例如@Resource @Service @Controller等
    总结：根据配置文件自动装配所属依赖的类，通过动态代理方式注入到spring容器
    
## 2.
    


