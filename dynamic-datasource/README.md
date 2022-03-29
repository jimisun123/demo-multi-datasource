# 动态数据源

> 期望在基础多数据源的基础上进行演进

* Mapper文件和数据源无关
* 在业务层需要的时候进行指定数据源

PS： Spring Boot 的动态数据源，本质上是把多个数据源存储在一个 Map 中，当需要使用某个数据源时，从 Map 中获取此数据源进行处理。 而在 Spring 中，已提供了抽象类
AbstractRoutingDataSource 来实现此功能。因此，我们在实现动态数据源的，只需要继承它，实现自己的获取数据源逻辑即可。

PS: 由于持久层需要遵循SpringBoot的规则，以及使用到了AOP切面。 则需要导入 spring-boot-starter-jdbc和spring-boot-starter-aop依赖

PS: 注意AOP切面的规则是如果类上存在注解则覆盖方法，如果不符合业务请修改DynamicDataSourceAspect类