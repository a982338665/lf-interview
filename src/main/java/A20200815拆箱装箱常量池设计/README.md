
## 部分来自：https://github.com/a982338665/lf-mkd-java-base-muke.git
>com/hdsf/hdsf/华东师范-1.md


**23.常量设计和常量池：**
    
    1.常量申明：不能修改，可直接引用，空间中仅保留一份，只读
        普通类中：public static final 【数据类型】 【全部大写的变量名,以下划线相连】 =  【UNIX_NAME】
        接口中：接口中定义的变量默认为常量
    2.常量池：
        1.java为很多基本类型的包装类、字符串都建立了常量池
        2.相同的值只存储一份，节省内存，共享访问，提高运行速度
        3.基本类型的包装类：常量池缓存范围
            Boolean     true/false
            Byte        -128 到 127  
            Short       -128 到 127  
            Integer     -128 到 127  
            Long        -128 到 127  
            Character   0 到 127
            Float       没有缓存-常量池
            Double      没有缓存-常量池
        4.java为字符串常量建立的缓存常量池：--
        5.基本类型的包装类和字符串的创建方式：
            -1.赋值创建，放在栈内存，会被常量化
            -2.new对象创建,放在堆内存，不会被常量化
                String a = "a";
                String a = new String("a");因为new对象不会被常量化，所以两者不等
        6.作用：节约内存，共享访问
    3.设计：
        java在编译Integer x = yyy ;时，会翻译成为Integer x = Integer.valueOf(yyy)。而java API中对Integer类型的valueOf的定义如下，对于-128到127之间的数，会进行缓存，
        Integer i = 127时，会将127进行缓存，下次再写Integer j = 127时，就会直接从缓存中取，就不会new了。
        如果超过128就会重新new一个新对象
        解析原因： 归结于java对于Integer与int的自动装箱与拆箱的设计，是一种模式：叫享元模式（flyweight）。
            1. 加大对简单数字的重利用，Java定义在自动装箱时对于值从–128到127之间的值，它们被装箱为Integer对象后，会存在内存中被重用，始终只存在一个对象。
            2. 而如果超过了从–128到127之间的值，被装箱后的Integer对象并不会被重用，即相当于每次装箱时都新建一个 Integer对象。
            
**24.常量延伸：不可变对象-DisabledOBJ**

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
    
**25.字符串加法:StringBufferTest**

    1.String a = "abc";
      a = a + "def"; //由于字符串是不可变对象，所以相加会产生新对象 "abcdef",
                     //此时将会有两个对象 abc abcdef，浪费空间,效率差
    2.StringBuffer： 线程安全，同步 修改快速    可变对象
    3.StringBuilder：不安全，更快 50000次       可变对象
        String耗时：1496
        StringBuffer耗时：8
        StringBuilder耗时：2
        StringConcat耗时：632
