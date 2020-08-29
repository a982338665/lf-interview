
## 2 volatile是什么？
    
    1.vloatile是java虚拟机提供的轻量级的同步机制。
        ·保证可见性：一个线程在工作内存中修改完后，写回主内存，并且及时通知给其他线程，【主内存值一改变，就及时通知给其他线程】
        ·不保证原子性：
        ·禁止指令重排

## 3 JMM内存模型之可见性

    2.JMM（java内存模型-java Memory Model）
        ·是个抽象概念-实际并不存在
        ·描述的是一种规范规则，通过规范定义了程序中的各个变量(包含实例字段，静态字段，构成数组对象的元素)的访问方式
        ·jmm关于同步的规定：
            1.线程解锁前，必须把共享变量的值刷新回主内存
            2.线程加锁前，必须读取主内存到最新的自己的工作内存
            3.加锁，解锁是同一把锁
        ·由于jvm运行程序的实体是线程，而每个线程创建时，jvm都会为其创建一个工作内存（有些地方称为栈空间），工作内存是每个线程的私有数据区域，
            而java内存模型中规定所有变量都存储在主内存，主内存是共享内存区域，所有线程都可以访问，但线程对变量的操作（读取赋值）都必须在自己的
            工作内存中进行，首先要将变量从主内存中拷贝到自己的工作内存中，然后对变量进行操作，操作完成后再将变量写回主内存，不能直接操作主内存中变量
            ，各个线程的工作内存中存储着主内存的变量副本拷贝，因此不同的线程间无法访问对方的工作内存，线程之间的通信必须是通过主内存来完成。
        ·cpu查看工具：www.cpuid.com
    3.jMM:
        可见性
        原子性
        有序性    
        
## 4 可见性的代码验证：同级目录下 code/A4Test_volatile
    
    1.内存可见性：线程AAA修改了共享变量X的值但是还未写会主内存时，另外一个线程BBB又对主内存中同一个共享变量X进行操作，但是此时AAA线程工作内存中共享变量X对线程B来说并不可见，
            所以出现了不可见的问题，而volatile则保证了内存可见性

## 5 volatile不保证原子性？code/A5Test_volatile
    
    1.原子性：不可分割，完整性，即某个线程在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整。
    2.解释：i++操作，多个线程同时操作变量i，进行++，此时的值小于等于单线程下的i++，所以不保证原子性
    
## 6 volatile不保证原子性的理论解释
    
    1.测试案例中，若对变量加了volatile，且进行i++时加了synchronized，则能保证原子性
    2.数据演示：
        Mydata5.java => Mydata5.class => JVM字节码
        Mydata5中的
        num++;
            n++被拆分成3个指令：
                ·执行getfield拿到原始n
                ·执行iadd进行加1操作
                ·执行putfield把累加后的值写回主内存
        写覆盖-造成值不对

## 7 不保证原子性的问题解决

    1.加synchronized保证原子性：不建议
    2.使用AtomicInteger保证原子性，AtomicInteger num = new AtomicInteger();默认值为0，底层主要是 CAS和自旋锁，之后题目会提到
    
## 8 volatile指令重排案例1 - 汇编编译原理
    
    1.概念：计算机在执行程序时，为了提高性能，编译器和处理器常常会对指令做重排，一般分为以下三种：
        源代码 -> [编译器优化重排]->[指令并行的重排]->[内存系统的重排] -> 最终执行的命令
    2.结论：
        ·单线程环境里确保程序最终执行结果和代码顺序执行结果一致
        ·处理器在进行重排序时必须要考虑指令直接的数据依赖性
        ·多线程环境下线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测
    3.示例1：
        public void sort(){
            int x = 11;//语句1
            int y = 20;//语句2
            x = x + 5;//语句3
            y = x * x;//语句4
        }
        单线程下正常执行
        多线程下可能会被重排，重排后的可能性有：1234,2134，1324
        问题：语句可以重排后到第一条吗？不可以，处理器在进行重排序时必须要考虑指令直接的数据依赖性

## 9 volatile指令重排案例2
    
    1.多线程环境下线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测
    
## 10 单例模式在多线程下可能存在安全问题
    
    1.哪些地方用到过：单例模式、手写一个缓存的时候，读写锁会用到，JUC里面大规模的使用了volatile
    2.懒汉式单例问题：单线程下没问题，多线程下需要注意线程安全问题
    
## 11 单例模式的volatile分析
    
    1.DCL：（Double Check Lock 双检锁机制）:【加锁前后各判一次空】
    2.经过以上调整可能成功率达到99.99%，但是仍有机会出问题，因为指令重排的问题，所以还需要【加volatile】:
    3.解释：看代码
    
