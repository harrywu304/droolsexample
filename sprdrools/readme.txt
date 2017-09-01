drools有两种方式跟spring整合：
1）使用spring-drools.xml配置：这是最常见的ioc注入，调用者只需要在类中使用@Resource引入即可；相当于全自动；
2）配置META-INF/kmodule.xml文件，使用@Bean自定义方法获取；相当于半自动；

注意：注解引用的方式只适合于StatelessKieSession；有状态的KieSession，需要prototype scope，这时候只能用getBean的方式来显式获取；

另外一种方式就是直接使用Kie Api全手动构建Ksession。

