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
