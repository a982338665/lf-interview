
# 参加博客
>https://www.cnblogs.com/jing99/p/11769093.html

# 分布式事务解决方案及实现

     一、事务的ACID原则
     　　数据库事务的几个特性：原子性(Atomicity )、一致性( Consistency )、隔离性或独立性( Isolation)和持久性(Durabilily)，简称就是ACID。
         原子性：操作这些指令时，要么全部执行成功，要么全部不执行。只要其中一个指令执行失败，所有的指令都执行失败，数据进行回滚，回到执行指令前的数据状态。
         一致性：事务的执行使数据从一个状态转换为另一个状态，但是对于整个数据的完整性保持稳定。
         隔离性：在该事务执行的过程中，无论发生的任何数据的改变都应该只存在于该事务之中，对外界不存在任何影响。只有在事务确定正确提交之后，才会显示该事务对数据的改变。其他事务才能获取到这些改变后的数据。
         持久性：当事务正确完成后，它对于数据的改变是永久性的。

## 分布式事务
    
    单系统而言，事务总在一个service，多系统微服务，service中包含其他服务的业务，但是又要保证该service中业务要么成功，要么失败，此时就会引入分布式事务
    
## 处理方案1-基于XA的两阶段提交方案
    
    1.简介：XA包含 事务管理器 和本地资源管理器，其中本地资源管理器往往由数据库实现，比如 Oracle、DB2 这些商业数据库都实现了 XA 接口，
        而事务管理器作为全局的协调者，负责各个本地资源的提交和回滚。
    2.劣势：
        1.两阶段提交方案应用非常广泛，几乎所有商业OLTP (On-Line Transaction Processing)数据库都支持XA协议
        2.但是两阶段提交方案开发复杂、锁定资源时间长，对性能影响很大，基本不适合解决微服务事务问题。

## 处理方案2-TCC解决方案
    
    1.场景：TCC方案在电商、金融领域落地较多
    2.简介：TCC方案其实是两阶段提交的一种改进。其将整个业务逻辑的每个分支显式的分成了Try、Confirm、Cancel三个操作。
        Try 阶段主要是对业务系统做检测及资源预留，完成业务的准备工作。
        Confirm 阶段主要是对业务系统做确认提交，Try阶段执行成功并开始执行 Confirm阶段时，默认 Confirm阶段是不会出错的。即：只要Try成功，Confirm一定成功。
        Cancel 阶段主要是在业务执行错误，需要回滚的状态下执行的业务取消，预留资源释放。
    3.过程：
        事务开始时，业务应用会向事务协调器注册启动事务。之后业务应用会调用所有服务的try接口，完成一阶段准备。
        之后事务协调器会根据try接口返回情况，决定调用confirm接口或者cancel接口。如果接口调用失败，会进行重试。
        微服务倡导服务的轻量化、易部署，而TCC方案中很多事务的处理逻辑需要应用自己编码实现，复杂且开发量大。
        
## 处理方案3-本地消息表 (异步确保)
    
    1.实现：
       1.我们首先需要在本地数据新建一张本地消息表，然后我们必须还要一个MQ（不一定是mq，但必须是类似的中间件）。
       2.消息表怎么创建呢？这个表应该包括这些字段： id, biz_id, biz_type, msg, msg_result, msg_desc,atime,try_count。
            分别表示uuid，业务id，业务类型，消息内容，消息结果（成功或失败），消息描述，创建时间，重试次数， 其中biz_id，msg_desc字段是可选的。
    　 3.实现思路为：
            A 系统在自己本地一个事务里操作同时，插入一条数据到消息表；
            接着 A 系统将这个消息发送到 MQ 中去；
            B 系统接收到消息之后，在一个事务里，往自己本地消息表里插入一条数据，同时执行其他的业务操作，如果这个消息已经被处理过了，那么此时这个事务会回滚，这样保证不会重复处理消息；
            B 系统执行成功之后，就会更新自己本地消息表的状态以及 A 系统消息表的状态；
            如果 B 系统处理失败了，那么就不会更新消息表状态，那么此时 A 系统会定时扫描自己的消息表，如果有未处理的消息，会再次发送到 MQ 中去，让 B 再次处理；
            这个方案保证了最终一致性，哪怕 B 事务失败了，但是 A 会不断重发消息，直到 B 那边成功为止。
            这个方案严重依赖于数据库的消息表来管理事务，这样在高并发的情况下难以扩展，同时要在数据库中额外添加一个与实际业务无关的消息表来实现分布式事务，繁琐。
    2.结论：不建议
    
