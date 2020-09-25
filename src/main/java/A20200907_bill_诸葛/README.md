
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
    4.想要尽量避免full gc，就需要老年代尽量少，最起码不能让占满，那么垃圾对象应该尽量在新生代就回收完成
        所以需要调大新生代，防止大对象进来eden后被复制到s0区时，由于s0区内存不够，而直接被放到老年代中。
        流程示例：
        1.前提：eden:s0:s1 = 800M:100M:100M
        2.60M数据占满eden区仅需要14秒，在最后一秒钟的时候执行一半的时候，仍旧有引用会被标记为非垃圾对象，那么此时的这60M，会被放进s0区域，s0区域不够放时
            由于对象动态年龄判断机制，会直接将该大对象放于老年代中，那么每次在minor gc的时候，就有可能会将大对象放在老年代中，直到老年代占满触发full gc，
            所以要解决此问题，需要使s0,s1区域的内存加大，防止直接将大对象放进老年代，频繁触发gc
        
## 10 java常见面试题剖析
    
    1.垃圾收集器
    2.可达性分析
    3.垃圾收集算法
    
## 11 索引的本质
    0.索引结构：-一次查询代表一次I/O
        ·二叉树
        ·红黑树
        ·Hash表
        ·B-Tree
        ·B+tree
    1.问题引入：慢sql查询。几十秒到几十毫秒
        第一时间想到的优化手段-调整索引
    2.索引：帮助mysql高效获取数据的【排好序】的数据结构
    3.网址：https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
    4.二叉树：
        ·右边>左边 ，且从第一个开始维护
        ·所以当插入的数据有顺序时，例如：1,2,3,4,5,6,7,此时二叉树将会退化成链表（如图），即，查询6的时候，依旧从头至尾要查询6次，所以产生这种数据的情况下，二叉树就不在适用做索引
        ·选择其他数据结构？？？
    5.红黑树：
        ·本质上还是二叉树（二叉平衡树），依旧为右边>左边，当树的单边增长明显大于另一边的时候，树的高度会相应调整，自动平衡
        ·此时看图可知，仅需查询2次即可
        ·最后还是没有选择红黑树？为什么，海量数据的情况下会出现问题（例如500万行记录，树的高度可能在20左右，然后查找的元素刚好在末端的叶子节点）
            就算从根节点查询，也至少需要查询20次才能查到，所以红黑树树的高度不可控，所以利用索引查询数据的效率不一定高
        ·改造红黑树，使树的高度在可控范围，例如 h<=4,纵向无法拓展，那么就只能横向拓展，横向拓展意味着原来节点就要存储多个索引
    6.B-Tree: 
        ·可控制高度的红黑树，即为最后的Btree
        ·叶节点具有相同的深度，叶节点的指针为空
        ·所有索引元素不重复
        ·节点中的数据索引从左到右递增排序
    7.B+tree:多叉平衡树
        ·B-tree的变种，最后mysql的索引底层
        ·非叶子节点不存储data，值存储索引（冗余），可以放更多的索引
        ·叶子节点包含所有的索引字段，每个节点大小不超过16kb
        ·叶子节点用指针连接，提高区间访问性能
        ·看图，树的高度为3，每个节点放多个索引，去查询数据时，会先将索引读到RAM，然后确定查询数据在哪两个索引区间中，确定区间后，
            进入叶子节点，继续寻找，相对于磁盘i/o来说RAM寻找时间可以忽略不计，所以疑问？？既然RAM快为什么不直接放在一个一整条线上，
            直接使用整条线，全部放在RAM中岂不是速度更快？？
                假如，数据库有上千万条数据，那么将上千万条索引放在一个节点中真的合适吗？就查找一条数据，一次IO就把整张表的索引
                都加载进内存中，很显然不合适，所以节点的大小设置也是需要考量的。

## 14 千万级数据表如何使用索引快速查找？

    接上部分问题。
    查询mysql节点大小：
        show global status like 'innodb_page_size';
            Innodb_page_size	16384   约为16kb
    mysql主键常用bigint，占8个字节，int占4个字节，每个节点大概能放1170个索引
        一个bigint的索引占8个字节，紧跟着是6个字节的指针，再然后优势8个字节的索引，循环往复则 单个索引加指针为 6+8 =14B
        一个节点为16kb/14b= 1170.2857
        最终寻到的叶子节点不仅包含索引，还包含数据data，假设data大小=1kb，那么该节点也能放16个索引元素
        计算叶子节点上的所有索引，以图为例【A13-B+树.jpg】，树高h=3，则为1170*1170*16约为2100万
        得出结论：假设千万条数据，使用b+tree结构存储，树的高度为3，那么查询也只会经历3次磁盘i/o，更何况真实情况下，第一层节点是常驻内存的，就会更快，少走一次i/o
            所以千万级数据表，走索引，最后也就经过1-2次的i/o就能查询出来，而且底层可能也有对其他节点的索引做了内存存储
            
