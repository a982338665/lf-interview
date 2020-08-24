
## 介绍
>此md主要做文档描述
>本项目此md代码讲解目录：A20200824DataStrutures
>详细代码讲解地址： https://github.com/a982338665/lf-leetcode-java-DataStructuresAndAlgorithms.git

### 1 数据结构
      
    1.逻辑关系：集合，线性，树形，图
    2.物理关系：内存存储：
        ·顺序存储：数组-连续内存空间存储
        ·链式存储：链表-非连续存储，寻址时根据其指针指向寻找

### 2 数组
    
    简介：
        数组不属于基本数据类型，所以其变量名在栈内存中，其引用指向堆内存(即数组元素值)
        --1.引用数据类型：定义数组仅是定义了一个指针，而未指向实际内容
        --2.初始化数组后（int [] arr=new Int[]{1,2,3}）,在内存中所占空间才会被固定-数组长度不可变
        --3.此时，数组元素若被清空，空间依然存在且长度不变
        --4.分类：
        	--1.静态初始化：int [] a={1,2,3}    --自己初始化值，系统分配长度
        	--2.动态初始化：int [] b=new int [5]--自己指定长度，系统分配初始化值
        --------------------------------
        二维数组：
        --1.实际为一维数组，其各个元素又为一维数组，故称二维数组（数组的元素又为数组--多维数组）

    介绍：
        1.引用数据类型,是一块连续的内存空间
        2.初始化数组后（int [] arr=new Int[]{1,2,3}）,在内存中所占空间才会被固定-数组长度不可变
        3.数组创建后，不能改变
        4.数组只能存储一种类型的数据 
        5.增加、删除元素效率慢 
        6.按照索引查询元素速度快,可存储大量数据
        7.java数组用int做引索,理论值是Integer.MAX_VALUE个，但实际是依赖jvm内存

 #### 2.1 ArrayList
    
    1.ArrayList底层的数据结构为数组，元素类型为Object ,Arraylist可存放null元素
    2.有序，可重复，线程不安全
    3.ArrayList插入有移位操作，故插入，移除效率低
    4.可以按索引读取， 所以读取效率高
    5.jdk1.6 new对象时，初始容量=10 copyof扩容为原来的1.5倍+1,即16
      jdk1.7 new对象时，初始容量=0 ,真正添加时分配容量,copyof扩容为原来的1.5倍
    6.ArrayList遍历时移除元素的问题及陷阱：
       * 正确方式：
       *  1.倒叙fori
       *  2.迭代器遍历-迭代器-remove
       * 错误方式：-ConcurrentModificationException（fail-fast机制-多线程下的问题）
       *  1.正序fori
       *  2.增强for(迭代器遍历-list-remove)
    7.线程安全的ArrayList的替代：java.util.concurrent -->
       * List<String> list = new CopyOnWriteArrayList<String>();
    //================================================================================
     * 以下分析均基于JDK1.8
     * 1.ArrayList底层的数据结构为数组，元素类型为Object ,Arraylist可存放null元素
     * ------------------
     * ·ArrayList的继承关系：
     *      public class ArrayList<E> extends AbstractList<E>
     *      implements List<E>, RandomAccess, Cloneable, java.io.Serializable
     *      ArrayList继承AbstractList抽象父类，
     *      实现了List接口（规定了List的操作规范）、
     *      RandomAccess（可随机访问）、Cloneable（可拷贝）、
     *      Serializable（可序列化）。
     * ·基本属性：
     *       // 版本号
             private static final long serialVersionUID = 8683452581122892189L;
             // 缺省容量
             private static final int DEFAULT_CAPACITY = 10;
             // 空对象数组
             private static final Object[] EMPTY_ELEMENTDATA = {};
             // 缺省空对象数组
             private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
             // 元素数组==核心内容存放实际元素，标记为transient，在序列化时，此字段忽略
             transient Object[] elementData;
             // 实际元素大小，默认为0
             private int size;
             // 最大数组容量
             private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
     * ·构造函数：三种
        · new ArrayList<>();
        · new ArrayList<>(size);
        · new ArrayList<>(list);
     * ·综合以上：
     *      1.ArrayList插入有移位操作，故插入效率低
     *      2.可以按索引读取， 所以读取效率高
     *      3.底层数组-插入有序-可重复-可插入null
     *      4.jdk1.6 new对象时，初始容量=10 copyof扩容为原来的1.5倍+1,即16
     *        jdk1.7 new对象时，初始容量=0 ,真正添加时分配容量,copyof扩容为原来的1.5倍
    8.问题：查看代码
        1.利fori遍历删除元素，由于指针再删除第一个元素后会进行前移，所以第二个“b”的字符串没有被遍历到，故没被删掉。
            优化方案:倒叙删除遍历--不会涉及到元素移动问题
        2.利fori遍历删除元素，java.util.ConcurrentModificationException
            增强for循环的原理使用的是迭代器：即是对实际的Iterable、hasNext、next方法的简写
            这里会做迭代器内部修改次数检查，因为上面的remove(Object)方法修改了modCount的值，所以才会报出并发修改异常。
            要避免这种情况的出现则在使用迭代器迭代时（显示或for-each的隐式）不要使用ArrayList的remove，改为用Iterator的remove即可。
    