## 处理方案4-MQ事务消息
    
    1.RocketMq:直接基于 MQ 来实现事务，不再用本地的消息表。有一些第三方的MQ是支持事务消息的，比如RocketMQ，
        他们支持事务消息的方式也是类似于采用的二阶段提交，但是市面上一些主流的MQ都是不支持事务消息的，比如 RabbitMQ 和 Kafka 都不支持。
    2.实现思路：
        A 系统先发送一个 prepared 消息到 mq，如果这个 prepared 消息发送失败那么就直接取消操作别执行了；
        如果这个消息发送成功过了，那么接着执行本地事务，如果成功就告诉 mq 发送确认消息，如果失败就告诉 mq 回滚消息；
        如果发送了确认消息，那么此时 B 系统会接收到确认消息，然后执行本地的事务；
        mq 会自动定时轮询所有 prepared 消息回调你的接口，问你，这个消息是不是本地事务处理失败了，所有没发送确认的消息，是继续重试还是回滚？
        一般来说这里你就可以查下数据库看之前本地事务是否执行，如果回滚了，那么这里也回滚吧。这个就是避免可能本地事务执行成功了，而确认消息却发送失败了。
        这个方案里，要是系统 B 的事务失败了咋办？重试咯，自动不断重试直到成功，如果实在是不行，要么就是针对重要的资金类业务进行回滚，
        比如 B 系统本地回滚后，想办法通知系统 A 也回滚；或者是发送报警由人工来手工回滚和补偿。
        　　这种方案缺点就是实现难度大，而且主流MQ不支持。
        
## 处理方案5-分布式事务中间件解决方案
    
    分布式事务中间件其本身并不创建事务，而是基于对本地事务的协调从而达到事务一致性的效果。典型代表有：阿里的GTS（https://www.aliyun.com/aliware/txc）、开源应用LCN。
    
