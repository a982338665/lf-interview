# 尚硅谷经典面试第一季
> https://www.bilibili.com/video/BV1Eb411P7bP

## 1 javase面试题 自增变量 Test

    public static void main(String[] args) {
            // int i  局部变量表：1   操作数栈：null
            int i = 1;
            //运算的过程是要把 值压进 操作数栈中
            //等号右边 i++ ： ++在后，后计算，先压栈
            //      1.压栈：把i的值压入操作数栈 此时，  局部变量表：1   操作数栈：1
            //      2.计算：i自增，此时，局部变量表：2   操作数栈：1
            //等号左边 i变量赋值 此时,将操作数栈的结果再赋值给 i ，即 i=1,在此过程中i是曾经变为2的，后面又变回1
            i = i++;
            //等号右边 i++ ： ++在后，后计算，先压栈
            //      1.压栈：把i的值压入操作数栈 此时，  局部变量表：1   操作数栈：1
            //      2.计算：i自增，此时，局部变量表：2   操作数栈：1
            //等号左边 j变量赋值,此时操作数栈结果1，i的结果为2（取局部变量表），操作数栈结果赋值给j，因此此时 i=2 j=1
            int j = i++;
            //等号右边：i + ++i * i++
            //      1.i压栈 局部变量表：2   操作数栈：2
            //      2.++i压栈，先++，就是先计算，后压栈，所以 ++i的压栈结果为
            //          1.计算后压栈：局部变量表：3 操作数栈：3
            //      3.i++压栈，++在后，后计算，先压栈
            //          1.压栈：把i的值压入操作数栈 此时，  局部变量表：3   操作数栈：3
            //          2.计算：i自增，此时，局部变量表：4   操作数栈：3
            //          3.i局部变量表变为 4 ，故i=4
            //等号右边计算：操作数栈结果分别为 2 3 3 ，计算为 2+3*3=11 赋值给 K ，所以k = 11
            int k = i + ++i * i++;
            //最后 i=4 j=1 k=11
            System.err.println("i=" + i);
            System.err.println("j=" + j);
            System.err.println("k=" + k);
    }
    注意：
        · 赋值 = ，最后计算
        · =右边的从左到右依次亚茹操作数栈，实际先算哪个，看运算符优先级
        · 自增，自减操作都是直接修改变量的值，不经过操作数栈，即直接修改局部变量表内的值
        · 赋值之前临时结果也 存储在操作数栈中
        · 更多详细查看 《java虚拟机规范》 关于指令的部分
        
        自增在变量，运算在栈中，栈中运算完，赋值变量中
    
## 2 单例设计模式 Test2
    
    1.定义：仅有一个实例能够被使用
    2.分类：
        饿汉式：先创建，线程安全，初始化后就生成了可用实例
        懒汉式：用的时候创建，双检锁+指令重排问题
    3.创建：构造方法私有化，属性私有化，对外提供获取实例的方法，或者构造方法私有化+对外提供公开的静态的单例实例的属性
        使用静态内部类实现单例进行延迟加载 - 懒汉式

## 3 类初始化和实例初始化  TestSon
    
    1.类初始化过程
        1.一个类要创建实例需要先加载并初始化该类
            ·main方法所在的类需要先加载和初始化
        2.一个子类要初始化需要先初始化父类
        3.一个类初始化就是执行<clinit>()方法，此方法由编译器生成，在.class文件中可以看见
            ·<clinit>()方法由静态类变量显示赋值代码和静态代码块组成
            ·类变量显示赋值代码和静态代码块从上到下执行，且只执行一次
    2.实例初始化过程
        1.实例初始化就是执行<init>()方法
            ·<init>()方法可能重载有多个，有几个构造器就有几个<init>方法
            ·由非静态实例变量显示赋值代码，非静态代码块，对应构造器代码组成
            ·非静态实例变量显示赋值代码，和非静态代码块从上到下顺序执行，对应的构造器代码最后执行
            ·每次创建实例对象，调用对应构造器，执行的就是对应的<init>()方法
            ·<init>方法的首行是super(),或super(实参列表)，及对应父类的<init>方法 
        2.方法重写问题：
            ·哪些方法不可以被重写：
                静态方法
                final方法
                private等子类中不可见的方法
            ·对象的多态性：非静态方法的重写
        3.重写的要求：
            ·方法名
            ·形参列表
            ·返回值类型
            ·抛出的异常列表
            ·修饰符
                1.重写方法的方法名和参数列表要和被重写方法一致。
                2.在 java 1.4版本以前，重写方法的返回值类型被要求必须与被重写方法一致，但是在java 5.0中放宽了这一个限制，添加了对协变返回类型的支持，
                    在重写的时候，重写方法的返回值类型可以是被重写方法返回值类型的子类。
                    示例代码如下：
                    class A{
                        public Object test(){
                        return null;
                      }
                    }
                    
                    class B extends A{
                        public String test(){
                            return null;
                        }
                    }
                    但是，对于基本数据类型，由于它们不是类，所以不能实现协变返回类型，但是使用对用的包装类则可以。
                3.重写方法不能使用比被重写方法更严格的权限，即重写方法的权限要大于或者等于被重写方法的权限。上面的代码如果改写成下面的样子将不能通过编译。
                    class A{
                        public Object test(){
                        return null;
                      }
                    }
                    class B extends A{
                        private String test(){
                            return null;
                        }
                    }
                    访问权限表：
                    修饰符	同一个类中	同一个包中	子类	不同包中
                    public	Yes	Yes	Yes	Yes
                    protected	Yes	Yes	Yes	
                    default	Yes	Yes		
                    private	Yes			
                4.private修饰不支持继承，所以无法重写private修饰的方法。static修饰的方法也不能被重写。虽然在子类中可以存在与父类中private方法和static方法相同名称的方法，
                    但是并不存在多态，所以并不是重写。
                    示例代码：
                    class Father{
                        private void method(){
                            System.out.println("父类的private方法");
                        }
                        static void staticMethod(){
                            System.out.println("父类的static方法");
                        }
                    
                        public static void main(String[] args){
                            Father son = new Son();
                            son.method();
                            son.staticMethod();
                    
                            Son son1 = new Son();
                            son1.method();
                            son1.staticMethod();
                        }
                    }
                    
                    class Son extends Father{
                        public void method(){
                            System.out.println("子类的private方法");
                        }
                        static void staticMethod(){
                            System.out.println("子类的static方法");
                        }
                    }
                5.重写方法声明抛出的异常不能比被重写方法宽泛，即不抛出异常或者抛出被重写方法中抛出异常的子类。
                
## 4 方法的参数传递机制 Test3

    1.形参是基本数据类型：传递数据值
    2.实参是引用数据类型：
        传递地址值
        特殊类型：String，包装类等对象的不可变性
      