## 15 MyISAM索引解析-非聚集

    1.索引文件和数据文件分离
    2.存储引擎是形容数据库的还是形容数据库表的？？
        形容数据库表的
    3.mysql数据存储位置查看
        show global variables like "%datadir%";  C:\ProgramData\MySQL\MySQL Server 5.6\Data\
            ·进入ttest数据库：C:\ProgramData\MySQL\MySQL Server 5.6\data\ttest 
            ·查看数据文件：
                MyISAM：
                    tb_xxxx.frm 表结构
                    tb_xxxx.MYD 表数据
                    tb_xxxx.MYI 索引  
                InnoDB:
                    tb_emp3.frm 表结构
                    tb_emp3.ibd 数据+索引
    4.使用较少
    
## 16 InnoDB索引解析-聚集
        
    1.数据索引放在一起 -聚集索引
        主键索引：聚集索引-根据索引可直接找到数据
        非主键索引-普通索引：使用的是非聚集索引，根据普通索引找到的是主键索引，而不是数据，具体看图
    1.数据索引不放在一起 -非聚集索引
        MyISAM
        
## 17 为什么DBA推荐使用自增主键做索引
    
    1.innodb的表如果没有主键，也没有普通索引，那么他会
        从第一列数据开始寻找，找到符合条件的列，创建唯一索引，维护你的所有数据（列数据唯一）
        假设，每列数据中都有重复值，没有唯一的，那么mysql将会在表末尾维护一个隐藏列，做唯一索引列
        来确保innodb中数据+索引（B+TREE）一起存储
    2.所以推荐使用主键索引，不然mysql需要自己去寻找，自己去创建隐藏列索引，去维护索引，影响性能
    3.主键索引必须创建，为啥dba推荐使用【整型】【自增】主键？？
        很多公司主键使用uuid？为什么不好
        uuid不是整型，也不是自增，而在索引查找的时候，是需要去比大小确定区间，继而确定最终数据，因为要比大小，所以
        1.整型类型比大小显然效率高于 字符串比大小（字符串比大小需要一个一个去比较）
        2.整型bigint最多占8个字节，而uuid需要占用几十个字节
            生产环境使用的固态硬盘（高速SSD），尽量要节省空间
        3.区间指针-hash结构
            mysql索引一般使用Btree，但是还有一种类型叫Hash
            举例：select * from user where col1= 22 【假设从col1上有hash索引】
            实际查找过程：
                1.对22进行hash计算得到散列值： hash(22)
                2.通过散列值快速定位22所在行的磁盘文件地址
            是不是意味着hash比btree索引效率高？？
                是比btree高，特别是数据量大的时候，但是工作中几乎不用hash，都采用btree，为什么？
                    1.hash会有冲突，mysql底层已解决，但是不是核心原因
                    2.范围查找支持差... 例如：select * from user where col1> 22
        4.B+tree索引会在叶子节点排好序，且索引之间有双向指针，查询是定位到元素后，可以直接拿到之前或者之后的所有数据，所以范围查询支持好
            而B-tree结构在叶子节点是没有双向指针的，因此，范围查询支持差，不采用b-tree
            而最终选用的b+tree也是在B-tree上做了很多优化得来的
            例如：
                1.data数据移动：非叶子节点的data数据 移动到叶子节点
                2.索引冗余：非叶子节点的索引冗余到下级非叶子节点或者叶子节点
                3.双向指针：叶子节点之间的双向指针，便于范围查找 
            好处，同等高度下，Btree放的索引远小于B+tree能放的索引
                例如：h=3的时候
                btree约为 16*16*16     【因为每个节点上都有data，一个data为1kb，每个节点最多16个所以，树高为3，所以为 16*16*16】
                b+tree约为 1170*1170*16  约为2100万 【只有最后的叶子节点上有data，所以非叶子节点上一个索引（8字节索引+6字节指针），而一个节点所占大小16kb，因此大约一个节点1170个索引】
        5.为啥使用自增？
            使用B+tree举例：看图，在添加非自增数据时对比
            若为自增的话，那么树再次分裂的概率就会很低,在后面添加就可以，而树的分裂，平衡也是会影响数据库性能的
            若为非自增，不定时就会在树中插一次,不仅树会走一次分裂，而且还可能让树在做一次平衡
            结论：非自增的会影响insert的效率，因为可能会造成树分裂，树平衡
                插入时，先维护索引，所以使用自增的会让效率更高，维护索引的成本会比较低
                