### 5.1 LCN分布式事务框架    
    
    1、LCN框架的由来
    　　在设计框架之初的1.0 ~ 2.0的版本时，框架设计的步骤是如下的，各取其首字母得来的LCN命名。
    　　锁定事务单元（lock）、
        确认事务模块状态(confirm)、
        通知事务(notify)
    2、LCN框架相关资料
        tx-lcn官方地址：https://www.txlcn.org/
        tx-lcn Github地址：https://github.com/codingapi/tx-lcn
        tx-lcn服务下载地址：https://pan.baidu.com/s/1cLKAeE#list/path=%2F
        tx-lcn服务源码地址：https://github.com/codingapi/tx-lcn/tree/master/tx-manager
    3、LCN框架核心执行步骤
        创建事务组：是指在事务发起方开始执行业务代码之前先调用TxManager创建事务组对象，然后拿到事务标示GroupId的过程。
        添加事务组：添加事务组是指参与方在执行完业务方法以后，将该模块的事务信息添加通知给TxManager的操作。
        关闭事务组：是指在发起方执行完业务代码以后，将发起方执行结果状态通知给TxManager的动作。当执行完关闭事务组的方法以后，TxManager将根据事务组信息来通知相应的参与模块提交或回滚事务。
    4、LCN应用
        4.1、搭建tx-manager服务
        　　LCN是通过一个独立的微服务tx-manager作为分布式事务控制服务端（事务协调器）。需要执行分布式事务控制的微服务应用都通过远程服务调用的方式，
            在tx-manager上标记事务组，在执行事务处理后，将本地事务状态发送到tx-manager中对应的事务组上，tx-manager会根据具体的状态来通知相应的微服务应用提交或回滚。
        　　tx-manager也是使用Spring Cloud开发的一个微服务应用，在搭建过程上是非常简单的。下载tx-manager事务协调器zip压缩包：https://pan.baidu.com/s/1cLKAeE#list/path=%2F
            压缩包解压后内容如下：
            修改application.properties配置文件，提供本地微服务应用的Eureka注册中心配置、redis配置。其中redis是事务协调器在处理事务组时使用的临时存储。
                1.修改application.properties配置文件，提供本地微服务应用的Eureka注册中心配置、redis配置。其中redis是事务协调器在处理事务组时使用的临时存储。
                        ##########################txmanager-start#######################
                        #服务端口
                        server.port=8899
                        
                        #tx-manager不得修改
                        spring.application.name=tx-manager
                        
                        spring.mvc.static-path-pattern=/**
                        spring.resources.static-locations=classpath:/static/
                        ###########################txmanager-end#######################
                        
                        # eureka地址:由于txManager也是一个SpringCloud的微服务，因此也要注册服务，交由Eureka Server管理
                        eureka.client.service-url.defaultZone=http://127.0.0.1:8761/eureka/
                        eureka.instance.prefer-ip-address=true
                        
                        #############################redis-start#########################
                        ## redis 单点环境配置：LCN采用Redis记录事务状态组状态，并基于事务组状态管理分布式事务
                        # redis
                        spring.redis.database=0
                        spring.redis.timeout=0
                        spring.redis.host=192.168.1.136
                        spring.redis.port=6379
                        spring.redis.pool.max-active=100
                        spring.redis.pool.max-wait=3000
                        spring.redis.pool.max-idle=200
                        spring.redis.pool.min-idle=50
                        spring.redis.pool.timeout=600
                        ##############################redis-end##########################
                        
                        ###############################LCN-start########################
                        tm.transaction.netty.delaytime = 5
                        tm.transaction.netty.hearttime = 15
                        tm.redis.savemaxtime=30
                        tm.socket.port=9999
                        tm.socket.maxconnection=100
                        tm.compensate.auto=false
                        tm.compensate.notifyUrl=http://ip:port/path
                        tm.compensate.tryTime=30
                        tm.compensate.maxWaitTime=5000
                        
                        logging.level.com.codingapi=debug
                        将修改后的application.properties配置文件打包到tx-manager-x.x.x.jar中，替代jar中原有的默认配置文件。
                        　　使用命令： java -jar tx-manager-x.x.x.jar启动微服务。
                        　　测试tx-manager事务协调器是否启动成功可以访问http://ip:8899/。如下结果代表事务协调器启动成功：
        4.2、在微服务中使用LCN实现分布式事务管理
            在所有需要处理分布式事务的微服务中增加下述依赖：为统一资源版本，使用properties统一管理版本信息
                <properties>
                    <lcn.last.version>4.1.0</lcn.last.version>
                </properties>
                <dependency>
                    <groupId>com.codingapi</groupId>
                    <!-- 此例采用SpringCloud，如果是dubbo需要引用另一个包 -->
                    <artifactId>transaction-springcloud</artifactId>
                    <version>${lcn.last.version}</version>
                    <exclusions>
                        <exclusion>
                            <groupId>org.slf4j</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
                <dependency>
                    <groupId>com.codingapi</groupId>
                    <artifactId>tx-plugins-db</artifactId>
                    <version>${lcn.last.version}</version>
                    <exclusions>
                        <exclusion>
                            <groupId>org.slf4j</groupId>
                            <artifactId>*</artifactId>
                        </exclusion>
                    </exclusions>
                </dependency>
        4.3 在全局配置文件中增加下述配置：
           # 定义事务协调器所在位置。根据具体环境定义其中的IP地址和端口。
           tm.manager.url=http://127.0.0.1:8899/tx/manager/        
        4.4 使用LCN做分布式事务管理时，微服务应用内必须提供一个用于获取txUrl（txUrl就是全局配置文件中定义的tm.manager.url）的类型实现，这个类可以使用独立应用定义，在微服务应用中引入。具体如下：
            @Service
            public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {
                @Value("${tm.manager.url}")
                private String url;
                @Override
                public String getTxUrl() {
                    return url;
                }
            }
        4.5 在分布式事务管理代码中增加注解@TxTransaction。在业务调用方增加的注解需要属性isStart=true。而被调用方则不需要定义任何的注解属性。如：
           　　A服务调用了B服务，那么A服务中代码：
            @TxTransaction(isStart=true)
            @Transactional
            public void trade() {
                //本地调用
                tradeDao.save();
                //远程调用方
                orderService.order();
            }
        4.6 B服务中代码：
            @Transactional
            @TxTransaction
            public void order() {
                //本地调用
                orderDao.save();
            }
            　　其中@Transactional用于管理本地事务，而@TxTransaction管理分布式事务，当需要回滚时，调用本地自带的事务管理器进行回滚。
            Stay hungry，stay foolish ！