#### 2.2 LinkList
    
    1.LinkedList底层使用的双向链表结构,可从头后从尾开始遍历
    2.LinkList插入或删除只需要改变前后节点指向即可，所以效率快
    3.由于LinkedList查询只能从头结点开始逐步查询的，可以使用 iterator 的方式，
      就不用每次都从头结点开始访问，
      因为它会缓存当前结点的前后结点。实测查询效率与ArrayList没有太大差别
    4.LinkList与ArrayList的效率比较：
        1. LinkedList add cost time :35
           LinkedList get fori--> cost time :2886
           LinkedList get foreach--> cost time :31
           LinkedList get iterator--> cost time :6
           LinkedList remove cost time :7
           --------------------------------------------------------
           ArrayList add cost time :294
           ArrayList get fori--> cost time :1
           ArrayList get foreach--> cost time :5
           ArrayList get iterator--> cost time :1
           ArrayList remove cost time :530
        2.综上：
           1.ArrayList读取数据效率：fori=iterator > foreach
           2.LinkList读取数据效率：iterator>foreach>fori(切不可使用fori读取LinkList)
           3.插入删除效率比较：LinkList>ArrayList
           4.综合选用：
            ·读：ArrayList
            ·写：LinkList
    
#### 2.3 HashMap
    
    1.HashMap的key和value可为null,线程不安全
    2.HashMap底层实现数组+链表即Entry<K,V>[],
      jdk1.8后采用红黑树优化，当链表长度>8时，转换为红黑树
    3.默认初始容量:16 装载因子：0.75f 当真实数据达到0.75*16=12时，扩容为原来两倍2*16
    4.HashMap的容量必须为2的指数倍，最大容量为2^30,Entry是一个单向链表
    5.相同的HashCode，相同的key，在put时，新值覆盖旧值，并返回旧值,不会存在链表中
    6.相同的hashcode，不同的key，Hash冲突,Hash冲突后，那么HashMap的单个bucket
      里存储的不是一个 Entry，而是一个 Entry链
    7.线程安全的替代物：
        ·Map map = Collections.synchronizedMap(new HashMap());
        ·new ConcurrentHashMap<String, String>();
        ·HashTable的性能要比以上两种差，以上两种用了分段锁
    8.加载因子越大,填满的元素越多，空间利用率提高,hash冲突概率增大，查询效率降低（时-空抉择）
    9.扩容是需要进行数组复制的，复制数组是非常消耗性能的操作,那么预设元素的个数能够有效的提高HashMap的性能。
    10.在HashMap数组扩容之后,最消耗性能的点：原数组中的数据必须重新计算其在新数组中的位置，
      并放进去，这就是resize
    11.hash表的容量一定为2的整数次幂的原因：
        ·length为2的整数次幂时，h&(length-1)就相当于对length取模，这样便保证了散列的均匀，
         同时也提升了效率改用h&(length-1)而不是h%length是为了提高效率（相比于hashTable的优化）
        ·length为2的整数次幂时，length-1为奇数，奇数的二进制最后一位为1，那么&运算后的结果可能为0或1
         若length-1偶数，则最后一位为0，那么&运算后的结果只能为0，即数组会浪费一半的空间