## 19 mysql最左前缀优化原则
    
    1.联合索引的底层存储结构长什么样子？还是B+tree
        1.组成-看图
        2.索引使用B+tree，使用多个字段，那他是怎么排好序的？？？
            1.比较规则：idx_name_age_position(`name`,`age`,`position`)
                1.先比较name
                2.若name相同则比较age
                3.若age相同则比较position
        3.优化原则：
            ·最左前缀 idx_name_age_position(`name`,`age`,`position`) 根据底层的索引结构可知，第一个总是会被比较的数据，也就是只有第一个会被排好序，而第二个，第三个没有排序，所以无法使用
                走索引：name 
                走索引：name age
                走索引：name age position
            ·全值匹配（等号查询） ref=const
                正确：explain select * from user where name = 'lilei'
            ·不在索引列上做操作（计算-函数-类型装换），防止索引失效
                正确：explain select * from user where name = 'lilei'
                错误：explain select * from user where left(name,3)= 'lilei'
            ·mysql在使用不等于（!= 或 <>）的时候，不走索引
            ·尽量使用覆盖索引
            ·减少select * 语句
            ·is null,is not null一般也不走索引
            ·like以通配符开头的，索引会失效，走全表扫描
                错误：select * from user where name like '%li'
                正确：select * from user where name like 'li%'
                解决 like '%字符串%' 不走索引的方案？
                    1.使用覆盖索引,查询字段必须是建立覆盖索引的字段  idx_name_age_position
                        explain select name,age,position from user where name like '%li%';
                    2.如果不能使用覆盖索引，则需要借助搜索引擎
            ·字符串不加单引号索引失效
            ·尽量少用or 或者 in ，使用此查询不一定会走索引，mysql内部优化器会根据检索比例，表大小等多个因素整体评估是否使用索引，详见范围查询优化
            ·范围查询优化，给年龄添加单值索引 age>18 and age<25
    2.联合索引是工作中最常用的，尽量不要维护太多单列索引
        
## 20 mysql索引优化和底层数据结构
    
    1.覆盖索引为什么是最左前缀？
        索引是排好序的数据结构，只有最左边的是会被比较的，有顺序，而后面的只在当前节点有排序，而对于整个索引来说无序，因此
        要想索引生效，必须是层层递进
    2.存储引擎是什么？存储数据的技术 - MyISAM,InnoDB等
    3.mysql中的mvcc，多版本并发控制机制
        1.版本链=三个隐藏列
            row_id
            trx_id
            roll_pointer 旧版本串成单链表，版本链的头结点是当前记录最新的值
        2.readView
        3.核心问题
            判断版本链中哪些版本对当前事务可见
            版本链访问记录判断逻辑
        4.实现
            Read Committed      每一次进行普通select操作前都会生成一个ReadView
            RepeatedRead Read   只在第一次执行普通select操作前生成ReadView，之后的查询操作都重复使用这个ReadView
        5.关于undo
            ·insert undo    事务提交即释放
            ·update undo    需要支持mvcc，不能立即删除
            ·delete mark    记录打删除标记-逻辑删除
    4.主从复制
    5.分库分表
    6.锁
        锁粒度：
            行级锁
            表级锁
            页级锁
        是否独占：
            ·读锁-共享锁 阻塞写
            ·写锁-排它锁 阻塞读写
    7.缓冲机制
    8.存储引擎
        ·innodb
            ·页作为磁盘和内存直接交互的基本单位
            ·mysql默认存储引擎，5.5版本之后
            ·应用场景，事务性，安全性操作性较多的情况下
        ·myisam
            ·不支持事务操作
            ·不支持行级锁
            ·不支持外键
            ·应用场景：执行大量的select
        ·memory
            ·数据存储内存，表结构存储磁盘，访问效率高
            ·服务关闭，表中数据丢失
            ·mysql应用场景：内存表做数据缓存

## 21 redis的五种数据结构讲解
    
    看图：string，hash ，list，set ，zset
    
