
## 0.总结
    
    1.自己写的生成excel使用继承时违反了里氏替换原则（使用继承重写了父类方法），使用了依赖倒装原则（参数传递使用接口，提高复用性），
        可能违反了接口隔离原则（实现了没必要的接口），可能违背了迪米特法则，类耦合性太强
    
## 1.面试
    
    1.原型模式：
        1.uml类图画出核心角色
        2.深拷贝，浅拷贝，及深拷贝的两种源码实现：1-序列化 2-克隆
        3.spring框架中原型模式的使用 prototype - spring框架下bean的创建就是原型模式的应用
            -1 bean.xml   <bean id="id01" class="pers.li.Person" scope="prototype">
            -2 Test.java  
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml") 
                Object obj = applicationContext.getBean("id01);//根据id获取克隆对象
            -3 getBean()
                 public Object getBean(String name) throws BeansException {
                        return this.doGetBean(name, (Class)null, (Object[])null, false);
                    }
            -4 doGetBean()
                else if (mbd.isPrototype()) {
                    var11 = null;
                
                    Object prototypeInstance;
                    try {
                        this.beforePrototypeCreation(beanName);
                        prototypeInstance = this.createBean(beanName, mbd, args);
                    } finally {
                        this.afterPrototypeCreation(beanName);
                    }
                    bean = this.getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
                }
    2.哪里使用了OCP原则-开闭原则
    3.设计模式面试题-借贷平台的订单，有审核发布抢单等步骤，随着操作不同，改变订单状态，使用【状态模式】进行设计完成相应代码
        问题分析：
            添加一种状态，需要手动添加if/else，
            添加一种功能，要对所有状态进行判断，代码会很臃肿
    4.设计模式面试题-解释器设计模式
        1.介绍什么是解释设计器模式
        2.uml类图，分析设计模式中各个角色
        3.spring中哪部分使用了此设计模式，结合源码分析
            spring中的SpelExpressionParser使用到解释器模式【表达式计算】
            SpelExpressionParser parser = new SpelExpressionParser()
            Expression express = parser.parseExpression("100*(2+400)*1+66")
            int result =(int) express.getValue();
            System.out.print(result)
    5.单例模式的多种写法？代码实现优缺点
        饿汉
        懒汉
        双检锁
        静态内部类
        枚举
            
## 2.七大原则
    
    0.目的：代码重用性，可读性，可拓展性，可靠性
    
    1.单一职责原则：一个类只负责一项职责
        原来：calss Vehic {run(String name){}} name=飞机，轮船，汽车
        改1： 类分解： -类级别上的单一职责
                class Fly {run(String name){}}  name=飞机
                class Load {run(String name){}} name=轮船，汽车
        改2： 方法分解：-方法级别上的单一职责【若类中方法数量较少时，可使用此种方式】
                calss Vehic {runFly(String name){};runLoad(String name){}} 不同方法调用
    2.接口隔离原则：客户端不应该依赖它不需要的接口
        原来：
            A实现C（接口1，接口2，接口3）- A只会用到接口1，接口3
            B实现C（接口1，接口2，接口3）- B只会用到接口2，接口3
            此种方式就会致使A，B有空实现
        拆分接口：
            接口C拆分：
                C （基类接口-接口3）
                C1（接口1）继承 C
                C2（接口2）继承 C
            A实现C1：重写接口1，接口3
            B实现C2：重写接口2，接口3
            若接口3可用可不用，则将其接口重做调整为抽象类即可
    3.依赖倒装原则 - 关于【面向接口编程，留好拓展】
        介绍：- 接口抽象类定规范，实现类做细节，细节多变，抽象稳定
            1)高层模块不应该依赖低层模块，二者都应该依赖其抽象
            2)抽象不应该依赖细节，细节应该依赖抽象
            3)依赖倒转(倒置)的中心思想是面向接口编程
            4)依赖倒转原则是基于这样的设计理念：相对于细节的多变性，抽象的东西要稳定的多。以抽象为基础搭建的架构比以细节为基础的架构要稳定的多。
                在 java 中，抽象指的是接口或抽象类，细节就是具体的实现类
            5)使用接口或抽象类的目的是制定好规范，而不涉及任何具体的操作，把展现细节的任务交给他们的实现类去完成
        示例：用户接受信息-【信息：email，phone，QQ等】
        原来：
            Person.class { receive(Email e){e.getInfo();}}
            Email.class  { getInfo(){'获取邮件信息'}}
            Main：Person p = new Person();p.receive(new Email())
        分析：方法接受参数为具体的实体类，那么每次接受不同种类的数据时就需要重写方法，新建类例如
            Person.class { receive2(QQ e){e.getInfo();}}
            QQ.class  { getInfo(){'获取QQ信息'}}
            Main：Person p = new Person();p.receive2(new QQ())
        改造：接受信息的方式不确定，也就意味着需要抽象，因此参数传递应当以 抽象类或者接口为中介，即，统一设立接收者
            IReceiver 接口 {getInfo();}
            Person.class { receive(IReceiver r){r.getInfo();}}
            Email.class impl  IReceiver {getInfo}
            QQ.class impl  IReceiver {getInfo}
            Main：Person p = new Person();p.receive(new QQ()/new Email())
        接口传递方式：
            -1 方法入参： p.receive(IReceiver r)
            -2 构造方法： new Person(IReceiver r);p.receiver()
            -3 setter  ： p.setIReceiver(IReceiver r);p.receiver()
        总结：接口抽象利于程序拓展和优化，继承时遵循里氏替换原则
    4.里氏替换原则：面向继承-【正确使用继承，尽量不重写父类方法】
        前言：子类继承父类，父类要被修改时，必须考虑是否对子类有影响，继承会增强对象间的耦合性，降低程序移植性
        使用：子类尽量不重写父类方法，而是通过其他方式（聚合，组合，依赖）解决问题
        示例：求和求差
        原来：- 写起来简单，多态运行时容易出错，复用性较差
            A.class {sub(int x,int y){x-y}}
            B.class entends A.class {sub(int x,int y){x+y}}
            Main: new B(); b.sub(); 本来想用的是继承父类的父类方法，即本意为x-y，因为被重写求得数据x+y
        解决：
            原有的父类，子类继承一个更通俗的基类，去掉原有继承关系，使用依赖，组合，聚合方式代替
            Base.class {//把更加基础的方法和成员写到 Base 类}
            A entends Base{ sub(int x,int y){x-y}}
            B entends Base{ 
                //B要使用A的方法，使用组合关系
                private A a = new A();
                sub(int x,int y){a.sub(x,y)}
                add(int x,int y){x+y}
            }
    5.开闭原则：对拓展开放，对修改关闭，【新增功能时，尽量少修改代码】
        前言：
            1)开闭原则（Open Closed Principle）是编程中最基础、最重要的设计原则
            2)一个软件实体如类，模块和函数应该对扩展开放(对提供方)，对修改关闭(对使用方)。用抽象构建框架，用实现扩展细节。
            3)当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化。即当我们给类增加新功能的时候，尽量不修改代码，或者尽可能少修改代码.
            4)编程中遵循其它原则，以及使用设计模式的目的就是遵循开闭原则
        示例：画图
            基类：Shape { int type}
            圆：extends Shape{ super.type=1 }
            方：extends Shape{ super.type=2 }
            三角：extends Shape{ super.type=3 }
            画笔：{ drawShape(Shape s){
                    if s.type=1 圆
                    if s.type=2 方
                    if s.type=3 三角
                  }}
            Main：画笔 huabi = new 画笔()
                  huabi.drawShape(new 圆()) 
                  huabi.drawShape(new 方()) 
                  huabi.drawShape(new 三角()) 
                  此时若是，要新加一种【菱形】，则需要改动的地方有类【画笔】，新加类【菱形】
        优化：让调用方不做修改，所有修改扔给新加类【菱形】
            抽象基类：Shape { abstract draw();}
            圆：extends Shape{ draw(){圆} }
            方：extends Shape{ draw(){方} }
            三角：extends Shape{ draw(){三角} }
            画笔：{ drawShape(Shape s){
                     s.draw();
                  }}
            Main：画笔 huabi = new 画笔()
                  huabi.drawShape(new 圆()) 
                  huabi.drawShape(new 方()) 
                  huabi.drawShape(new 三角()) 
                  此时若是，要新加一种【菱形】，则仅需要再新类里面实现抽象方法
    6.迪米特原则：【最少知道原则，降低类耦合】
        示例：一个学校，下属有各个学院和总部，现要求打印出学校总部员工 ID 和学院员工的 id
        原来：
            学校类：
            School.class { 
                //返回学校总部的员工
                public List<Employee> getAllEmployee() { 
                    List<Employee> list = new ArrayList<Employee>();
                    for (int i = 0; i < 5; i++) { 
                        //这里我们增加了 5 个员工到 list 
                        Employee emp = new Employee(); 
                        emp.setId("学校总部员工 id= " + i);
                        list.add(emp);
                    }
                    return list;
                }
                //该方法完成输出学校总部和学院员工信息(id) 
                void printAllEmployee(CollegeManager sub) {
                    //分析问题
                    //1. 这 里 的  CollegeEmployee 不是	SchoolManager 的直接朋友
                    //2. CollegeEmployee 是以局部变量方式出现在 SchoolManager
                    //3. 违反了 迪米特法则
                    //获取到学院员工 --> 更正部分，将此处+++封住的内容提取到类CollegeEmployee中，然后直接调用打印
                    //++++++++++++++++++++++++++++++++++++++
                    List<CollegeEmployee> list1 = sub.getAllEmployee(); System.out.println("------------学院员工------------"); 
                    for (CollegeEmployee e : list1) {
                        System.out.println(e.getId());
                    }
                    //++++++++++++++++++++++++++++++++++++++
                    //获取到学校总部员工
                    List<Employee> list2 = this.getAllEmployee(); System.out.println("------------学校总部员工------------"); 
                    for (Employee e : list2) {
                        System.out.println(e.getId());
                    }
                }
            }
            学院员工类：CollegeEmployee.class { }
            管理学院员工类：
                CollegeManager.class {
                    //返回学院的所有员工
                    public List<CollegeEmployee> getAllEmployee() { 
                        List<CollegeEmployee> list = new ArrayList<CollegeEmployee>();
                        for (int i = 0; i < 10; i++) { //这里我们增加了 10 个员工到 list
                        CollegeEmployee emp = new CollegeEmployee(); emp.setId("学院员工 id= " + i);
                        list.add(emp);
                    }
                    return list;
                }
            总部员工类：Employee.class { }
            管理总部员工类：Employee.class { }
            Main:
                School s = new School()
                s.printAllInfo();
    7.合成复用原则：【同里氏替换原则，使用合成聚合方式代替继承】
        1)找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。
        2)针对接口编程，而不是针对实现编程。
        3)为了交互对象之间的松耦合设计而努力
        
## 3.UML类图-Unified modeling language UML (统一建模语言)
    
    1.介绍：
        1.帮助开发者思考且记录思路
        2.UML本身有一套符号规定，用于描述软件模型中，各个元素之间的关系，例如类，接口，实现，泛化，依赖，组合，聚合等
        
## 4.设计模式（23种）
    
    1.介绍：
        -1.设计模式是程序语言中通用的解决方案
        -2.不区分语言
    2.分类：- 注意：不同的书籍上对分类和名称略有差别
        1)创建型模式：单例模式、抽象工厂模式、原型模式、建造者模式、工厂模式。
        2)结构型模式：适配器模式、桥接模式、装饰模式、组合模式、外观模式、享元模式、代理模式。
        3)行为型模式：模版方法模式、命令模式、访问者模式、迭代器模式、观察者模式、中介者模式、备忘录模式、解释器模式（Interpreter 模式）、
          状态模式、策略模式、职责链模式(责任链模式)。