## 12 CAS是什么？
    
    1.原子整型类AtomicInteger底层实现：CAS解决多线程下i++问题
    2.定义：比较并交换 是方法 compareAndSet的缩写
        如果线程的期望值==物理内存的真实值，则修改为更新值
    3.CAS：全称Compare-And-Swap，他是一条cpu并发原语，即线程安全，直接操作最底层，不会有缓冲影响，不会造成数据不一致
        功能：判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的
        体现：在java语言中就是sun.misc.Unsafe类中的各个方法，调用unsafe类中的cas方法，jvm会帮我们实现出cas汇编指令。这是一种完全依赖于硬件的功能，通过它实现原子操作
        强调：cas是一种系统原语，属于操作系统用语范畴，是由若干条指令组成的用于完成某个功能的过程，并且原语的执行必须是连续的，执行过程中不允许被中断，也就是说cas是一条
            CPU的原子指令，不会出现数据不一致的情况
    
## 13 CAS底层原理上？
    
    1.底层原理：
        ·自旋锁
        ·UnSafe类:jre/lib/rt.jar下
            是cas核心类，由于java无法直接访问底层系统，需要通过本地方法（native）方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。
            unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因为java中cas操作的执行依赖于Unsafe类的方法
            注意：Unsafe类中，所有的方法都是native修饰的，也就是说，unsafe类中的方法都直接调用操作系统底层资源执行相应任务            
    2.源码释义:看代码      
    
## 14 CAS底层原理下？

    ·源码：
        AtomicInteger：
            public final int getAndIncrement() {
                    return unsafe.getAndAddInt(this, valueOffset, 1);
            }
        Unsafe：
            /**
             * var1:    AtomicInteger对象本身
             * var2:    该对象值的引用地址
             * var4:    需要变动的数量，即+1
             * var5:    通过var1，var2在主内存中找到的真实值
             * this.compareAndSwapInt(var1, var2, var5, var5 + var4):
             *    如果根据var1，var2得到的值==var5，则将var5+var4得到的结果更新到主内存中       
             *    如果根据var1，var2得到的值!=var5，则重新循环继续取值比较更新       
             */
            public final int getAndAddInt(Object var1, long var2, int var4) {
                int var5;
                do {
                    var5 = this.getIntVolatile(var1, var2);
                } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
        
                return var5;
            }
    ·原理：通过不断的比较去更新值，既保证了一致性，又提高了并发
    ·cas释义：
        比较当前工作中内存的值和主内存中的值，如果相同，则执行规定操作，否则一直比较直到相同为止
    ·cas应用：
        cas有三个操作数，内存值A，旧值V，预期的新值B
        当A=V时，将内存值改为B，否则什么都不做，继续自旋比较
        
## 15 CAS缺点？
    
    1.循环时间长，会给cpu带来很大开销（自旋）
    2.只能保证一个共享变量的原子操作
    3.引出的ABA的问题（最重要的问题）
    
## 16 ABA问题
    
    1.面试：CAS  -  unsafe  -  cas底层思想  -  ABA  -  原子更新引用  -  如何规避ABA问题
    2.ABA问题：狸猫换太子   
        CAS算法实现的一个重要前提,需要提取内存中某时刻的数据，并在当下时刻比较并替换，那么在这个时间差类会导致数据的变化
        例如：一个线程one从内存位置V中取出值A，这时另一个线程two也从内存中取出值A，并且线程two进行了一些操作将A换成B，然后线程two又将
            V位置的数据变成A，这时候线程one进行CAS操作发现内存中仍是A，然后线程one操作成功！
            尽管线程one的cas操作成功，但是并不代表这没有问题
            只管开头和结尾，不管中间，若不在乎中间的过程，则不算问题
            
## 17 AtomicReference原子引用
    
    1.AtomicReference原子引用类的使用

## 18 AtomicStampedReference版本号原子引用

    1.解决ABA问题：CAS+原子引用+新增一种机制，就是修改版本号（类似于时间戳），- 类似于乐观锁

## 19 ABA问题解决
    
    1.使用AtomicStampedReference版本号原子引用

## 20 集合类不安全之并发修改异常
    
    0.ArrayList
        //1.初始化时默认没有指定大小,new ArrayList<>(initialCapacity),可以在初始化的时候指定
        //2.add时会初始化大小为10
    1.Vector
        //1.new对象的时候初始化大小为10
        public Vector() {this(10);}
        //2.add的时候加锁方法上
        public synchronized boolean add(E e) {}
    2.Collections.synchronizedList(new ArrayList<>());
        //1.初始化大小也在add内部
        //2.他的锁是加在 内部类中的属性：：final Object mutex;
        @Override
        public boolean add(T e) {
            synchronized(mutex) {
                return backingList.add(e);
            }
        }
        //3.读操作，加锁性能差点
        public E get(int index) {synchronized (mutex) {return list.get(index);}}
    
