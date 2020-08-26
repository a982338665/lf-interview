# 简介
>https://www.bilibili.com/video/BV18b411M7xz?t=6
>尚硅谷Java大厂面试题第二季(java面试必学，周阳主讲)
    
    1.2019年的笔试面试
        2018.12开始裁员，缩招

## 导图-redis
    
    1.你的项目中，哪些数据是数据库和redis缓存双写一份的？如何保障双写一致性？
    2.系统上线，redis缓存系统是如何部署的？，落地规划方案
    3.系统上线，redis缓存给了多大的总内存，命中率多高，抗住了多少QPS，数据流回源会有多少QPS
    4.热key大value问题，某个key出现了热点缓存，导致缓存集群中的某个机器负载过高，如何发现并解决
    5.超大value打满网卡的问题，如何规避
    6.工作经历中，有咩有遇到过缓存集群事故，说说细节和保障高可用的方案
    7.平时如何监控缓存集群的qps和容量
    8.缓存集群如何扩容
    9.说下redis集群原理和选举机制 
    10.key寻址算法有哪些
    11.redis线程模型现场画图说说
    12.redis内存模型画图说说
    13.redis的底层数据结构了解多少
    14.redis单线程特性优缺点
    15.怎么解决缓存击穿问题，雪崩？
    
## 导图-消息中间件-MQ
    
    1.四大消息中间件：kafka，rabbitmq，rocketmq，activemq，他们的对比？技术选型问题？
    2.消息积压，堵塞，如何容灾，限流
    3.消息队列主要作用
    4.为什么要引入中间件
    5.怎么使用消息队列的
    6.如何保证消息队列高可用
    7.kafka，rabbitmq，rocketmq，activemq，他们的对比？技术选型问题？
    8.mq在高并发情况下假设队列满了，如何防止消息丢失
    9.消费者消费消息，如何保证mq幂等性
    10.对死信队列的理解
    11.若百万基本的消息被积压，如何处理
    12.为什么不用其他mq，选用RocketMq？
    
## 导图-JUC多线程及高并发
## JVM和GC垃圾回收解析
    
    1.jvm垃圾回收时如何确定垃圾？
    2.你说使用过jvm调优和参数配置，问怎么查看jvm系统默认值？
    3.jvm常用的基本配置参数有哪些？
    4.对ooM的认识：
        ·StackOverFlowError：
        ·OutOfMemoryError:java heap space
        ·OutOfMemoryError:GC overhead limit exceeded
        ·OutOfMemoryError:Direct buffer memory
        ·OutOfMemoryError:Metaspace
        ·OutOfMemoryError:unable to create new native thread
        ·OutOfMemoryError:Requested array size exceeds VM limit 
    5.gc回收算法和垃圾收集器的关系？串行收集/并行收集/并发收集/STW是什么？
    6.怎么查看服务器默认的垃圾收集器是哪个？生产上如何配置垃圾收集器，说说理解
    7.G1垃圾收集器
    8.强，软，弱，虚引用
    9.生产环境服务器变慢，谈谈诊断思路和性能评估
    10.生产环境cpu占用过高，谈谈分析思路和定位
    11.jdk自带的jvm分析工具有哪些？你用过哪些，怎么用的
        ·jps
        ·jinfo
        ·jmap
        ·jstat
        ·jstack
    12.jvm字节码指令接触过吗
    

