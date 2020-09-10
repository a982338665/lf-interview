
# 视频地址
>https://www.bilibili.com/video/BV19K411J7KM

## 3 线程栈及栈帧结构
    
    1.栈帧：线程运行一个方法，就会给这个方法分配一块自己专属的内存区域，那么这块区域就称为栈帧
        即，java中一个方法对应一个栈帧
    2.栈帧内部包含：
        ·局部变量表
        ·操作数栈
        ·动态链接 
        ·方法出口等
    3.javap指令分析类文件

## 4 局部变量表及操作数栈
    
    public class A20200907_bill_诸葛.code.A3_栈帧 {
      public static final int initData;
    
      public static A20200907_bill_诸葛.code.A3_User a3_user;
    
      public A20200907_bill_诸葛.code.A3_栈帧();
        Code:
           0: aload_0
           1: invokespecial #1                  // Method java/lang/Object."<init>":()V
           4: return
    
      public int compute();
        Code:
           0: iconst_1           推送栈顶             => 值  为什么不是从iconst_0开始？
                                 因为局部变量0，其实指的是当前方法本身【this】，所以不会被占用
           1: istore_1           将栈顶值存入局部变量 => 赋值 -在局部变量表中分配空间，将栈顶值存入局部变量
           2: iconst_2           推送栈顶             => 值
           3: istore_2           将栈顶值存入局部变量 => 赋值
           4: iload_1            从局部变量1中装载int类型值
                                     程序计数器：当前线程运行到的代码位置或者行号-解决多线程问题
                                     此时的值为 4，实际上是指方法区所在的那个内存位置。那为什么会设计程序计数器？
                                     为了多线程下，线程交替执行时，能找到上次执行到的位置，以便于程序继续执行，举例：
                                     线程1执行到第五行，被挂起，线程2执行完成后，回到线程1，继续从第五行执行，而此时的第五行就是程序计数器针对于单个线程所记录的开始执行的代码 位置
                                     由于类是加载在方法区中，字节码引擎执行加载在方法区中的方法，所以具体执行到代码的哪一行，也是由字节码引擎控制，所以程序计数器的值是由字节码执行引擎来修改的
           5: iload_2            从局部变2中装载int类型值放进操作数栈
           6: iadd               将栈顶两int型数值相加并将结果压入栈顶 - 取出相加再放回
           7: bipush        10   往栈里压个值：10
           9: imul               将栈顶里面的两个值取出后相乘获得结果压入栈顶
          10: istore_3           将栈顶值存入局部变量
          11: iload_3            从局部变3中装载int类型值放进操作数栈
          12: ireturn            从当前方法返回 操作数栈中的值
    
      public static void main(java.lang.String[]);
        Code:
           0: new           #2                  // class A20200907_bill_诸葛/code/A3_栈帧
           3: dup
           4: invokespecial #3                  // Method "<init>":()V
           7: astore_1
           8: aload_1
           9: invokevirtual #4                  // Method compute:()I
          12: pop
          13: getstatic     #5                  // Field java/lang/System.err:Ljava/io/PrintStream;
          16: ldc           #6                  // String test
          18: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
          21: return
    
      static {};
        Code:
           0: new           #8                  // class A20200907_bill_诸葛/code/A3_User
           3: dup
           4: invokespecial #9                  // Method A20200907_bill_诸葛/code/A3_User."<init>":()V
           7: putstatic     #10                 // Field a3_user:LA20200907_bill_诸葛/code/A3_User;
          10: return
    }

## 5 动态链接和方法出口

    1.动态链接：把符号引用转换为直接引用。
        public static void main(String[] args) {
                A3_栈帧 a3_栈帧 = new A3_栈帧();
                a3_栈帧.compute();//动态链接是指compute方法入口在方法区内存中的地址，即根据此地址可找到执行代码
                System.err.println("test");
                }
    2.方法出口：a3_栈帧.compute();的结果作为方法出口，定位到该行 
    3.main方法有个局部变量表，里面有new对象 A3_栈帧 a3_栈帧 = new A3_栈帧();
        那么new A3_栈帧();是放在堆中，局部变量表中存放引用地址a3_栈帧，指向堆中对象
    4.方法区：
        1.8之前叫做永久代      在堆中
        1.8及1.8之后叫元空间   使用的是直接物理内存，非堆内存
        存放：常量，类信息，静态变量
             public static A3_User a3_user =new A3_User();
             a3_user在方法区，是指针指向堆内存中的new A3_User(); 
    5.本地方法栈：
        类似于接口，他的代码实现是C语言或者c++写的【c及c++可能会有内存泄漏，java自动管理】                                         