## 21 集合类不安全之写时复制
    
    3.new CopyOnWriteArrayList<>();
        public boolean add(E e) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                //1.拿到当前数组
                Object[] elements = getArray();
                int len = elements.length;
                //2.复制并扩容加1个长度，得到新数组
                Object[] newElements = Arrays.copyOf(elements, len + 1);
                //3.将要添加的数据放到扩容的那个长度里
                newElements[len] = e;
                //4.更新当前数组为最新数组
                setArray(newElements);
                //5.返回成功
                return true;
            } finally {
                lock.unlock();
            }
        }
    4.写时复制：读写分离的思想
    5.分析比较：
        Vector：锁加在方法上 add ，get
        CopyOnWriteArrayList：锁加在代码块里面的对象上，发生修改时候做copy，新老版本分离，保证读的高性能，适用于以读为主，读操作远远大于写操作的场景中使用，比如缓存。每次写都要复制新集合，所以写慢，无锁，所以读快
        Collections.synchronizedList：可以用在CopyOnWriteArrayList不适用，但是有需要同步列表的地方，读写操作都比较均匀的地方。
    6.Java的synchronized加在方法上或者对象bai上区别如下
      1.synchronized 在方法上，所有这个类的加了 synchronized 的方zhi法，在执行时，dao会获得一个该类的唯一的同步锁，当这个锁被占用时，其他的加了 synchronized 的方法就必须等待
      2.加在对象上的话，就是以这个对象为锁，其他也以这个对象为锁的代码段，在这个锁被占用时，就必须等待
    
## 22 集合类不安全之Set
    
    1.HashSet
    2.Collections.synchronizedSet
    3.CopyOnWriteArraySet -- 底层实际还是 CopyOnWriteArrayList
    4.HashSet底层是什么？就是HashMap，有参数构造时初始值16，负载因子为0.75的标准HashMap
    5.既然HashSet底层是HashMap,而HashMap存储的是键值对，可是HashSet的add方法仅能写入一个值？这是为什么？
        源码分析：
            1.无参构造：public HashSet() {map = new HashMap<>();}
            2.有参构造：public HashSet(Collection<? extends E> c) { map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));addAll(c);}
            3.add方法： public boolean add(E e) {return map.put(e, PRESENT)==null;}
                // Dummy value to associate with an Object in the backing Map 支持映射中的对象关联的伪值
                private static final Object PRESENT = new Object();常量伪值
            
## 23 集合类不安全之Map
    
    1.HashMap
    2.Collections.synchronizedMap
    3.ConcurrentHashMap//分段锁...需要细化
    
## 24 TransferValue醒脑小练习 - 值传递/引用传递
## 25 java锁之公平锁和非公平锁

    1.公平锁：是指多个线程按照申请锁的顺序来获取锁。先来的先处理，按队列走
    2.非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。
              有可能，会造成优先级反转或者饥饿现象。
    3.public ReentrantLock(boolean fair) {sync = fair ? new FairSync() : new NonfairSync();}
    4.吞吐量：非公平锁>公平锁，公平锁仍多维护一个类似于队列的东西保证顺序
    5.synchronized也是一种非公平锁

## 26 java锁之可重入锁即递归锁理论知识

    1.线程可以进入任何一个他已经拥有锁所同步者的代码块
    2.锁中套锁
    3.synchronized void setA() throws Exception{    Thread.sleep(1000);    setB();}
      synchronized void setB() throws Exception{    Thread.sleep(1000);}
    4.防止死锁
    5.synchronized、ReentrantLock都是可重入锁

## 27 java锁之可重入锁即递归锁代码验证
## 28 java锁之自旋锁理论知识

    1.在Java中，自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。
          典型的自旋锁实现的例子，可以参考自旋锁的实现。
    2.提现：CAS源码里
    
## 29 java锁之自旋锁代码验证

    1.
    
## 30 java锁之读写锁理论知识    
## 31 java锁之读写锁代码验证    
## 32 CountDownLatch    
## 33 CyclicBarrierDemo    
## 34 SemaphoreDemo    
## 35 阻塞队列理论    
## 36 阻塞队列接口结构和实现类    
## 37 阻塞队列API之抛出异常组    
## 38 阻塞队列API之返回布尔值组    
## 39 阻塞队列API之阻塞和超时控制    
## 40 阻塞队列之同步SynchronousQueue队列    
## 41 线程通信之生产者，消费者传统版
## 42 synchronized与lock的区别
## 43 锁绑定多个条件Condition
## 44 线程通信之生产者，消费者阻塞队列版
## 45 Callable接口
## 46 线程池使用及优势
## 47 线程池三个常用方式
## 48 线程池七大参数入门简介
## 49 线程池七大参数深入介绍
## 50 线程池底层工作原理
## 51 线程池的四种拒绝策略理论简介
## 52 线程池实际中使用哪个
## 53 线程池的手写改造和拒绝策略
## 54 线程池配置合理线程数
## 55 死锁编码及定位分析
## 56 JVM-GC下半场技术加强说明和前提知识要求
## 57 JVM-GC快速回顾复习串讲
## 58 谈谈你对GCRoots的理解
## 59 JVM的标配参数和X参数
## 60 JVM的XX参数及布尔类型
## 61 JVM的XX参数及设值类型
## 62 JVM的XX参数及Xms，Xmx坑题
## 63 JVM盘点家底查看初始默认值
## 64 JVM盘点家底查看修改变更值
## 65 堆内存初始大小快速复习 


    
    
