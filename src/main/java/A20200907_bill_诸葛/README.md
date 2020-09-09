
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
