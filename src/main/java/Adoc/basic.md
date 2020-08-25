
#https://www.cnblogs.com/tietazhan/articles/5771886.html
**1.实例方法静态方法区别：**
    
    静态方法针对于类，不用实例化对象即可使用
    实例方法针对于对象，new 对象后可调用方法
    
**2.java异常分类：**
    
    1.检出异常：编译时无法通过 io sqlException 需要try-catch
    2.非检出异常：可编译，运行时报错 RuntimeException
    
**3.String类不可变：DisabledOBJ**
    
    1.不可变对象：八种基本类型的包装类，String，BigInteger，BigDecimal等
    2.可变对象：普通对象
    3.如何创建不可变对象：
        ·所有属性都是final和private   不能被修改
        ·不提供setter方法，避免值被修改
        ·类是final，或者所有的方法都是final ，不允许通过继承被修改
    4.优点：
        ·只读，线程安全
        ·并发读，提高性能
        ·可重复使用
    5.缺点：制造垃圾，浪费空间
    6.通过反射可以破坏 String 的不可变性
