# simple-multi-datasource
测试多数据源示例

* basic-multi-datasource 
 * 两套SqlSessionFactory 两套数据源 PS : 可以理解为在一个Spring工程中使用了两套myabtis

* dynamic-datasource 
 * 动态数据源的思路为 由Spring在业务层根据业务提供相应的Connection放入到threadlocal中，mybatis执行sql语句去threadlocal获取即可！PS ： 此方式事务可能问题可能会不好处,应当考虑分布式事务。