## 6 堆内存详细讲解

    0.年轻代：eden,s0,s1
    1.new对象先放去eden区，放满后触发垃圾回收（minor gc）然后清理区域中垃圾对象
        可达性分析：-> 垃圾回收的方式          
        所有从gc roots出发可以到达的对象都被标记为 非垃圾                                                                              
    2.非垃圾在回收是会被复制到Survivor区域中的s0区，然后清理eden区，此时分代年龄+1
    3.第二次回收eden+非空的s区，将非垃圾对象复制到s1区，此时清理eden+s0，此时分代年龄+1
    3.第三次回收eden+非空的s区，将非垃圾对象复制到s0区，此时清理eden+s1，此时分代年龄+1
    4.名词：对象的分代年龄，每经历一次gc，复制一次，分代年龄+1，当分代年龄的值=15的时候,说明十五次的垃圾回收他仍旧存在，此时就把他给移入老年代
        对象头：存放分代年龄，锁状态等
    5.有哪些对象会被放入老年代？
        缓存对象，静态变量引用的对象，对象池，数据库连接池，连接对象池，Spring容器中的bean，bean容器，bean对象    
   
## 7 jvm诊断工具调优实践

    jdk自带的调优诊断工具：jvisualvm
    cmd命令窗口执行打开：jvisualvm
    当老年代也放满的时候，程序会怎么样？--> 会做一次Full GC，会对整个堆区域内的所有对象做垃圾回收，耗费时间较长
         针对于本程序，年轻代不会被回收，因为是GC-root可达对象，所以最后木得回收就会触发OOM
    jvm调优的目的：
        减少gc的次数，特别是full gc，其根本意义是减少STW
        STW-stop the word：只要gc就会触发，full gc时间会更长
            在做gc是会暂停应用线程，用户体验差
    java虚拟机为什么要设计STW机制吗？
        可能找不干净，之前非垃圾对象，后面线程执行完，又变成了垃圾对象，就会一直清理不干净，只要程序变化，就有可能，所以有了STW机制

## 8 亿级流量电商网站jvm参数调优实战
    
    前提：系统稍微有点压力就会发生full gc，很频繁【几天或几周发生一次算是正常的，隔几分钟一次就需要查找问题了】
    亿级流量网站：每日点击上亿次【京东，美团等】
    上线时需要设置jvm参数：
        每秒1000单 - 大型公司业务较复杂，每秒1000单已经很高了
        三台服务器（4核8G）
        凭经验：凭机器大小设置出了问题的
            8G内存，给操作系统预留2-3G运行防止机器宕机，还剩5-6G给java虚拟机，堆内存分配3个G，剩下的给栈内存和元空间
        凭计算：
            1.大约每台服务器1秒300多订单
            2.每个对象假定1kb（new Order()）?
                预估对象大小，看其属性所占大小，例如Order中属性id+name所占字节之和+对象头等
                    private int id;
                    private String name;
                此时int占4个字节，String可能占4个字节，也可能占8个字节（可能跟指针压缩有关）
                每个对象最多也就几十个字段，那么算下来也就几百个字节，一般而言一个对象不会超过1kb
                此时约为 300订单约为300kb
            3.下单还会涉及到其他对象，如库存，优惠券，积分等，此时将对象放大20倍，假设有20个相关对象 -- 自己评估根据业务
                此时 300KB*20/秒对象生成
            4.可能同时还有其他操作，如订单查询，及其他交易情况等，再次放大十倍
                此时 300kb*20*10/秒对象生成，约60M对象，1秒后都变为垃圾对象
            5.命令
                java -Xms3072M -Xmx3072M -Xss1M -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=512M -jar eureka-server.jar         
        
## 9 能否对jvm调优，让其基本不Full GC        
    
    1.方法执行结束后，会被销毁
    2.看图
    3.java -Xms3072M -Xmx3072M -Xmn=2048M -Xss1M -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -jar eureka-server.jar       
        年轻代调大点，避免【对象动态年龄判断】导致数据直接被放入老年代
        老年代调小点

## 10 java常见面试题剖析
    
    