## 5 递归与迭代 Test4

    有N步台阶，每次只能1步或2步，共有多少种走法:一步走了x ，两部走了y，那么x和y的所有解即为答案
    1.递归：
        N       走法                      走法数量
        n=1     一步                      1 种走法  
        n=2     一步一步/直接两步         2 种走法
        ========================================== 以上为基础 走一步的走法有一种，走两步的走法有两种
        n=3     
                先到f(2) 再f(1)           f(3) = f(2) + f(1) = 3
                先到f(1) 再f(2)           
        n=4     
                先到f(3) 在f(1)           f(4) = f(2) + f(3) = 5
                先到f(2) 在f(2)           f(4) = 2f(2)+f(1)
        n=5
                先到f(4) 在f(1)           f(5) = f(4) + f(3) = 8    
                先到f(3) 在f(2)           f(5) = 3f(2)+2f(1) = 6+2 =8
        ========================================== 离最后走完要么一步，要么两步所以
        n=x
                先到f(x-1) 在f(1)           f(x) = f(x-1) + f(x-2)
                先到f(x-2) 在f(2)           f(x) = f(x-1) + f(x-2)
        所以结果为：
             public static  int f(int x){
                    if(x==1 || x==2){
                        return x;
                    }else{
                        return f(x-2)+f(x-1);
                    }
             }
    2.迭代：
         N       走法                      走法数量                   累加临时值
         n=1     一步                      1 种走法  f(1) = 1         x = 1   
         n=2     一步一步/直接两步         2 种走法  f(2) = 2         y = 2
         ========================================== 以上为基础 走一步的走法有一种，走两步的走法有两种
         n=3     
                 先到f(2) 再f(1)           f(3) = f(2) + f(1) = 3
                 先到f(1) 再f(2)           第三次循环sum = f(3) = 2+1 = 3 同时为下次循环做准备  f(2)=2 f(3)=3  x=2 y=3
         n=4     
                 先到f(3) 在f(1)           f(4) = f(2) + f(3) = 5
                 先到f(2) 在f(2)           第四次循环sum = f(4) = 2+3 = 5 同时为下次循环做准备  f(4)=5 f(3)=3  x=3 y=5
         n=5
                 先到f(4) 在f(1)           f(5) = f(4) + f(3) = 8    
                 先到f(3) 在f(2)           
         ========================================== 离最后走完要么一步，要么两步所以
         n=x
                 先到f(x-1) 在f(1)           f(x) = f(x-1) + f(x-2)
                 先到f(x-2) 在f(2)           f(x) = f(x-1) + f(x-2)
    3.结语：   
        ·掉自己称为递归，利用变量原值推出新值称为迭代
        ·递归：
            优点：大问题转小问题，代码量小，精简，可读性好
            缺点：浪费空间，易造成堆栈溢出
        ·迭代：
            优点：代码运行效率好，无额外空间开销
            缺点：代码不如递归简洁，可读性差

## 6 成员变量与局部变量

    1.变量就近原则
    2.变量分类：
        ·成员变量：类中方法外
            类变量：static修饰的
            实例变量：无static修饰的
            修饰符：private public protected final static volatile transient
        ·局部变量
            方法体，形参，代码块中申明的
            修饰符：final
    3.非静态代码块的执行：每次创建实例对象都会执行
    4.方法调用规则：掉一次执行一次
    5.静态变量的值跟随的是类，而非对象，所以不管有几个对象都会网上累加
    6.值存储位置：
        ·局部变量：栈
        ·实例变量：堆（存放实例对象）
        ·类变量：方法区（用于存储被虚拟机加载的类信息，常量，静态变量，即时编译器编译后的代码等数据）
    7.作用域：
        ·局部变量：{开始，}结束
        ·实例变量：this.调用（可缺省，但是同名情况下调用需要指定），外部通过 对象.调用
        ·类变量：类名.变量名 直接调用
    8.生命周期：
        ·局部变量：每一个线程，每次调用执行都是一个新的生命周期
        ·实例变量：需要等待垃圾回收
        ·类变量：随着类的初始化而初始化，随类的卸载而消亡，该类所有的类变量都是共享的
        
## 7 spring Bean作用域的区别
    
    1.简介：
        ·scope：在Spring中，可以在<bean>元素的scope属性里设置bean的作用域，以决定这个bean是单实例还是多实例的
        ·scope默认作用域：Singleton，默认情况下，Spring只为每个在IOC容器里声明的bean创建唯一一个实例，整个IOC容器范围内都能共享该实例
            所有后续的getBean()调用和bean引用都将返回这个唯一的bean实例，该作用域被称为singeton，他是所有Bean的默认作用域
    2.作用域分类：
        ·singleton  在springIOC容器中仅存在一个bean实例，以单实例方式存在
        ·prototype  每次调用getBean()d都会返回一个新实例Bean
        ·request    每次http请求都会创建一个新的bean，该作用域仅适用于WebApplicationContext环境
        ·session    同一个http Session共享一个bean，不同的使用不同的bean，该作用域仅适用于WebApplicationContext环境