## 22 string结构与应用详解

    字符串常用操作：
        set key val                 存入字符串键值对
        mset key val [key val ...]  批量存储字符串键值对
        setnx key val               存入一个不存在的字符串键值对
        get key                     获取字符串键值
        mget key [key ...]          批量获取字符串键值
        del key [key ...]           删除一个键
        expire key seconds          给key设置过期时间（秒）
    原子操作
        incr key                    将key中存储的数字的值加一
        decr key                    将key中存储的数字的值减一
        incrby key increment        将key中存储的数字的值加上increment
        decrby key decrement        将key中存储的数字的值减去decrement
    应用场景：
        1.单值缓存
            命令：
                set key val
                get key
            场景：
                商品库存放在redis中，解决下单减库存高并发
        2.对象缓存
            命令：
                1. set user:1 val(json数据格式)
                2. mset user:1:name li user:1:age 18 user:1:sex 男
                   mget user:1:name user:1:age
            场景：
                存储用户session的信息：
                    第一种方式：set 用户id（key） 用户信息json（val）
                    第二种方式：mset存储
                    两种方式区别：
                        第二种方式可以修改指定字段，例如用户数据中的余额字段会被频繁更新，而其他的不需要，所以使用此种方式的好处就在这里 
        3.分布式锁
            命令：
                setnx不允许key重复
                setnx product:10001 true    //返回1代表获取锁成功
                setnx product:10001 false   //获取锁失败
                ...执行业务操作
                del product:10001           //执行完业务释放锁
                set product:10001 true ex 10 nx     //防止程序意外终止导致死锁
            场景：
                下单，秒杀，在分布式系统中 就需要做分布式锁
                在高并发情况下减库存，可能会造成超卖问题
        4.计数器
            命令：
                incr article:readcount:{文章id}
                get article:readcount:{文章id}
            场景：
                某些微博或者帖子下的阅读数等
        5.web集群session共享
            string session + redis实现session共享
        6.分布式系统全局序列号
            incrby orderid 1000 //redis批量生产序列号提升性能，每次取1000个id存到内存，在程序中计算，当id耗尽的时候重新去redis获取序列号
                //若是redis挂了id丢失不用处理
                  
## 23 hash结构与应用场景讲解
    
    等同于一个双层map
    key val 【val 又是一个 key val】
    help @hash -> 查看hash命令结构帮助
    查看redis的命令手册
    hash常用操作：
        hset key field value   存储一个hash表key的键值
        hsetnx key field value 存储一个不存在hash表key的键值
        hmset key field value [field value ...] 在一个hash表key中存储多个键值对
        hget key field                      获取hash表key对应的field键值
        hmget key field [field ...]         获取hash表key对应的field键值
        hdel key field [field ...]          删除hash表key对应的field键值
        hlen key                            返回hash表key中field的数量
        hgetall key                         返回hash表中key的所有值
        hincrby key field increment         为hash表key中field的值加上增量increment
    应用场景：
        1.对象缓存-用hash更合适
            hmset user {userid}:name lisheng {userid}:age 18 
            hmset user 1:name lisheng 1:age 18 --> 这样存储数据量很大时，不允许直接返回hash表中key的所有值  hgetall key 
                例如 user表存储 1000万条用户信息，该怎么优化？？ -> 分片存储-一种优化思想
                    再同一个redis中，可以将user表按一定规则分开，分段存储，例如 id取模==0 存储在user-1，id取模==1存储在user-2，等同于分表存储
                    再不同redis中，分片存储
            hmget user 1:name 1:age -->取数据
            一般而言缓存中只放些热点数据，对于对象缓存中数据量过大的该怎么存储？？几千几百万条数据该怎么存储，优化？
            分片存储，将业务数据按照业务规则区分，存储在不同的redis中，类似于同表不同库
            1.例如可以根据 key取模，将其存储在不同的redis
            2.例如可以按照 用户所在不同省份去区分，将其存储在不同的redis
        2.电商购物车
            1.以用户id为key
            2.商品id为field
            3.商品数量为value
          购物车操作：
            1.添加商品：hset cart:1001 1088 1
            2.增加数量：hincrby cart:1001 1088 1
            3.商品数量：hlen cart:1001
            4.删除商品：hdel cart:1001 1088
            5.获取购物车所有商品：hgetall cart:1001
    优缺点：
        优点：
            同类数据归类整合存储，方便数据管理
            相比string操作消耗内存及cpu更小
            相比string存储更节省空间
        缺点：
            过期功能不能使用在field上，只能用在key
            redis集群架构下不适合大规模使用？
                分片存储时，一个key可能会有很大的数据量，hashkey都落在一个节点上，可能会导致数据倾斜
                跟集群思想相违背，将多个hashkey让他尽量落在不同节点上
        
## 24 list结构与应用场景讲解
## 25 set结构与应用场景讲解
