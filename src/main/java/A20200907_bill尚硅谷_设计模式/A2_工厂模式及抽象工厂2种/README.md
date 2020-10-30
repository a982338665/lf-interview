
## 使用
    
    1.简单工厂模式
    2.工厂方法模式
    3.抽象工厂模式

## 1.简单工厂模式
    
    1.示例：
        披萨的种类 N
        披萨的制作过程 N
        完成披萨订购
    2.介绍：
        1)简单工厂模式是属于创建型模式，是工厂模式的一种。简单工厂模式是由一个工厂对象决定创建出哪一种产品类的实例。简单工厂模式是工厂模式家族中最简单实用的模式
        2)简单工厂模式：定义了一个创建对象的类，由这个类来封装实例化对象的行为(代码)
        3)在软件开发中，当我们会用到大量的创建某种、某类或者某批对象时，就会使用到工厂模式.
    3.示例：--> 需要哪种类型的都交给工厂去生产
        simpleFactory.createPizza(orderType)
        public Pizza createPizza(String orderType) {
            Pizza pizza = null;
            System.out.println("使用简单工厂模式"); 
            if (orderType.equals("greek")) {
                pizza = new GreekPizza();
                pizza.setName(" 希腊披萨 ");
            } else if (orderType.equals("cheese")) { 
                pizza = new CheesePizza();
                pizza.setName(" 奶酪披萨 ");
            } else if (orderType.equals("pepper")) { 
                pizza = new PepperPizza();
                pizza.setName("胡椒披萨");
            }
            return pizza;
        }

## 2.工厂方法模式
    
    1.示例：
        新需求-按区域不同分披萨：如 北京的奶酪披萨，北京胡椒披萨，伦敦奶酪披萨，伦敦胡椒披萨
        思路1：采用简单工厂模式：- 同上 【软件可维护性，拓展性不太好】
            OrderPizza.class
            public Pizza createPizza(String orderType) {
                Pizza pizza = null;
                System.out.println("使用简单工厂模式"); 
                if (orderType.equals("greek")) {
                    pizza = new GreekPizza();
                    pizza.setName(" 北京的奶酪披萨 ");
                } else if (orderType.equals("cheese")) { 
                    pizza = new CheesePizza();
                    pizza.setName(" 北京胡椒披萨 ");
                } else if (orderType.equals("pepper")) { 
                    pizza = new PepperPizza();
                    pizza.setName("伦敦奶酪披萨");
                } ...
                return pizza;
            }
        思路2：- 工厂方法模式 - 【订购的时候作区分】
            将订购的菜单区分地域：北京：[奶酪披萨,胡椒披萨],伦敦：[奶酪披萨,胡椒披萨]
            将OrderPizza做成父类，并且包含抽象方法public abstract Pizza createPizza
            每次新加地域的时候，都去继承该父类，将创建披萨的方法交由 子类去实现
            OrderPizza.class{ public abstract Pizza createPizza(String orderType) }  
            BJOrderPizza.class{ public Pizza createPizza(String orderType){//实现创建北京的奶酪} }
            LDOrderPizza.class{ public Pizza createPizza(String orderType){//实现创建伦敦的奶酪} }

## 3.抽象工厂模式：简单工厂模式的抽象
    
    1.介绍：
        1)抽象工厂模式：定义了一个 interface 用于创建相关或有依赖关系的对象簇，而无需指明具体的类
        2)抽象工厂模式可以将简单工厂模式和工厂方法模式进行整合。
        3)从设计层面看，抽象工厂模式就是对简单工厂模式的改进(或者称为进一步的抽象)。
        4)将工厂抽象成两层，AbsFactory(抽象工厂) 和 具体实现的工厂子类。程序员可以根据创建对象类型使用对应的工厂子类。这样将单个的简单工厂类变成了工厂簇，更利于代码的维护和扩展。
    2.示例：- 完成以上2中示例  【制作的时候做区分】
        披萨工厂：抽象类或者接口 AbsFactory：abstract Pizza createPizza(type)
            北京披萨工厂：实现类1 BJFactory Pizza createPizza(type)
            伦敦披萨工厂：实现类2 LDFactory Pizza createPizza(type)
        订购披萨：接收入参-【披萨工厂-抽象类或者接口】,需要的类型

## 4.工厂模式在 JDK-Calendar 应用的源码分析 
        
    switch (caltype) { 
        case "buddhist":
            cal = new BuddhistCalendar(zone, aLocale);
            break;
        case "japanese":
            cal = new JapaneseImperialCalendar(zone, aLocale);
            break; 
        case "gregory":
            cal = new GregorianCalendar(zone, aLocale);
            break;
    }
    
    
    