## 8 spring支持的常用数据库事务传播属性和事务的隔离级别？
    
    1.Spring事务：
        什么是事务：
        事务逻辑上的一组对数据对操作，组成这些操作的各个逻辑单元，要么一起成功，要么一起失败。
    2.事务特性（4种）：ACID
        原子性（atomicity）：强调事务的不可分割；
        一致性（consistency）：事务的执行前后数据的完整性保持一致；
        隔离性（isolation）：一个事务的执行的过程中，不应该受到其他事务的干扰；
        持久性（durability）：事务一旦结束，数据就持久到数据库。
    3.事务的传播行为：
        当一个事务方法被另一个事务方法调用时，必须指定事务该如何传播。
        例如，方法可能继续在现有事务中运行，也可能开启一个新事务，并在自己的事务中运行。
        事务的传播行可以由传播属性指定，spring指定了七种传播属性。
    4.设置方式： 
        传播行为设置：@Transactional(propagation = Propagation.REQUIRED)
        隔离级别设置：@Transactional(isolation = Isolation.REPEATABLE_READ)
            常用：
               propagation:
                    Propagation.REQUIRED：
                    Propagation.REQUIRES_NEW：
               isolation:
                    Isolation.REPEATABLE_READ:可重复读，mysql默认的隔离级别
                    Isolation.READ_COMMITTED:读已提交，oracle默认隔离级别，开发时常用的隔离级别
    5.7种传播行为：
        *保证同一个事务中
            REQUIRED支持当前事务，如果不存在，就新建一个（默认）
            SUPPORTS支持当前事务，如果不存在，就不适用事务
            MANDATORY 支持当前事务，如果不存在，抛出异常
        *保证没有在同一个事务中
            REQUIRES_NEW如果有事务存在，挂起当前事务，创建一个新的事务
            NOT_SUPPORTED 以非事务方式运行，如果有事务存在，挂起当前事务
            NEVER 以非事务方式运行，如果有事务存在，抛出异常
            NESTED 如果当前事务存在，则嵌套事务执行
    6.示例：-使用默认传播行为
        controller：
            service.test(list);
        service:
            @Transactional
            public void test(List<String> list){
                for(String str : list){
                    //调用子service
                    serviceChild.testChild(str);
                }
            }
        serviceChild：//此处使用默认传播行为
             @Transactional
             public void testChild(String str){
                dao.testChild(str);
             }
        结论：
            ·结果：若for循环中的任意一个失败，则整体失败
            ·原因：默认REQUIRED，支持当前事务，即for循环中所有内容都仅支持当前事务（for循环所在方法）
    7.示例：-使用REQUIRED_NEW传播行为,开启新事物
        controller：
            service.test(list);
        service:
            @Transactional
            public void test(List<String> list){
                for(String str : list){
                    //调用子service
                    serviceChild.testChild(str);
                }
            }
        serviceChild：
             @Transactional(propagation = Propagation.REQUIRES_NEW)
             public void testChild(String str){
                dao.testChild(str);
             }
        结论：
            ·结果：若for循环中的每一个方法都会开启一个独立的事务，即该成功的成功，该失败的失败，事务之间相互隔离
            ·原因：REQUIRES_NEW,挂起当前事务，开启新事务
    8.数据库事务并发的问题：事务 t1 和 事务 t2
        ·脏读：一个事务读取到了另一个事务的更新，但是还未提交的数据（A读到了B未提交的事物）
            t1 修改 age 20为30
            t2 读取了更新后的值 age 30
            t1 回滚 age 变为20
            t2 读到了无效值 age 30
        ·不可重复读：指读到了已经提交的事务的更改数据（修改或删除）A读数据时，B更新数据并提交，若A多次读取，则前后读取的数据不一致
            t1 读取 age=20
            t2 改为 age=30
            t1 再次读取 age=30，和第一次读取结果不同
        ·幻读：读到了其他已经提交事务的新增数据
            t1 读表 5行
            t2 插入 1行
            t1 再读 6行
    9.数据库的隔离级别：
        ·隔离级别：一个事务与其他事务隔离的程度。不同隔离级别对应不同干扰程度，隔离程度越高，数据一致性越好，并发性越若
        ·分类：
            事务隔离级别（隔离级别渐高）	                脏读	    不可重复读	幻读      概念
            读未提交（read-uncommitted）	                是	        是	        是       读取到未提交的修改
            读已提交（read-committed）	                    否	        是	        是       读取到已提交的修改
            可重复读（repeatable-read）	                    否	        否	        是       读取时，禁止其他事务修改该记录或者字段
            串行化（serializable）	                        否	        否	        否       读取时，禁止其他事务对此表进行增删改，可解决并发，但是性能较低
            mysql默认的事务隔离级别为repeatable-read innodb,开发常用的为【读已提交】
                可重复读，无法避免幻读的问题
        ·查看命令：
            select @@tx_isolation;   //查看事物隔离级别
            show variables like '%iso%'//查看事物隔离级别
            set session tx_isolation = 'read-committed';设置事物的隔离级别为读已提交
        ·各个数据库产品对事务隔离级别的支持程度
            事务隔离级别（隔离级别渐高）	                oracle      mysql
            读未提交（read-uncommitted）	                不支持	    支持    
            读已提交（read-committed）	                    支持（默认）支持    
            可重复读（repeatable-read）	                    不支持      支持（默认）    
            串行化（serializable）	                        支持	    支持
        ·注意：
             1、事务隔离级别为读提交时，写数据只会锁住相应的行
             2、事务隔离级别为可重复读时，如果检索条件有索引（包括主键索引）的时候，默认加锁方式是next-key 锁；如果检索条件没有索引，更新数据时会锁住整张表。
                 一个间隙被事务加了锁，其他事务是不能在这个间隙插入记录的，这样可以防止幻读。
             3、事务隔离级别为串行化时，读写数据都会锁住整张表
             4、隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大。
    X.Spring事务失效的原因：
        1.注解使用错误，放在了接口上：
            Spring建议在具体的类（或者类方法）上使用@Transactional注解，而不是接口上：
                在接口上使用 @Transactional 注解，只能当你设置了基于接口的代理时它才生效。因为注解是 不能继承 的，
                这就意味着如果正在使用基于类的代理时，那么事务的设置将不能被基于类的代理所识别，而且对象也将不会被事务代理所包装。
        2.数据库引擎设置问题：比如我们最常用的mysql，引擎MyISAM，是不支持事务操作的。需要改成InnoDB才能支持
        3.入口的方法必须是public，否则事务不起作用（这一点由Spring的AOP特性决定的，理论上而言，不public也能切入，但spring可能是觉得private自己用的方法，
            应该自己控制，不应该用事务切进去吧）。另外private 方法, final 方法 和 static 方法不能添加事务，加了也不生效
        4.Spring的事务管理默认只对出现运行期异常(java.lang.RuntimeException及其子类)进行回滚（至于为什么spring要这么设计：因为spring认为Checked的异常属于业务的，
            coder需要给出解决方案而不应该直接扔该框架）  
        5.@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
            @EnableTransactionManagement 在springboot1.4以后可以不写。框架在初始化的时候已经默认给我们注入了两个事务管理器的Bean
            （JDBC的DataSourceTransactionManager和JPA的JpaTransactionManager ），其实这就包含了我们最常用的Mybatis和Hibeanate了。当然如果不是AutoConfig的而是自己自定义的，请使用该注解开启事务
        6.确认你的类是否被代理了（因为spring的事务实现原理为AOP，只有通过代理对象调用方法才能被拦截，事务才能生效）
        7.确保你的业务和事务入口在同一个线程里，否则事务也是不生效的，比如下面代码事务不生效：
            @Transactional
            @Override
            public void save(User user1, User user2) {
            new Thread(() -> {
            saveError(user1, user2);
            System.out.println(1 / 0);
            }).start();
            }
        8.@Transactional的事务开启 ，或者是基于接口的 或者是基于类的代理被创建。所以在同一个类中一个无事务的方法调用另一个有事务的方法，事务是不会起作用的
            （这就是业界老问题：类内部方法调用事务不生效的问题原因）
            controller：
                service.test(list);
            service:
                //@Transactional的事务开启 ，或者是基于接口的 或者是基于类的代理被创建。所以在同一个类中一个无事务的方法调用另一个有事务的方法，事务是不会起作用的
                @Override
                public void test(List<String> list){
                    test22(list);
                }
                @Transactional
                public void test22(List<String> list){
                    for(String str : list){
                        //调用子service
                        serviceChild.testChild(str);
                    }
                }
            serviceChild：
                 @Transactional(propagation = Propagation.REQUIRES_NEW)
                 public void testChild(String str){
                    dao.testChild(str);
                 }

## 9 spring-mvc解决post请求中文乱码问题
    
    1.类：CharacterEncodingFilter
        String encoding;
        boolean forceEncoding = false
    2.原理：
        HttpServletRequest      request.setCharacterEncoding(this.encoding)
        HttpServletResponse     response.setCharacterEncoding(this.encoding)
        FilterChain             filterChain.doFilter(request,response)
    3.配置：
        1.web.xml
            <filter>
                <filter-name>CharacterEcondingFilter</filter-name>
                <filter-class>org.springfarmework.web.filter.CharacterEcondingFilter</filter-class>
                <init-param>
                    <param-name>encoding</param-name>
                    <param-value>UTF-8</param-value>
                </init-param>
                <init-param>
                    <param-name>forceEncoding</param-name>
                    <param-value>true</param-value>
                </init-param>
            </filter>
            <!-- 拦截所有请求 -->
            <filter-mapping>
                <filter-name>CharacterEcondingFilter</filter-name>
                <url-pattern>/*</url-pattern>
            </filter-mapping>
    4.以上方式只能解决 POST请求乱码，无法解决GET
        1.解决get问题：
                找到tomcat中的 server.xml修改：
                    <Connector URIEncoding="UTF-8" ... ><Connector>

## 10 spring-mvc 的工作流程
    
    1.使用方式：
        1.返回ModelAndView
            @RequestMapping("/test")
            public ModelAndView test(){
                //1.创建ModelAndView对象
                ModelAndView mav = new ModelAndView();
                //2.设置模型数据，最终会放到request域中;
                mav.addObject("user","admin");
                //3.设置视图
                mav.setViewName("success")
            }
        2.返回String:
            /**
             * 在方法的入参中传入Map，Model，ModelMap
             * 不管将处理方法的返回值设置为ModelAndView还是方法在方法中传入Map，Model，ModelMap
             * SpringMVC都会转化为一个ModelAndViewView对象
             */
            @RequestMapping("/test")
            public String test(Map<String,Object> map){
                //向Map中添加模型数据，最终会自动放到request域中
                map.put("user","admin");
                return "success";
            }
        3.前端：request域中用户：${requestScope.user}
    
## 11 mybatis中实体类和表名中的 字段名称不一致怎么办？
    
    1.ResultMap 设置映射关系
    2.写sql起别名
    3.默认驼峰命名
    
## 12 linux常用命令
    
## 13 git分支相关命令
    
    1.创建分支：
        git branch 分支名
        git branch -v 查看分支
    2.切换分支：
        git checkout 分支名
    3.创建并切换分支：
        git checkout -b 分支名
    4.合并分支：
        切换回主分支：git checkout master
        合并到之分支：git merge 分支名
    5.删除分支：
        先切换到主干：git checkout master
        git branch -D 分支名
    6.git工作流：看图

