
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
    
    
