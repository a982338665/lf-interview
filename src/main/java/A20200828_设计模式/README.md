
## 介绍
>文件夹中.cn 代表内容来自于菜鸟教程

## 1.享元模式

    思想：避免创建大量对象，维护一个池或者缓存
    举例：
        1.字符串常量池：有则返回，无则创建
        2.数据库连接池：维持连接对象，提高复用性
        3.基本数据类型包装类的常量缓存：拆箱装箱问题 -> 此处的缓存默认值是可以修改的不一定非是128，看源码知道其内部是维护了一个数组，所以此池是在对内存中
             1. 加大对简单数字的重利用，Java定义在自动装箱时对于值从–128到127之间的值，它们被装箱为Integer对象后，会存在内存中被重用，始终只存在一个对象。
             2. 而如果超过了从–128到127之间的值，被装箱后的Integer对象并不会被重用，即相当于每次装箱时都新建一个 Integer对象。
    核心代码：
        //维护一个简单的缓存池
        private static final HashMap<String, Shape> circleMap = new HashMap<>();
        public static Shape getCircle(String color) {
           Circle circle = (Circle)circleMap.get(color);
           if(circle == null) {
              circle = new Circle(color);
              circleMap.put(color, circle);
              System.out.println("Creating circle of color : " + color);
           }
           return circle;
        }
        
## 2.工厂模式：
    
    思想：想要什么 交给工厂生产
    举例：hibernate 换数据库只需换方言和驱动就可以。
    核心代码：-- 从一个商店里 - 购买 猪猫狗
        商店即为工厂
        猪猫狗即为产品
    
## 3.抽象工厂模式：
    
    思想：管理工厂的超级工厂
    举例：工厂抽象类- > 衍生多个工厂 ，工厂生产者统一管理这多个工厂
          AbstracFactory1 factory = FactoryProducer.getFactory("1");

## 4.单例模式
    
    思想：仅维护一个对象
    举例：
        --懒汉式
        --饿汉式
    建议：饿汉或者双检锁
    
## 5.建造者模式
    
    思想：Builder模式可以将一个类的构建和表示进行分离。
    举例：构建者模式是一个非常实用而常见的创建类型的模式（creational design pattern)，
        1.例如图片处理框架Glide
        2.网络请求框架Retrofit等都使用了此模式。
        3.mybatis-plus框架中,List<User> users = new User().selectAll();此种用法不知道算不算建造者模式？？？
    扩展：其实上面的内容是Builder在Java中一种简化的使用方式，经典的Builder 模式与其有一定的不同，如果没有兴趣的同学就可以不用往下读了。
    builder模式有4个角色：
        Product: 最终要生成的对象，例如 Computer实例。
        Builder： 构建者的抽象基类（有时会使用接口代替）。其定义了构建Product的抽象步骤，其实体类需要实现这些步骤。其会包含一个用来返回最终产品的方法Product getProduct()。
        ConcreteBuilder: Builder的实现类。
        Director: 决定如何构建最终产品的算法. 其会包含一个负责组装的方法void Construct(Builder builder)， 在这个方法中通过调用builder的方法，就可以设置builder，
                  等设置完成后，就可以通过builder的 getProduct() 方法获得最终的产品。

## 6.原型模式Prototype Pattern
    
    思想：通过拷贝原型创建新对象
    优点：性能提高，规避构造函数的约束
    缺点：
        1.
        2.必须实现Cloneable接口
    举例：
        1、细胞分裂。 
        2、JAVA 中的 Object clone() 方法。
        3.spring中默认单例模式对应的@Scope("prototype")即为原型模式，通过克隆来创建对象    
    注意：与通过对一个类进行实例化来构造新对象不同的是，原型模式是通过拷贝一个现有对象生成新对象的。浅拷贝实现 Cloneable，重写，深拷贝是通过实现 Serializable 读取二进制流
        ·浅拷贝：
            1.实现 Cloneable 接口，并覆写 clone() 方法。
            2.拷贝对象后生成新对象
            3.生成的对象里面：基本数据类型直接复制的，引用数据类型，仅复制引用，所以在修改原对象引用数据类型时，新对象里面的属性也会出问题
        ·深拷贝：-> 引用数据类型也应该拷贝
            有两种方式：一种序列化、一种串行拷贝
            通过上面的例子可以看到，浅拷贝会带来数据安全方面的隐患，例如我们只是想修改了 studentB 的 subject，但是 studentA 的 subject 也被修改了，因为它们都是指向的同一个地址。
            所以，此种情况下，我们需要用到深拷贝
            1.深拷贝，在拷贝引用类型成员变量时，为引用类型的数据成员另辟了一个独立的内存空间，实现真正内容上的拷贝。
            2.对于有多层对象的，每个对象都需要实现 Cloneable 并重写 clone() 方法，进而实现了对象的串行层层拷贝。
            3.深拷贝相比于浅拷贝速度较慢并且花销较大。
        由输出结果可见，深拷贝后，不管是基础数据类型还是引用类型的成员变量，修改其值都不会相互造成影响。
        
## 7.适配器模式
    
    思想：两个不兼容接口的桥梁，使得目标接口的方法经过适配之后，可以完成原本不具备的能力
    举例：
        1.java中的jdbc
        2.linux上运行windows程序
        3.一个进口电视110v ，目前插头220v ，需要将220v适配可以使用110v（Adapt）
        4.InputStreamReader类就是个适配器：使得InputStream和Reader这两个接口可以一同工作
    代码：
        两个接口：
            一个【多媒体播放接口】：
                一个实现【mp3播放器】
            一个【高级多媒体播放接口】    
                一个实现【MP4播放器】
                一个实现【AVI播放器】
        实现：使用【多媒体播放接口】播放MP4
        
## 8.桥接模式

    思想：把抽象化与实现化解耦，使得二者可以独立变化
    举例：