## 14 redis持久化
    
    1.类型
        ·RDB： redis database
            在指定的时间内将内存中的数据集快照写入磁盘，也就是snapshot快照，恢复时，将快照读进内存
            Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用临时文件替换上次持久化好的文件。
            整个过程中，主进程是不进行任何IO操作的，就保证了极高的性能，如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那么RDB方式将
            比AOF方式更加高效。
            缺点：
                ·虽然redis在fork时使用了写时拷贝技术，但是数据庞大时，仍然比较耗费性能
                ·在备份周期在一定间隔时间做一次备份，所以如果redis意外宕机，就会丢失在这段时间内的所有修改
            优点：可以一边正常使用，一边持久化
                ·节省磁盘空间
                ·恢复速度快 
        ·AOF： Append Of File   
            方式:以日志的形式记录每个操作，将redis执行过的所有指令记录下来（除了读操作），只许追加文件，不可改写文件，redis启动之初会读取该文件重新构建数据
                换言之，redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作
            优点：
                ·备份机制更稳健，丢失数据概率更低
                ·可读的日志文本，通过操作AOF稳健，可以处理误操作
            缺点：
                ·比RDB占用更多的磁盘空间
                ·恢复备份速度要慢
                ·每次读写都同步的话，有一定的性能压力
                ·存在个别bug，造成无法恢复

## 15 mysql什么时候建索引
    
    1.mysql官方定义：索引是帮助mysql高效获取数据的数据结构（排好序的快速查找数据结构）
        一般来说，索引本身也很大，不可能全部存储在内存中，因此索引往往以索引文件的方式存储在磁盘上
    2.优势：
        ·提高数据检索效率，降低数据库IO成本
        ·通过索引列对数据进行排序，降低数据排序成本，降低CPU消耗
    3.劣势：
        ·因为维护了索引，所以会降低更新表的速度。在对表进行insert，update，delete时，mysql不仅要保存数据，还要保存索引文件每次更新添加了索引列的字段
            都会调整因为更新所带来的的键值变化后的索引信息
        ·实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是要占用空间的
    4.需要创建索引的时候：
        1.主键自动建立唯一索引
        2.频繁所谓查询条件的字段应该创建索引
        3.关联字段，外键关系应该建立索引
        4.单键和组合索引的选择问题，组合索引的性价比更高
        5.查询中排序的字段，排序字段若通过索引访问将大大提高排序速度
        6.查询中统计或者分组字段（分组其实在内部会进行排序）
    5.不建立索引：
        1.表数据太少
        2.经常增删改的表或者字段：例如电商表中的用户余额
        3.where条件中用不到的字段
        4.过滤性不好的不适合索引：例如性别字段

