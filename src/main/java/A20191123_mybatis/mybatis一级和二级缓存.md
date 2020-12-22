
>https://www.cnblogs.com/yuluoxingkong/p/8205858.html

## 一级缓存:默认开启
    
    1.一级缓存是SqlSession级别的缓存。在操作数据库时需要构造sqlSession对象，在对象中有一个数据结构用于存储缓存数据。
        不同的sqlSession之间的缓存数据区域是互相不影响的。也就是他只能作用在同一个sqlSession中，不同的sqlSession中的缓存是互相不能读取的
    2.工作原理
        用户发起查询请求，查找某条数据，sqlSession先去缓存中查找，是否有该数据，如果有，读取；
        如果没有，从数据库中查询，并将查询到的数据放入一级缓存区域，供下次查找使用。
        但sqlSession执行commit，即增删改操作时会清空缓存。这么做的目的是避免脏读。
        如果commit不清空缓存，会有以下场景：A查询了某商品库存为10件，并将10件库存的数据存入缓存中，之后被客户买走了10件，数据被delete了，
        但是下次查询这件商品时，并不从数据库中查询，而是从缓存中查询，就会出现错误。
        既然有了一级缓存，那么为什么要提供二级缓存呢？
        二级缓存是mapper级别的缓存，多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。二级缓存的作用范围更大。
        还有一个原因，实际开发中，MyBatis通常和Spring进行整合开发。Spring将事务放到Service中管理，对于每一个service中的sqlsession是不同的，
        这是通过mybatis-spring中的org.mybatis.spring.mapper.MapperScannerConfigurer创建sqlsession自动注入到service中的。 每次查询之后都要进行关闭sqlSession，
        关闭之后数据被清空。所以spring整合之后，如果没有事务，一级缓存是没有意义的。
    
## 二级缓存：需要配置
    
    1.二级缓存是mapper级别的缓存，多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。
        UserMapper有一个二级缓存区域（按namespace分），其它mapper也有自己的二级缓存区域（按namespace分）。每一个namespace的mapper都有一个二级缓存区域，
        两个mapper的namespace如果相同，这两个mapper执行sql查询到数据将存在相同的二级缓存区域中。
    2.开启二级缓存
        1.在MyBatis的配置文件中加入：
            <settings>    
               <!--开启二级缓存-->    
                <setting name="cacheEnabled" value="true"/>    
            </settings>
        2.在需要开启二级缓存的mapper.xml中加入cache标签
            <cache/>
        3.让使用二级缓存的POJO类实现Serializable接口
            public class User implements Serializable {}
        4.junit测试代码
            @Test  
            public void testCache2() throws Exception {  
                SqlSession sqlSession1 = sqlSessionFactory.openSession();  
                SqlSession sqlSession2 = sqlSessionFactory.openSession();  
                UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);  
                User user1 = userMapper1.findUserById(1);  
                System.out.println(user1);  
                sqlSession1.close();  
                UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);  
                User user2 = userMapper2.findUserById(1);  
                System.out.println(user2);  
                sqlSession2.close();  
            }
        5.输出结果
           DEBUG [main] - Cache Hit Ratio [com.iot.mybatis.mapper.UserMapper]: 0.0  
           DEBUG [main] - Opening JDBC Connection  
           DEBUG [main] - Created connection 103887628.  
           DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@631330c]  
           DEBUG [main] - ==>  Preparing: SELECT * FROM user WHERE id=?   
           DEBUG [main] - ==> Parameters: 1(Integer)  
           DEBUG [main] - <==      Total: 1  
           User [id=1, username=张三, sex=1, birthday=null, address=null]  
           DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@631330c]  
           DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@631330c]  
           DEBUG [main] - Returned connection 103887628 to pool.  
           DEBUG [main] - Cache Hit Ratio [com.iot.mybatis.mapper.UserMapper]: 0.5  
           User [id=1, username=张三, sex=1, birthday=null, address=null]
        6.我们可以从打印的信息看出，两个sqlSession，去查询同一条数据，只发起一次select查询语句，第二次直接从Cache中读取。
          前面我们说到，Spring和MyBatis整合时， 每次查询之后都要进行关闭sqlSession，关闭之后数据被清空。所以spring整合之后，如果没有事务，一级缓存是没有意义的。
          那么如果开启二级缓存，关闭sqlsession后，会把该sqlsession一级缓存中的数据添加到namespace的二级缓存中。这样，缓存在sqlsession关闭之后依然存在。
          总结：
            对于查询多commit少且用户对查询结果实时性要求不高，此时采用mybatis二级缓存技术降低数据库访问量，提高访问速度。
            但不能滥用二级缓存，二级缓存也有很多弊端，从MyBatis默认二级缓存是关闭的就可以看出来。
            二级缓存是建立在同一个namespace下的，如果对表的操作查询可能有多个namespace，那么得到的数据就是错误的。
          举个简单的例子:
            订单和订单详情，orderMapper、orderDetailMapper。在查询订单详情时我们需要把订单信息也查询出来，那么这个订单详情的信息被二级缓存在orderDetailMapper的namespace中，
            这个时候有人要修改订单的基本信息，那就是在orderMapper的namespace下修改，他是不会影响到orderDetailMapper的缓存的，那么你再次查找订单详情时，拿到的是缓存的数据，
            这个数据其实已经是过时的
        7.根据以上，想要使用二级缓存时需要想好两个问题：
          1）对该表的操作与查询都在同一个namespace下，其他的namespace如果有操作，就会发生数据的脏读。
          2）对关联表的查询，关联的所有表的操作都必须在同一个namespace。