## 15 JVM垃圾回收机制：详情查看：https://github.com/a982338665/lf-mkd-java-core-high-level.git

    0.运行时数据区
        ·方法区
        ·java栈
        ·本地方法区
        ·程序计数器
        ·堆
    1.垃圾回收发生在哪部分：
        堆：
    2.GC：
        频繁收集新生代
        较少收集老年代
        基本不动永久代
    3.GC算法：
            1.JVM概述：
                1.虚拟机：vm,Virtual Machine
                    -逻辑上，一台虚拟的计算机
                    -实际上，一个软件，能够执行一系列虚拟的计算指令，介于软件和硬件之间
                    -系统虚拟机：-桌面程序和服务器级别的
                        ·对物理计算机的仿真
                        ·如VMware,Oracle VirtualBox等
                    -软件虚拟机：
                        ·专门为单个计算程序而设计
                        ·如JVM等
                2.java官方虚拟机发展历程：sun
                    -当今：2014年jdk1.8发布，Hotsport融合了JRockit
                3.java常用虚拟机：
                    ·Sun/Oracle，Classic Vm/Hotsport VM/JRockit VM
                    ·IBM/Eclipse:J9 VM,https://www.eclipse.org/openj9/
                    ·OpenJDK:开源的虚拟机，https://openjdk.java.net:由此衍生出很多大公司的特定的虚拟机，如Alibaba JVM等
                        在jdk1.6的时候由Sun公司同步建立的开源项目，虚拟机开源，可拿到源码
                    ·Apache Harmony，已消亡，被OpenJdk冲击
                    ·Google Android Dalvik Vm，已消亡，被ART虚拟机顶替
                4.Sun/Oracle和OpenJDK
                    ·Sun/Oracle:
                        -从jdk9分成LTS和non-LTS版本，LTS-long term support长期稳定版
                        -jdk11是LTS，长期稳定版，目前最新的
                        -OTN协议，个人免费使用，生产环境商用收费+长期更新
                    ·Open-JDK：
                        -GPL V2协议
                        -可以免费使用，含个人和商用
                5.官方提供的Hotsport虚拟机构成：
                    一个java类，经过ClassLoader加载以后进入JVM里面，
                    JVM内存：所有的字节码文件，被加载进jvm内存
                    那么，有相应的执行机，有相应的执行机读取字节码文件内容，进行执行
                    最后转化为本地的方法指令进行工作
                    所有的java字节码可以跨平台使用，但是都要经过jvm解释执行
                    本次重点学习【JVM内存】，执行机及本地方法是由C语言写的，暂不关注
            2.JVM内存分类：
                1.java自动内存管理：
                    ·传统程序语言：由程序员手动内存管理
                        -C/C++，malloc申请内存和free手动释放内存
                        -由于程序员疏忽或程序异常，导致内存泄漏
                    ·现在程序语言：自动内存管理
                        -java/C#,采用内存自动管理
                        -程序员只需要申请使用，系统会检查无用的对象并回收内存
                        -系统统一管理内存，内存使用相对高效，但也会出现异常
                2.jvm调优：主要就是针对jvm内存管理进行的调优
                    ·一般程序员只会使用java的标准虚拟机，很少会自己去定制新的虚拟机，在某方面添加新功能
                    ·大部分程序员都是在官方的jvm下如何使得程序更加高效
                3.jvm内存：
                    ·线程私有内存：java程序运行中的每个线程都含有的内容
                        -程序计数器 Program Counter Register（PC）
                            -一块小内存，每个线程都有
                            -PC只要用来存储当前方法：线程正在执行的方法称为该线程的当前方法
                            -当前方法为本地（native）方法时，pc值未定义[当前方法为C语言方法时，未定义]
                            -当前方法为非本地方法时，pc包含了当前正在执行指令的地址[当前语言为java方法时]
                            -当前唯一一块不会引发OutOfMemaryError异常[小内存存储指令地址不会爆发异常]
                        -java虚拟机栈 jvm stack - java栈
                            - 每个线程有自己独立的java虚拟机栈，线程私有
                            - -Xss设置每个线程堆栈大小
                            - java方法的执行基于栈
                                -每个方法从调用到完成对应一个栈帧在栈中入栈，出栈的过程
                                    ·栈帧存储局部变量表，操作数栈等
                                    ·局部变量表存放方法中存在"栈"里面的东西
                                -引发的异常 - 代码展示
                                    -栈的深度超过虚拟机规定深度，StackOverflowError异常
                                    -程序中使用过多变量，无法拓展内存，OutOfMemoryError等
                        -本地方法栈 Native Method Stack
                            -存储本地（native）方法执行信息，线程私有
                                ·java调用c程序的时候，c的函数就叫做native方法，会存储在本地方法栈里
                            -vm规范没有对本地方法栈做明显规定,靠每个虚拟机厂商自己实现
                            -引发的异常：
                                -栈的深度超过虚拟机规定深度，StackOverflowError异常
                                -程序中使用过多变量，无法拓展内存，OutOfMemoryError等
                    ·多线程共享内存：
                        -堆 Heap
                            -虚拟机启动时创建，所有线程共享，占地最大
                            -对象实例和数组都是在堆上分配内存，int，byte等基本类型数据都是在jvm栈上来定义
                            -垃圾回收的主要区域
                            -设置大小：程序能否高效运行的重要因素 - 代码展示两个参数对程序的影响
                                · -Xms 初始堆值
                                · -Xmx 最大堆值 
                            -引发的异常：
                                · 无法满足内存分配要求，OutOfMemoryError等【内存泄漏/内存不足】
                                · JVM参数配置 -XX:-OmitStackTraceInFastThrow 会显示所有的堆栈异常信息
                                · 所以当一些异常抛出的足够多的时候，JIT编译器会优化掉异常堆栈的信息。加入** -XX:-OmitStackTraceInFastThrow**参数才会显示所有的信息。
                        -方法区 Method Area
                            ·运行时常量池 Run-Time Constant Pool
                                -Class文件中常量池的运行时表示
                                    ·每个class文件中都有一个Constant Pool,在运行的时候，这个类被加载进来，class里面的constant pool就会被映射到内存区域上
                                -属于方法区的一部分，没法单独进行大小调整，只能调整方法区的大小
                                -动态性
                                    ·java语言并不要求常量一定只有在编译期产生
                                    ·比如使用String.intern方法，也可以将运行时的数据放入常量池
                                -引发的异常：
                                    · 无法满足内存分配要求，OutOfMemoryError等【内存泄漏/内存不足】
                            -存储jvm已经加载类的结构，线程共享：运行时常量池，类信息，常量，静态变量等
                                -一个jvm能够加载多少个类，这个将会被方法区的大小限制
                            -jvm启动时创建，逻辑上属于堆的一部分
                            -很少做垃圾回收
                            -引发的异常【javaee编写时，jsp会被编译成servlet，此时他的类有可能会超过最大限制，导致内存溢出】
                                · 无法满足内存分配要求，OutOfMemoryError等【内存泄漏/内存不足】
                4.总结表
                    |名称|线程私有/共享|功能|大小|异常|
                    |---|---|---|---|---|
                    程序计数器|私有|保存当前线程执行方法|通常固定大小|不会
                    jvm栈|私有|方法的栈帧|-Xss|OutOfMemoryError & StackOverflowError
                    本地方法栈|私有|存储native方法信息|通常固定大小|OutOfMemoryError & StackOverflowError
                    堆|共享|存储对象和数组|-Xms -Xmx|OutOfMemoryError
                    方法区|共享|存储类结构，常量，静态变量|-XX参数设置[方法区大小随着jdk不同的版本设置的参数还不一样-代码展示]|OutOfMemoryError
                    运行时常量池|共享|常量池运行时表示|从属于方法区|OutOfMemoryError
                5.总结：
                    ·jvm内存分类
                    ·对照class文件，理清jvm各部分内存的作用
            3.JVM内存参数：通过调整内存参数，使程序运行高效
                1.JVM默认的运行参数：
                    -支持jvm运行的重要配置，根据操作系统，物理硬件不同而不同，特别是内存，机器的物理内存越多，可能有些参数就越大
                        ·两台机器安装同版本的jdk，然后运行出来的程序，他的jvm默认参数可能有所不同
                    -使用-XX:+PrintFlagsFinal 显示VM参数
                        ·完整命令：1M = 1024KB = 1024*1024B 查看堆内存大小
                            D:\go-20191030\CoreHighLevel\src\main\java\pers\li\annotation\$6>java -XX:+PrintFlagsFinal -version | findstr HeapSize
                                uintx ErgoHeapSizeLimit                         = 0                                   {product}
                                uintx HeapSizePerGCThread                       = 87241520                            {product}
                                uintx InitialHeapSize                          := 201326592                           {product} [换算：201326592b/1024/1024 = 192M ] 初始堆内存
                                uintx LargePageHeapSizeThreshold                = 134217728                           {product} [128M] 
                                uintx MaxHeapSize                              := 3191865344                          {product} [3044M = 2G 996M] 最大堆内存
                            java version "1.8.0_231"
                            Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
                            Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
                        ·命令解析：
                            1.使用管道查找堆内存大小：| findstr HeapSize
                                findstr 对关键字的查找
                2.程序启动的两类参数：- param.jpg
                    -程序参数：程序需要，存储在main函数的形参数组中
                    -虚拟机参数：更改默认配置，用以指导进程运行
                        · -X参数，不标准，不在所有的VM中通用 non-standard
                        · -XX参数，不稳定，容易变更  non-stable ，可能下个版本参数就被废弃了
                3.本示例使用 JDK1.8
                    -jvm堆内存：设置最大值为20M:-Xmx20M 【HeapOOM】
                        -循环创建对象 Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
                    -jvm栈内存：【JvmStackSOF】 StackOverFlowError -Xss1M
                        -循环调用方法 - 递归无限调用
                        -循环方法中局部变量 - 【JvmStackSOF】
                        -多线程 - 【JvmStackOOM】
                    -方法区：-XX参数命令，可能会变更，不稳定
                        -存储类信息，常量池，静态变量等
                        -1.7及以前，永久区（Perm），-XX:PermSize,-XX:MaxPermSize
                            查看命令：java -XX:+PrintFlagsFinal -version | findstr Perm
                        -1.8及以后，元数据区，-XX:MetaspaceSize,-XX:MaxMetaspaceSize,
                            查看命令：java -XX:+PrintFlagsFinal -version | findstr Meta
                            D:\go-20191030\CoreHighLevel\src\main\java\pers\li\annotation\$6>java -XX:+PrintFlagsFinal -version | findstr Meta
                                uintx InitialBootClassLoaderMetaspaceSize       = 4194304                             {product}
                                uintx MaxMetaspaceExpansion                     = 5451776                             {product}
                                uintx MaxMetaspaceFreeRatio                     = 70                                  {product}
                                uintx MaxMetaspaceSize                          = 4294901760                          {product}    默认的方法区最大值：约等于4G
                                uintx MetaspaceSize                             = 21807104                            {pd product} 默认的方法区初始大小：20.79M
                                uintx MinMetaspaceExpansion                     = 339968                              {product}
                                uintx MinMetaspaceFreeRatio                     = 40                                  {product}
                                 bool TraceMetadataHumongousAllocation          = false                               {product}
                                 bool UseLargePagesInMetaspace                  = false                               {product}
                            java version "1.8.0_231"
                            Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
                            Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
                        -存储类信息，若一个项目有很多类，那么默认的方法区是否能够容纳下这么多类，所以在程序运行前，限定最大方法区大小为9M：
                            -XX:MaxMetaspaceSize=9M 【JavaCompilerTask】在线编译类函数
                            循环注册类，直到内存溢出 OutOfMemoryError 
                            注意：因此在写程序时，尽量不使用import * ,因为加载的类越多，方法区所承受的压力就越大，若整个系统要依赖的jar包有几百个，
                                  每个jar有几百类，那么方法区消耗就会很快
                4.总结：
                    ·jvm运行参数
                    ·各个区域的调优及参数配置
            4.java对象引用：--> 判断无用对象
                1.垃圾收集器：
                    ·jvm有内置垃圾收集器
                        -GC，Garbage Collector
                        -自动清除无用的对象，回收内存
                    ·垃圾收集器的工作职责（John Mccarthy提出，Lisp语言发明者，人工智能奠基者之一，图灵奖获得者，提出任务分时，和垃圾回收）
                        -什么内存需要收集 - 判定无用对象
                        -什么时候回收 - 何时启动，不影响程序正常运行
                        -如何回收 - 回收过程要求速度快，时间短，影响小
                2.java对象的生命周期：
                    1.对象通过构造函数创建，但是没有析构函数回收内存
                        构造函数和析构函数：
                            构造函数：创建对象过程
                            析构函数：清除对象过程 -> java中没有析构函数，因为自动垃圾回收机制
                                变量创建站内存，引用消失，回收内存
                                GC垃圾回收器：回收算法关系性能好坏，是jvm重点
                    2.对象存在在离他最近的一对大括号中:执行完，可回收，不是立刻回收
                    3.虽然java程序中没有析构函数，但是提供了其他内存回收的api
                        -Object的finalize方法，垃圾回收器在回收对象时调用，有且仅被调用一次
                        -System的gc方法，运行垃圾收集器
                        -以上两种方法均不靠谱，因为虚拟机会自己决定什么时候来运行垃圾收集器，所以即使写上，虚拟机也不一定会运行垃圾收集器，所以目的转变为怎么使垃圾收集器快速定位到 可回收对象
                    4.基于对象引用判定无用对象
                        -零引用和互引用等
                    5.对象引用链：
                        -通过一系列的称为"GC Roots"的对象作为起始点，从这些节点开始向下搜索，搜索所走过的路径称为引用链，当一个对象到GC roots没有任何引用链相连
                            （用图论话来说，从GC Roots到这个对象不可达）时，则证明此对象是不可用的
                        -GC Roots对象包括：--> 当前活着的对象可当做出发点来用
                            -虚拟机栈中的引用对象
                            -方法区中类静态属性引用的对象
                            -方法区中常量引用的对象
                            -本地方法栈中引用的对象
                    6.为了加快垃圾收集器判定无用对象的速度，java提供了几种不同的引用方法：
                        Java执行GC判断对象是否存活有两种方式其中一种是引用计数。
                            引用计数：Java堆中每一个对象都有一个引用计数属性，引用每新增1次计数加1，引用每释放1次计数减1。
                            在JDK 1.2以前的版本中，若一个对象不被任何变量引用，那么程序就无法再使用这个对象。也就是说，只有对象处于(reachable)可达状态，程序才能使用它。
                            从JDK 1.2版本开始，对象的引用被划分为4种级别，从而使程序能更加灵活地控制对象的生命周期。这4种级别由高到低依次为：强引用、软引用、弱引用和虚引用
                        -强引用：所有对象的赋值，均叫做强引用
                            ·例如：Object obj1 = new Object(); Object obj2 = obj1; 当obj1置为null，其实例仍赋值给obj2，所以该实例仍然不会被回收
                            ·只要强引用存在，对象就不会被回收，哪怕发生OOM异常 - OutOfMemoryError
                            ·场景：
                                在一个方法的内部有一个强引用，这个引用保存在Java栈中，而真正的引用内容(Object)保存在Java堆中。
                                当这个方法运行完成后，就会退出方法栈，则引用对象的引用数为0，这个对象会被回收。
                                但是如果这个strongReference是全局变量时，就需要在不用这个对象时赋值为null，因为强引用不会被垃圾回收。
                                ArrayList的Clear方法:方法内存数组中存放的引用类型进行内存释放特别适用，这样就可以及时释放内存。
                        -软引用：
                            ·描述有用，但非必须的对象
                            ·在系统将要发生内存溢出异常之前，会把这些对象列为可回收
                            ·JDK提供了SoftReference类来实现软引用
                            ·场景：页面缓存问题，若未被回收，取出来，已回收，重新获取，常用于缓存数据
                        -弱引用：
                            ·描述非必须对象，比软引用更弱一些
                            ·被软引用关联的对象，只能生存到下一次垃圾收集器发生之前
                            ·jdk提供了WeakReference类来实现弱引用
                        -虚引用：用来跟踪对象的回收过程
                            ·最弱的引用关系，jdk提供PhantomReference实现虚引用
                            ·为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知，用于对象回收跟踪
                            ·场景：
                                虚引用必须和引用队列(ReferenceQueue)联合使用。当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，
                                把这个虚引用加入到与之关联的引用队列中。
                                程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要进行垃圾回收。如果程序发现某个虚引用已经被加入到引用队列，
                                那么就可以在所引用的对象的内存被回收之前采取必要的行动。
                    7.总结：
                        强引用：正常赋值        不回收
                        软引用：SoftReference   内存紧张时回收
                        弱引用：WeakReference   只要gc就回收
                        虚引用：PhantomReference随时被回收
                        软引用和弱引用适合保存可有可无的缓存数据
                        在日常程序编写中，我们对一些不重要的数据，例如缓存数据等，可以采用软引用，弱引用可以加快对象所占用的内存的回收速度
            5.垃圾收集算法：--> 什么时候回收不影响程序运行，如何回收好
                1.引用计数法：4种引用
                    1.介绍：
                        -一种古老的算法，每个对象都有一个引用计数器
                        -有引用，计数器加一，失效减一
                        -计数器为0的时候，回收
                    2.优点：
                        -简单高效
                    3.缺点：
                        -无法识别对象直接相互循环使用 ，例如 a引用b，b引用a，但是其他外部没有引用a和b,那么a和b作为整体是可以被回收的，但是用词方法无法回收
                2.标记-清除：可以根据引用计数法标记出来
                    1.介绍：
                        -标记阶段：标记出所有需要回收的对象
                        -回收阶段：统一回收所有被标记的对象
                    2.优点：简单
                    3.缺点：
                        -效率不高
                        -内存碎片：内存不连续
                3.复制算法：
                    -将可用内存容量划分为大小相等的两块，每次只使用其中的一块
                    -当这一块用完了，就将还存活的对象复制到另一块上面去
                    -然后在把已使用过的内存空间一次清理掉
                    -优点：简单高效
                    -缺点：
                        ·可用内存减少：本来是一整块，后面可用的只有一半
                        ·对象存活率高时，复制操作较多
                4.标记-整理：
                    -标记阶段：与“标记-清除”算法一样
                    -整理阶段：让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存
                    -优点：
                        -避免碎片产生
                        -无需两块相同内存
                    -缺点：
                        -计算代价大，标记清除+碎片整理
                        -更新引用地址
                5.分代收集：-> 标准JVM普遍采用的垃圾收集算法
                    -java对象根据生命周期不同，有长有短
                    -根据对象存活周期，将内存划分为新生代和老年代
                    -新生代：-会频繁gc
                        ·主要存放短暂生命周期的对象
                        ·新创建的对象都先放入新生代，大部分新建对象在第一次gc时被回收
                    -老年代：-不会频繁gc
                        ·一个对象经过几次gc仍存活，则放入老年代
                        ·这些对象可以存活很长时间，或者伴随程序一生，需要常驻内存的，可以减少回收次数
                    -针对不同代进行不同算法：
                        -新生代：复制算法，包含两部分
                            -Eden区：占比80%
                            -Survivor区：占比20%
                                ·From Space 50%
                                ·To Space   50%
                            -解释：
                                1.对象创建优先放在 Eden和From Space区
                                2.在第一次GC的时候会将里面还活着的内容复制到 To Space区
                                3.因为新生代对象，大部分在第一次gc的时候都会死掉，都会被回收，只有少部分会被放到To Space里面
                                4.那么 Eden和From Space就会被清空
                                5.新的对象 被放在 Eden 和 To Space里面
                                6.下次gc ，将eden 和 To space里面活着的对象放到 From Space
                                7.那么 Eden 和To Space就会被清空
                                8.所以新生代里会采用复制的算法，进行活的对象的拷贝
                        -老年代：标记清除或标记整理
                            -由于老年代的存活率非常高，所以采用标记-清除，或标记-整理的方式
                        -注意：永久代-jdk6和jdk7，jdk8的时候重命名为元数据区，里面存放的都是类结构等
                6.总结：
                    理解各种垃圾收集方法的基本原理
                    理解分代收集和java堆内结构
            6.JVM堆内存参数设置和GC跟踪：
                1.堆内存参数：
                    ·-Xms 初始堆大小
                    ·-Xmx 最大堆大小
                    ·-Xmn 新生代大小：包含eden,from,to
                    ·-XX:SurvivorRatio设置eden区/from(to)的比例
                    ·-XX:NewRatio 设置老年代/新生代比例
                    ·-XX:+PrintGC/-XX:+PrintGCDetails打印GC的过程信息：可以看出垃圾收集器在垃圾收集过程中内存变化情况
                2.Hotspot现有垃圾收集器：
                    ·串行收集器  Serial Collector 
                    ·并行收集器  Parallel Collector   大内存可用
                    ·CMS收集器 Concurrent Mark Sweep Collector java高版本
                    ·GI收集器  Garbage-First Collector  java高版本 
                    ·Z收集器   Z Garbage Collector java高版本
            7.jvm内存管理总结和展望     
                ·自己编译一个jvm
                ·jvm的多种垃圾收集器的使用和区别
                ·理解每一种内存分类的职责，熟悉相关参数设置
                ·根据GC报告和VM监控。调整正确的内存分配策略，例如新生代/老生代之比

## 17 redis在项目中的使用场景
    
    1.数据类型：
        String  封锁ip地址，Incrby命令，记录某段时间内ip的访问次数
        Hash    存储用户信息【id，name，age】 Hset(key,field,value) 
                Hset(userKey,name,admin)
                Hset(userKey,age,18)
                Hset(userKey,id,101)
                Hget(userKey,id) //可以取出某个固定字段的值
                为什么不使用String存储用户信息
                Set(userKey,用户信息字符串)？
                get(userKey)取出来的所有值，反序列化，不能只取出要修改的那个字段，所以增加了IO次数，降低了程序性能
        List    实现最新消息排行，还可以利用List的push命令将任务存在list集合中，同时使用另一个命令，将任务从一个集合中取出[pop]
                这种数据类型可以用来模拟消息队列：实际场景使用，电商中的秒杀可采用此种方式来完成一个秒杀活动
        Set     自动去重，比如微博中将每个人的好友存在集合中（Set），求两个人的共同好友的操作，只需要求交集即可
        Zset    以某一个条件为权重进行排序，京东:商品详情，综合排名，还可以按照价格排名，可以使用zset完成

## 18 ElasticSearch和solr的区别
    
    1.背景：
        都是基于Lucene搜索服务器基础上开发的优秀的，高性能的企业级搜索服务器
        【因为都是基于分词计数构建倒排索引的方式进行查询】
    2.开发语言：都是基于java
    3.诞生时间：
        solr：2004
        es： 2010，功能更强大
    4.区别：
        ·当实时建立索引时，solr会产生io阻塞，es不会，所以es查询性能更好
        ·不断动态添加数据的时候，solr的检索效率会变差，es不会
        ·solr利用zookeeper进行分布式管理，而es自身带有分布式管理功能
        ·solr一般部署到web服务器（tomcat）才能使用，启动tomcat时候，需要配置tomcat与solr的关系【solr本质是一个动态的web项目】
        ·solr支持更多的格式数据【xml,csv,json等】，而es仅支持json文件格式
        ·solr是传统搜索应用的有力解决方案，ES更适用于新兴的实时搜索应用
            ·对已有数据检索，solr效率更好，高于es
            ·对实时数据检索，es更好
        ·solr官网提供的功能更多，而es本身更注重于核心功能，高级功能多由第三方插件提供
    5.常用：目前推荐ES
      
## 19 单点登录
    
    1.定义：一处登录，处处使用
    2.使用：多用于分布式系统中
    3.认证中心：
        京东：-- 登录时，将分配的token放入cookie中，在后面使用时带着token即可
        
## 20 购物车实现
    
    1.购物车跟用户的关系
        ·一个用户必须对应一个购物车
        ·单点登录在开发购物车之前
    2.购物车有关的操作有哪些
        ·添加购物车（是否区分登录，未登录）
            ·先存储在redis中
            ·后存储在mysql中，保障数据库安全性
        ·展示购物车
            ·显示redis+cookie中的数据
            
## 21 消息队列
    
    1.背景：分布式如何处理高并发，高并发环境下，来不及同步处理用户发送的请求，则会导致请求发生阻塞
        例如，大量的insert，update之类的请求同时到达数据库会导致无数的行锁表锁，甚至会导致请求堆积，从而触发too many connections 错误。
        使用消息队列，异步处理
    2.场景：
        排队
    3.弊端：
        消息的不确定性：延迟队列和轮询技术解决该问题
        推荐使用 activemq，环境都是java，大数据就是用kafka
            
## 22 java的基本数据类型都放在栈中？https://www.cnblogs.com/xiohao/p/4296059.html
    
    ·结论：：：很可能所有的基本数据类型都存在栈上。不分是局部变量还是成员变量
    ·以下内容待商榷：--> 可能不对，请看疑问
        基本数据类型是放在栈中还是放在堆中，这取决于基本类型在何处声明，下面对数据类型在内存中的存储问题来解释一下：
        一：在方法中声明的变量，即该变量是局部变量，每当程序调用方法时，系统都会为该方法建立一个方法栈，其所在方法中声明的变量就放在方法栈中，当方法结束系统会释放方法栈，其对应在该方法中声明的变量随着栈的销毁而结束，这就局部变量只能在方法中有效的原因
          在方法中声明的变量可以是基本类型的变量，也可以是引用类型的变量。
             （1）当声明是基本类型的变量的时，其变量名及值（变量名及值是两个概念）是放在JAVA虚拟机栈中
             （2）当声明的是引用变量时，所声明的变量（该变量实际上是在方法中存储的是内存地址值）是放在JAVA虚拟机的栈中，该变量所指向的对象是放在堆类存中的。
        二：在类中声明的变量是成员变量，也叫全局变量，放在堆中的（因为全局变量不会随着某个方法执行结束而销毁）。
          同样在类中声明的变量即可是基本类型的变量 也可是引用类型的变量
           （1）当声明的是基本类型的变量其变量名及其值放在堆内存中的
           （2）引用类型时，其声明的变量仍然会存储一个内存地址值，该内存地址值指向所引用的对象。引用变量名和对应的对象仍然存储在相应的堆中
    ·疑问：A20200807_bili第一季.TestBaseData2 及 A20200807_bili第一季.TestBaseData
        貌似是不对的，在类下定义一个int a = 1;在方法里定义int a = 1;比较两者
        this.a = a;返回true；说明存放地址一样，应该是栈里。
    
## 23 java中的逃逸分析：https://segmentfault.com/a/1190000016803174
    
    1.引言：
        java程序运行时，jvm会将class文件解释成机器能够识别的指令，指令转换过程中，产生耗时，延缓程序运行速度，为解决此种问题，出现了JIT技术【即时编译】
        JIT主要的两个功能：
            ·缓存
            ·代码编译优化
        而在JIT代码优化过程中，最重要的就是逃逸分析【Escap Analysis】
    2.逃逸分析：
        定义：就是分析java的动态作用域。
            方法逃逸：当一个对象被定义后，可能会被外部对象引用，称之为【方法逃逸】
            线程逃逸：可能被其他线程所引用
        示例：
            //String对象被定义后可被外部对象引用，所以对象[逃逸]
            public static String str(){
                String sb = "ello";
                return sb;
            }
            //StringBuffer被定义后，没有被外部引用，所以没有[逃逸]
            public static String str(){
                StringBuffer sb =  new StringBuffer("ello");
                return sb.toString();
            }
    3.利用逃逸分析，编译器对代码作如下优化
        ·同步省略：【不会被多线程访问时-锁消除】
            在JIT编译过程中，如果发现一个对象不会被多线程访问，那么针对这个对象的同步措施就可以省略掉，即【锁消除】。
            例如Vector和StringBuffer这样的类，他们很多方法都是有锁的，当某个对象确定是线程安全的情况下  ，jit在编译这段
            代码时会进行锁消除来提升效率
        ·标量替换：
            标量：是指无法在分解成更细粒度的数据（例如，int，long）
            聚合量Aggregate：一个数据可以继续分解
            在JIT编译过程中，经过逃逸分析确认一个对象不会被其他线程或者方法访问，那么会将对象的创建替换为多个成员变量的创建，称之为标量替换。
            示例：
                public class EscapeObject {
                    private static void getUser() {
                        User user = new User("张三", 18);
                        System.out.println("user name is " + user.name + ", age is " + user.age);
                    }
                    public static void main(String[] args) {
                        getUser();
                    }
                }
                class User {
                    String name;
                    int age;
                    public User(String name, int age) {
                        this.name = name;
                        this.age = age;
                    }
                }
                //上面代码中，对象 user 只会在getUser()方法中被调用，那么 JIT动态编译时，不会创建对象 user，而之创建它的两个成员变量 name 和 age,类似于以下
                private static void getUser() {
                    String name = "张三";
                    int age = 18;
                    System.out.println("user name is " + user.name + ", age is " + user.age);
                }
                public static void main(String[] args) {
                    getUser();
                }
                //标量替换减少了创建对象需要的堆内存，同时也不用进行 GC。
        ·栈上分配
            「栈上分配」是指对象和数据不是创建在堆上，而是创建在栈上，随着方法的结束自动销毁。
            但实际上，JVM 例如常用的「HotSpot」虚拟机并没有实现栈上分配，实际是用「标量替换」代替实现的。
            在 JAVA 中，对象只分配在堆中：
                堆是所有的对象实例以及数组分配内存的运行时数据区域。
        ·如何开启逃逸分析
            可以通过设置 JVM 参数来开启或关闭逃逸分析
            -XX:+DoEscapeAnalysis :开启逃逸分析（从JDK1.7开始默认开启）
            -XX:-DoEscapeAnalysis :关闭逃逸分析

## 24 对象和数组并不是都在堆上分配内存的。

    1.《深入分析java的编译原理》
    2.java，javac编译和JIT编译原理的区别
    3.JIT编译：
        ·缓存
        ·代码优化：例如逃逸分析，锁消除，锁膨胀，方法内联，空值检查消除、 类型检测消除、 公共子表达式消除等
    4.jvm内存分配策略：
        1.原来的常识：
            ·Java虚拟机所管理的内存包括方法区、虚拟机栈、本地方法栈、堆、程序计数器等
            ·JVM中运行时数据存储包括堆和栈。这里所提到的栈其实指的是虚拟机栈，或者说是虚拟栈中的局部变量表。
            ·栈中存放一些基本类型的变量数据（int/short/long/byte/float/double/Boolean/char）和对象引用
            ·堆中主要存放对象，即通过new关键字创建的对象。
            ·数组引用变量是存放在栈内存中，数组元素是存放在堆内存中。
        2.《深入理解Java虚拟机中》书中指出的有悖常识的地方：
            ·随着JIT编译期的发展与逃逸分析技术逐渐成熟，栈上分配、标量替换优化技术将会导致一些微妙的变化，所有的对象都分配到堆上也渐渐变得不那么“绝对”了。
            
        3.结论：即【对象和数组不一定会分配在堆上】
        4.分析：
            编译期间，JIT对代码的优化，减少内存堆分配的压力，其中一种重要的技术->【逃逸分析】
    5.逃逸分析：
        1.定义：
            逃逸分析(Escape Analysis)是目前Java虚拟机中比较前沿的优化技术。这是一种可以有效减少Java 程序中【同步负载】和【内存堆分配压力的跨函数全局数据流】分析算法
            通过逃逸分析，Java Hotspot编译器能够分析出一个新的对象的引用的使用范围从而决定是否要将这个对象分配到堆上。
        2.分类：
            ·方法逃逸
            ·线程逃逸
        3.使用逃逸分析后编译器可以对代码有什么优化？
            ·同步省略 - 锁消除
            ·将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会逃逸，对象可能是栈分配的候选，而不是堆分配。
            ·分离对象或标量替换。有的对象可能不需要作为一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在内存，而是存储在CPU寄存器中
    6.代码演示：A20200807_bili第一季.Test24
        1.关闭逃逸分析：指定JVM参数
            -Xmx4G -Xms4G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError 
            ·结果：
                1.在程序打印出 cost XX ms 后，代码运行结束之前，我们使用[jmap][1]命令，来查看下当前堆内存中有多少个User对象：
                2.执行命令及结果：
                    ➜  ~ jps    //使用jps查出进程号
                    2809 StackAllocTest
                    2810 Jps
                    ➜  ~ jmap -histo 2809  //使用jmap统计StackAllocTest类的实例
                     num     #instances         #bytes  class name
                    ----------------------------------------------
                       1:           524       87282184  [I
                       2:       1000000       16000000  StackAllocTest$User //100万
                       3:          6806        2093136  [B
                       4:          8006        1320872  [C
                       5:          4188         100512  java.lang.String
                       6:           581          66304  java.lang.Class
            ·结论：
                ·从上面的jmap执行结果中我们可以看到，堆中共创建了100万个StackAllocTest$User实例。
                ·在关闭逃避分析的情况下（-XX:-DoEscapeAnalysis），虽然在alloc方法中创建的User对象并没有逃逸到方法外部，但是还是被分配在堆内存中。
                    也就说，如果没有JIT编译器优化，没有逃逸分析技术，正常情况下就应该是这样的。即所有对象都分配到堆内存中。
        2.开启逃逸分析：指定JVM参数
            -Xmx4G -Xms4G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError 
            ·结果：
                1.在程序打印出 cost XX ms 后，代码运行结束之前，我们使用jmap命令，来查看下当前堆内存中有多少个User对象：
                2.执行命令及结果
                    ➜  ~ jps //获取进程号
                    709
                    2858 Launcher
                    2859 StackAllocTest
                    2860 Jps
                    ➜  ~ jmap -histo 2859 //统计实例数
                     num     #instances         #bytes  class name
                    ----------------------------------------------
                       1:           524      101944280  [I
                       2:          6806        2093136  [B
                       3:         83619        1337904  StackAllocTest$User //8万实例
                       4:          8006        1320872  [C
                       5:          4188         100512  java.lang.String
                       6:           581          66304  java.lang.Class                  
            ·结论：
                从以上打印结果中可以发现，开启了逃逸分析之后（-XX:+DoEscapeAnalysis），在堆内存中只有8万多个StackAllocTest$User对象。
                也就是说在经过JIT优化之后，堆内存中分配的对象数量，从100万降到了8万。

                除了以上通过jmap验证对象个数的方法以外，读者还可以尝试将堆内存调小，然后执行以上代码，根据GC的次数来分析，也能发现，开启了逃逸分析之后，
                在运行期间，GC次数会明显减少。正是因为很多堆上分配被优化成了栈上分配，所以GC次数有了明显的减少。
    7.总结：
       不是所有的对象和数组都会在堆内存分配空间：
       随着JIT编译器的发展，在编译期间，如果JIT经过逃逸分析，发现有些对象没有逃逸出方法，那么有可能堆内存分配会被优化成栈内存分配。
       但是这也并不是绝对的。就像我们前面看到的一样，在开启逃逸分析之后，也并不是所有User对象都没有在堆上分配             
