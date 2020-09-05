
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
    2.体现：CAS源码里
    
## 29 java锁之自旋锁代码验证

    1.通过CAS操作完成自旋锁，A线程先进来调用mylock方法自己持有锁5秒钟，B随后进来发现当前线程持有锁，不是null
       * 所以只能通过自旋等待，直到A释放锁后B再抢到
    
## 30 java锁之读写锁理论知识    
    
    1.独占锁 写锁
    2.共享锁 读锁
    3.理论：
         独享锁是指该锁一次只能被一个线程所持有。对于Java ReentrantLock和Synchronized而言，其是独享锁。
         共享锁是指该锁可被多个线程所持有。对于Lock的另一个实现类ReentrantReadWriteLock，其读锁是共享锁，其写锁是独享锁。
    4.读锁的共享锁可保证并发读是非常高效的，读写，写读 ，写写的过程是互斥的。
    
## 31 java锁之读写锁代码验证    
    
    多个线程同时读一个资源类，满足并发，读取资源可以同时进行
    若有一个线程要写资源，则其他线程不能读
    总结
        写写 互斥
        读写 互斥
        读读 共享
    
## 32 CountDownLatch    

    1.倒计时,计数减为0，继续往下走
    2.将条件判断改为枚举判断

## 33 CyclicBarrierDemo    
    
    1.跟CountDownLatch相反，做加法，集合开会的意思
    
## 34 SemaphoreDemo    
    
    1.信号量主要用于两个目的：
        ·一个是用于多个共享资源的互斥使用
        ·另一个用于并发线程数的控制
    2.dubbo-rpc-nio-netty
    
## 35 阻塞队列理论    
    
    1.ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按FIFO-先进先出原则对元素进行排序
    2.LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO-先进先出原则对元素进行排序,吞吐量通常高于ArrayBlockingQueue
    3.SynchronousQueue：一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量一般高于LinkedBlockingQueue
    4.队列：排队-先到先得
    5.阻塞队列：
        ·阻塞队列的好处？
        ·必须阻塞，怎么管理阻塞？
    
## 36 阻塞队列接口结构和实现类    

    1.生产者-消费者
    2.线程一往阻塞队列中添加元素，线程二从阻塞队列中移除元素
        ·阻塞队列为空，取出操作会被阻塞
        ·阻塞队列已满，添加操作会被阻塞
    3.阻塞：在某些情况下会挂起线程，一旦条件满足，挂起的线程又会被唤醒
        ·为什么需要BlockingQueue？
            不需要关心什么时候该阻塞，什么时候该唤醒，内部已经实现，不需要程序员自己去控制数据安全及性能
    4.除了ArrayList，LinkedList还用过哪些？还有 CopyOnWriteArrayList，BlockingQueue
    5.Iterable：
        Collection：
            ·List
            ·Queue接口
                ·BlockingQueue 阻塞队列接口
                    1.LinkedTransferQueue   由链表结构组成的无界阻塞队列
                    2.LinkedBlockingDeque   由链表结构组成的双向阻塞队列-双端队列
                    3.PriorityBlockingQueue 支持优先级排序的无界阻塞队列
                    5.DelayQueue            使用优先级队列实现的延迟无界限阻塞队列
                    -- 主要讲以下三个
                    6.ArrayBlockingQueue    由数组构成的有界阻塞队列
                    4.SynchronousQueue      不存储元素的阻塞队列，也即单个元素的队列，有且只有一个，生产一个，消费一个
                    7.LinkedBlockingQueue   由链表构成的有界阻塞队列（单大小默认值为Integer.MAX_VALUE，21亿多，所以一定谨慎使用）
    6.BlockingQueue核心方法：查看图片
    
## 37 阻塞队列API之抛出异常组    
    
    1.异常组：add(e)  remove()  element()-检查，无报错
    
## 38 阻塞队列API之返回布尔值组    
    
    1.返回布尔值组：offer(e)  remove(e)/poll() peek()-检查，无返回null
    
## 39 阻塞队列API之阻塞和超时控制    

    1.阻塞：put(e)  take() 
    2.超时： offer(e,time,unit) poll(time,unit)
        
## 40 阻塞队列之同步SynchronousQueue队列 
    
    1.0库存队列，生产一个消费一个
       
## 41 线程通信之生产者，消费者传统版

    1.多线程三句口诀：
        1.线程 操作（方法） 资源类
        2.判断 干活 通知
        3.防止虚假唤醒机制-多线程判断用while 可使用if尝试错误情况
    2.Object类的wait和notify方法：查看api知解释
        wait的api解释中：
        As in the one argument version ，interrupts and spurious wakeups are possible，and this method should always be used in a loop
        //在单参数版本中，中断和虚假唤醒是可能的，并且这个方法应该始终在循环中使用
        synchronized (obj){
            whild(condition does not hold){
                obj.wait();
            }
        }
    
## 42 synchronized与lock的区别
    
    1.原始构成
        synchronized 
                1.属于jvm层面是java关键字
                2.底层：
                    monitorenter 底层是通过monitor对象来完成，其实wait和notify方法也依赖于monitor对象，只有在同步块或方法中才能掉wait/notify等方法
                    monitorexit
        lock 
            1.属于api层面是java5以后新发行的类java.util.concurrent.locks.lock
    2.使用方法
        synchronized:不需要用户手动去释放锁，当synchronized代码执行完，系统会自动让线程释放对锁的占用
        ReentrantLock:则需要用户手动释放锁，若没有释放，可能会产生死锁现象
    3.等待是否可中断
        synchronized:不可中断，除非抛出异常或者正常运行完成
        ReentrantLock:可中断，
            ·设置超时方法 trylock(long timeout,TimeUnit unit)
            ·lockInterruptibly()放在代码块中，调用interrupt方法可中断
    4.加锁是否公平
        synchronized为非公平锁
        ReentrantLock:都可以，构造方法传入boolean值 true为公平，false为非公平,默认非公平锁
    5.锁绑定多个条件Condition
        synchronized没有
        ReentrantLock：用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程，要么唤醒全部线程
        
## 43 锁绑定多个条件Condition - 精确唤醒，顺序调用
    
    见代码
    
## 44 线程通信之生产者，消费者阻塞队列版

    1.学习路径：volatile - cas - atomicInteger - atomicReference - BlockingQueue - 线程交互
    
## 45 Callable接口

    1.线程创建的方式：工作中常用的只有3和4
        ·集成Thread类
        ·实现Runnable接口
            - 没有返回值
        ·实现Callable<T>：接口第三种获得多线程的方式很常用（100个线程有两个错误了，需要他的返回值）
            - 有返回值
        ·线程池
    2.FutureTask
    
## 46 线程池使用及优势

    1.为什么用线程池？优势是什么？
        线程池的工作主要是控制运行的线程的数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务，如果线程数量超过了最大数量，超出数量的线程排队等候
        等其他线程执行完毕，再从队列中取出任务来执行
        假设4核八线程，每个线程占一个cpu，省略了上下文开销
        主要特点：线程复用，控制最大并发数，管理线程
        优势：
            1.降低资源消耗，重复利用已创建的线程，降低线程创建和销毁造成的消耗
            2.提高响应速度，当任务到达时，任务可以不需要等到线程创建就能执行
            3.提高线程的可管理性，线程时稀缺资源，如果无节制的创建，不仅会消耗系统资源，还会降低系统稳定性，使用线程池可以统一管理，分配，调优和监控
    2.线程池如何使用？
    3.几个重要参数介绍？
    4.线程池的底层工作原理？
    
## 47 线程池三个常用方式 - 2.线程池如何使用？
    1.java中线程池是通过Executor框架实现的，该框架中用到了Executor，Executors，ExecutorService，ThreadPoolExecutor等类
    2.重要：
        Executors
        ThreadPoolExecutor
    3.编码实现：
        ·了解
            Executors.newScheduledThreadPool() 带调度的，线程池中设置参数，每间隔几秒钟执行一次
            java8新出 - Executors.newWorkStealingPool(int) 使用目前机器上可用的处理器作为他的并行级别
        ·重点
            ·Executors.newFixedThreadPool(int)      一池固定线程
                源码：return new ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
                常用：执行长期任务，性能好
                特点：
                    ·创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
                    ·创建的线程池corePoolSize,maximumPoolSize是相等的，使用的是LinkedBlockingQueue阻塞队列
            ·Executors.newSingleThreadExecutor()    一池一线程
                源码：return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()));
                常用：一个任务一个任务执行
                特点：
                    ·单线程化线程池，保证所有任务按顺序执行
                    ·创建的线程池corePoolSize,maximumPoolSize都等于1，使用的是LinkedBlockingQueue阻塞队列
            ·Executors.newCachedThreadPool()        一池多线程
                源码：return new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
                常用：执行很多短期异步小程序或者负载较轻的服务器
                特点：
                    ·缓存线程池，可灵活回收线程，不够则新建
                    ·maximumPoolSize都等于Integer最大值
                    ·SynchronousQueue来个任务就创建线程运行，超过60s空闲，销毁现场
        ·以上三个源码中都是：ThreadPoolExecutor
            new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>())
    
## 48 线程池七大参数入门简介，阿里，美团，百度
    
    1.为什么程序对外接口只开放五个参数
    2.源码跟踪：
        public ThreadPoolExecutor(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler)
    
## 49 线程池七大参数深入介绍

    1.int corePoolSize,                     线程池中的常驻核心线程数，{都线程被占满了就将任务放到队列缓存，当队列缓存都被占满了，就需要添加线程到最大值maximumPoolSize加班}
        当调用一个execute方法添加一个请求任务时，线程池会做如下判断：
            1.当前运行线程数 < corePoolSize  新创建线程执行任务
            2.当前运行线程数 >= corePoolSize 将任务放入队列
            3.BlockingQueue满了并且 当前运行线程数 < maximumPoolSize 创建非核心线程执行这个任务，注意：多出来的任务会直接使用此线程，而不是排队
            4.BlockingQueue满了并且 当前运行线程数 >= maximumPoolSize 线程池会启动饱和拒绝策略来执行
    2.int maximumPoolSize,                  线程池中能够容纳同时执行的最大线程数，此值必须大于等于1
    3.long keepAliveTime,                   多余的空闲线程的存活时间：当线程池数量超过corePoolSize时，空闲时间达到keepAliveTime，多余空闲线程会被销毁直到只剩下corePoolSize个线程为止
    4.TimeUnit unit,                        keepAliveTime的单位
    5.BlockingQueue<Runnable> workQueue,    任务队列，被提交但是尚未被执行的任务
    6.ThreadFactory threadFactory,          表示生成线程池中工作线程的线程工厂，用于创建线程。(一般用默认的即可)
    7.RejectedExecutionHandler handler      拒绝策略，表示当队列满了并且工作线程大于等于线程池的最大线程数maximumPoolSize

## 50 线程池底层工作原理

    看图片A50
    
## 51 线程池的四种拒绝策略理论简介
    
    1.生产上的线程池配置？
    5.线程池的四种拒绝策略？
        1.是什么？
            ·等待队列排满，塞不下新任务 同时 线程池中的max也达到了最大值，无法为新任务服务，此时就配合拒绝策略来处理问题           
        2.jdk内置的拒绝策略
            ·AbortPolicy（默认）：直接抛出RejectedExecutionException
            ·CallerRunsPolicy：“调用者运行”一种调节机制，不会抛弃任务，也不会抛弃异常，而是将某些任务回退给调用者，从而降低新任务的流量
            ·DiscardOldestPolicy：抛弃队列中等待最久的任务，然后吧当前任务加入队列中，尝试再次提交当前任务
            ·DiscardPolicy：直接丢弃任务，不处理，也不抛异常，若允许任务丢失，此方案最合适
        3.以上内置拒绝策略均实现了RejectedExecutionHandler接口
    
## 52 线程池实际中使用哪个

    1.工作中单一，固定数，可变线程池你主要用哪个？
        一个都不用，使用自定义的。
        阿里巴巴开发手册1.4：并发处理章节中指出原因：
            线程池不允许使用Executors创建，而是通过ThreadPoolExecutor的方式，这样的处理才能让人更加明白线程池的运行规则，避免资源耗尽的风险
            Executors创建线程的弊端：
                1.FixThreadPool 和 SingleThreadPool
                    允许【请求队列】的长度为Integer.MAX_VALUE，可能会堆积大量请求从而导致OOM
                    LinkedBlockingQueue有界队列，但是最大值为Integer.MAX_VALUE (约21亿)
                2.CachedThreadPool 和 ScheduledThreadPool
                    运行的【创建线程】数量为Integer.MAX_VALUE，可能会创建大量线程从而导致OOM
    
## 53 线程池的手写改造和拒绝策略

    1.工作中如何使用线程池？是否有过自定义线程池使用？
        工作中很少用抛异常拒绝策略
    
## 54 线程池配置合理线程数

    0.查看cpu核数：System.err.println(Runtime.getRuntime().availableProcessors());
    1.如何配置线程池的最佳数量，合理配置线程池怎么考虑的？
        第一步：查看自己服务器信息核数
        第二步：从业务角度出发，分为两种
                CPU密集型：该任务需要大量运算，没有阻塞，cpu一直全速运行，【while死循环-cpu密集】
                    只有在真正的多核cpu上，才可能得到加速（通过多线程）  
                    在单核cpu上，无论怎么模拟多线程任务都不会加速，因为cpu能力是固定的
                    cpu密集型任务配置尽可能少的线程数量
                    一般公式：CPU核数+1个线程的线程池 （每核一个尽量减少切换）         
                IO密集型：两种【磁道取数据-mysql，redis等-io密集】
                    1.看书经验：
                        IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程，如cpu核数*2
                    2.实战生产经验：
                        任务需要大量io，即大量阻塞
                        在单线程上运行io密集型的任务会导致浪费大量cpu运算能力浪费在等待上
                        所以io密集任务中使用多线程能大大加速程序运行，即使在单核cpu上，这种加速就是利用被浪费掉的阻塞时间
                        io密集型时，大部分线程都阻塞，故需要多配置线程数
                        参考公式：cpu核数/(1-阻塞系数)
                        阻塞系数范围：0.8-0.9之间
                        例如八核cpu：8/(1-0.9)=80个线程                        

## 55 死锁编码及定位分析

    1.产生死锁的原因：自己持有A想获得B，自己获得B想持有A
        ·两个或两个以上的进程在执行过程中，因争抢资源而造成的一种互相等待的现象。 
        ·系统资源充足，能够满足各个进程，死锁出现概率就底，否则就可能因争夺有限资源陷入死锁
    2.代码 
    3.解决思路:
        用到的分析命令：
        jps查看进程号：jps -l
        查看堆栈信息：jstack 【上一步查询的进程号】
            "lockB":
                    at A20200814_bili第二季.code.HoldLock.run(A55_DeadLock.java:18)
                    - waiting to lock <0x0000000780ac5620> (a java.lang.String) 等5620
                    - locked <0x0000000780ac5658> (a java.lang.String)          锁5658
                    at java.lang.Thread.run(Thread.java:748)
            "lockA":
                    at A20200814_bili第二季.code.HoldLock.run(A55_DeadLock.java:18)
                    - waiting to lock <0x0000000780ac5658> (a java.lang.String) 等5658
                    - locked <0x0000000780ac5620> (a java.lang.String)          锁5620
                    at java.lang.Thread.run(Thread.java:748)
            Found 1 deadlock.
    
## 56 JVM-GC下半场技术加强说明和前提知识要求 以下都基于java8
    
    1.多线程
    2.jvm调优
    3.微服务tomcat调优。Springboot2.0以后将tomcat换成Undertow吞吐量增强
    
## 57 JVM-GC快速回顾复习串讲
    
    1.复习：
        ·jvm内存结构
            1.jvm体系概述：
                ·类加载器
                    双亲委派
                    沙箱安全机制
                ·运行时数据区
                    线程私有：java栈，本地方法栈，程序计数器
                    线程共享：方法区，堆
            2.java8以后的jvm
                ·永久代变为元数据区
        ·gc作用域：-> 线程共享部分
            方法区
            堆
        ·常见垃圾回收算法 - 分代收集
            引用计数        已弃用
            复制            新生代
            标记清除        老生代
            标记整理        老生代
    
## 58 谈谈你对GCRoots的理解
    
    1. 如何确定垃圾？什么是GCC Roots？
        ·什么是垃圾？
            内存中已经不再被使用到的空间就是垃圾
        ·进行垃圾回收，怎么判断他是不是垃圾？
            ·引用计数法：- 无法解决循环引用问题
                给对象添加一个引用计数器，一次引用加一，一个引用失效减一，计数值为0，则判定为可回收对象
            ·枚举根节点做可达性分析（根搜索路径）看图A58 -> 不可达的会被回收
                哪些作为GC ROOTs对象？
                    1.虚拟机栈（栈帧中的局部变量区，也叫着局部变量表）  方法中new对象
                    2.方法区中类静态属性引用的对象                      类的静态属性
                    3.方法区中常量引用的对象                            类的常量属性
                    4.本地方法栈中JIN（Native方法）引用的对象          
    
## 59 JVM的标配参数和X参数

    1.jvm参数调优和配置，怎么查看系统默认值？
        -Xms 初始堆空间
        -Xmx 堆空间最大值
        -Xss 初始栈空间
    2.初始值调到期望值
    3.jvm参数类型：
        1.标配参数：java8-12一直存在的参数
            java -version
            java -help
            java -showversion
        2.X参数-了解
            -Xint   解释执行
            -Xcomp  第一次使用就编译成本地代码
            -Xmixed 混合模式
        3.XX参数-重要
            ·boolean值
            ·KV设值
            ·jinfo举例，如何查看当前运行程序的配置
            ·题外话-坑题目
    4.命令测试：-了解
        C:\Users\Administrator>java -version
        java version "1.8.0_231"
        Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
        Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)    虚拟机HotSpot，mixed mode混合模式
        C:\Users\Administrator>java -Xint -version
        java version "1.8.0_231"
        Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
        Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)    虚拟机HotSpot，interpreted mode解释执行
        C:\Users\Administrator>java -Xcomp -version
        java version "1.8.0_231"
        Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
        Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)    虚拟机HotSpot，compiled mode编译执行
    
## 60 JVM的XX参数及布尔类型

    -XX:+或-某个属性
        +表示开启
        -表示关闭
    case：
        是否打印gc收集细节: 
            -XX:+PrintGCDetails
            -XX:-PrintGCDetails
        是否使用串行垃圾回收器:
            -XX:+UseSerialGC 
            -XX:-UseSerialGC 
    如何查看正在运行中的java程序，他的某个jvm参数是否开启，具体值是多少？  
        获取进程号：jps -l
        查看某进程是否添加PrintGCDetails参数：jinfo -flag PrintGCDetails 【进程号】
        查看某进程所有参数：jinfo -flags【进程号】
        示例：
            ===========================================================
            D:\go-20191030\interview>jps -l
            480
            7568 A20200814_bili第二季.code.A60_JVM
            5964 org.jetbrains.jps.cmdline.Launcher
            7516 sun.tools.jps.Jps
            ===========================================================
            D:\go-20191030\interview>jinfo -flag PrintGCDetails 7568//获取指定参数
            -XX:-PrintGCDetails //减号表示关闭，未开启
            ===========================================================
            D:\go-20191030\interview>jinfo -flags 7516  //获取所有参数信息
            Attaching to process ID 7516, please wait...
            Debugger attached successfully.
            Server compiler detected.
            JVM version is 25.231-b11
            //虚拟机默认，若有自己加的会将参数值覆盖
            Non-default VM flags: -XX:CICompilerCount=3 -XX:InitialHeapSize=201326592 -XX:MaxHeapSize=3191865344 -XX:MaxNewSize=1063
            780352 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=67108864 -XX:OldSize=134217728 -XX:+PrintGCDetails -XX:+UseCompressedCla
            ssPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:-UseLargePagesIndividualAllocation -XX:+UseParalle
            lGC
            //Command line只自己加的
            Command line:  -XX:+PrintGCDetails -javaagent:D:\install-soft\ideaIU-2019.2.1.win\lib\idea_rt.jar=52067:D:\install-soft\
            ideaIU-2019.2.1.win\bin -Dfile.encoding=UTF-8
    启动添加VM options参数：
        -XX:+PrintGCDetails 
          
## 61 JVM的XX参数及设值类型

    -XX:属性key=属性value
    case：
        -XX:MetaspaceSize=128m          元数据区大小
        -XX:MaxTenuringThreshold=15     新生代到老生代 活过15次才能升到老年代
    查看运行中程序的MetaspaceSize：
        jps -l
        jinfo -flag MetaspaceSize 7568
        示例：
            D:\go-20191030\interview>jinfo -flag MetaspaceSize 6924
            -XX:MetaspaceSize=21807104  //元空间初始值约为21M左右
    VM options设置值：
        -XX:MetaspaceSize=1024M
        
## 62 JVM的XX参数及Xms，Xmx坑题

    两个经典参数：-Xms -Xmx 不属于boolean，而是属于kv设值，是别名
        -Xms1024M 等价于 -XX:InitialHeapSize=1024M   初始化堆内存  默认为物理内存的1/64，jvm在安装时根据机器的物理大小进行的初始化
        -Xmx1024M 等价于 -XX:MaxHeapSize=1024M       最大堆内存    默认为物理内存的1/4，jvm在安装时根据机器的物理大小进行的初始化
    -Xss 为jvm启动的每个线程分配的内存大小，默认JDK1.4中是256K，JDK1.5+中是1M
    -Xmn2g：设置年轻代大小为2G。整个堆大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。
        此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。

## 63 JVM盘点家底查看初始默认值

    1.第一种：
        jps -l
        jinfo -flag 参数 进程号
        jinfo -flags 进程号
    2.第二种：
        -XX:+PrintFlagsInitial 查看安装后默认初始值 -特别重要：盘点jvm初始化参数
            java -XX:+PrintFlagsInitial -version
            java -XX:+PrintFlagsInitial
        -XX:+PrintFlagsFinal 查看修改后的默认初始值 -特别重要：盘点jvm初始化参数
            java -XX:+PrintFlagsFinal -version
            java -XX:+PrintFlagsFinal
                     bool MaxFDLimit                                = true                                {product}     //boolean类型
                    uintx MaxHeapFreeRatio                          = 100                                 {manageable}  //=表示kv设值类型
                    uintx MaxHeapSize                              := 3191865344                          {product}     //:=表示被修改过的值（人为或者jvm修改的）
        
## 64 JVM盘点家底查看修改变更值

    PrintFlagsFinal举例：运行java的时候同时打印出参数
            D:\go-20191030\interview\src\main\java\A20200814_bili第二季\code>more T.java
            D:\go-20191030\interview\src\main\java\A20200814_bili第二季\code>java -XX:+PrintFlagsFinal -XX:MetaspaceSize=512M T.java
    PrintCommandLineFlags：
            D:\go-20191030\interview\src\main\java\A20200814_bili第二季\code>java -XX:+PrintCommandLineFlags -version
                -XX:InitialHeapSize=199473984 -XX:MaxHeapSize=3191583744 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops 
                -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC//UseParallelGC默认的垃圾回收器，并行GC
                java version "1.8.0_231"
                Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
                Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
                                                         
## 65 堆内存初始大小快速复习 
    
    1.jvm常用的配置参数有哪些？
        -Xms 初始堆内存  默认为物理内存的1/64,等价于 -XX:initialHeapSize 
        -Xmx 最大堆内存  默认为物理内存的1/4,等价于 -XX:MaxHeapSize
        -Xmn 新生代
        看65,66，...
    
## 66 常用基础参数栈内存Xss 约有10个
      
    接65：【栈管运行-堆管存储】
        -Xss 设置单个线程栈空间大小，一般默认为512k-1M，线程私有，管运行，等价于 -XX:ThreadStackSize
            查看进程号 jsp -l
            查看运行程序的这个值：jinfo -flag ThreadStackSize 进程号
                运行后得到 -XX:ThreadStackSize=0 
                查看官网可知，当-XX:ThreadStackSize=0时就代表它要取默认值，即512k-1M
                Linux系统默认是1M=1024k
    
## 67 常用基础参数元空间MetaspaceSize
    
    接66:    -一般不用调
        -Xmn 设置年轻代大小，一般不用调，使用默认即可（新生代1/3,老年代2/3）
        -XX:MetaspaceSize 设置元空间大小
            元空间本质和永久代类似，都是对jvm规范中方法区的实现。不过其最大区别在于，【元空间并不在虚拟机中，而是使用本地内存】
            java8元空间取代了永久代
            因此默认情况下，元空间大小仅受本地内存限制
            出现的OOM异常：java.lang.OutOfMemoryError: Metaspace
                虽然仅受本地内存限制，但是还是会OOM，通过命令查看默认值并且修改
                他的默认值不会因为内存的关系而改变，所以一般在调优时，可以将其设置大一点
                jinfo -flag MetaspaceSize 进程号
                    -XX:MetaspaceSize=21807104 约等于20M
            修改命令：
                VM options添加：-Xms128m -Xmx4096m -Xss1024k -XX:MetaspaceSize=512m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC
                -Xms128m                        初始堆
                -Xmx4096m                       最大堆
                -Xss1024k                       线程栈
                -XX:MetaspaceSize=512m          元数据区
                -XX:+PrintCommandLineFlags      精简的命令行信息
                -XX:+PrintGCDetails             垃圾回收详情
                -XX:+UseSerialGC                串行垃圾回收器（默认是并行垃圾回收器：：-XX:+UseParallelGC）

## 68 常用基础参数PrintGCDetails回收前后对比
    
    接67：
        -XX:+PrintGCDetails 【重点】配图A68
         * VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails
         * 1.设置堆最大为 10m
         * 2.在main函数中申请50m内存的结果
         * 3.执行结果
         *      [GC (Allocation Failure) [PSYoungGen: 1550K->488K(2560K)] 1550K->668K(9728K), 0.0009466 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         *      //++++++++++++++++++++++++++++++++++++++++++++++
         *      [GC 表示在新生代
         *      (Allocation Failure) 内存申请失败
         *      [PSYoungGen: GC类型-新生代
         *      1550K：gc前新生代的内存占用
         *      ->488K：gc后新生代的内存占用
         *      (2560K)]：新生代总共大小
         *      1550K：gc前jvm堆内存占用
         *      ->668K:：gc后jvm堆内存使用
         *      (9728K)：jvm堆总大小
         *      0.0009466 secs]：gc耗时
         *      [Times: user=0.00：gc用户耗时
         *      sys=0.00,：gc系统耗时
         *      real=0.00 secs]：gc实际耗时
         *      //++++++++++++++++++++++++++++++++++++++++++++++
         *      [GC (Allocation Failure) [PSYoungGen: 488K->504K(2560K)] 668K->684K(9728K), 0.0004350 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         *      [Full GC (Allocation Failure) [PSYoungGen: 504K->0K(2560K)] [ParOldGen: 180K->609K(7168K)] 684K->609K(9728K), [Metaspace: 3126K->3126K(1056768K)], 0.0058359 secs] [Times: user=0.05 sys=0.00, real=0.01 secs]
         *      //++++++++++++++++++++++++++++++++++++++++++++++
         *      [Full GC 表示老生代，同理
         *      //++++++++++++++++++++++++++++++++++++++++++++++
         *      [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 609K->609K(9728K), 0.0003462 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         *      [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 609K->591K(7168K)] 609K->591K(9728K), [Metaspace: 3126K->3126K(1056768K)], 0.0056701 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
         *      Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
         *      	at A20200814_bili第二季.code.A68_JVMPrintGCDetails.main(A68_JVMPrintGCDetails.java:19)
         *      Heap
         *       PSYoungGen      total 2560K, used 104K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
         *        eden space 2048K, 5% used [0x00000000ffd00000,0x00000000ffd1a208,0x00000000fff00000)
         *        from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
         *        to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
         *       ParOldGen       total 7168K, used 591K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
         *        object space 7168K, 8% used [0x00000000ff600000,0x00000000ff693d38,0x00000000ffd00000)
         *       Metaspace       used 3210K, capacity 4496K, committed 4864K, reserved 1056768K
         *        class space    used 345K, capacity 388K, committed 512K, reserved 1048576K
        
    
## 69 常用基础参数SurvivorRatio
    
    -接68：
        -XX:SurvivorRatio   幸存区
            设置新生代中eden和s0/s1空间的比例
            默认：-XX:SurvivorRatio=8，Eden：S0:S1 = 8:1:1
            例如：-XX:SurvivorRatio=4，Eden：S0:S1 = 4:1:1
                即SurvivorRatio的值就是eden所占的比，S0/S1相同
            示例：在启动的时候可以加上看结果 -XX:+UseSerialGC 一定使用串行垃圾收集器
            * 1.不加幸存区比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC
             *     def new generation   total 3072K, used 1703K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
                 *   eden space 2752K,  61% used [0x00000000ff600000, 0x00000000ff7a9e18, 0x00000000ff8b0000)
                 *   from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
                 *   to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
                 *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
             *      默认 约为8:1:1
             * 2.添加加幸存区比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:SurvivorRatio=4
             *  def new generation   total 2880K, used 1694K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
             *   eden space 2368K,  71% used [0x00000000ff600000, 0x00000000ff7a7850, 0x00000000ff850000)
             *   from space 512K,   0% used [0x00000000ff850000, 0x00000000ff850000, 0x00000000ff8d0000)
             *   to   space 512K,   0% used [0x00000000ff8d0000, 0x00000000ff8d0000, 0x00000000ff950000)
             *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
             *      约为4:1:1
                          
## 70 常用基础参数NewRatio

    -接69：
        -XX:NewRatio 一般不会调整，面试用
            配置年轻代与老年代在堆结构中的占比
            默认：-XX:NewRatio=2 新生代占1，老年代占2，年轻代占整个堆的1/3
            假如：-XX:NewRatio=4 新生代占1，老年代占4，年轻代占整个堆的1/5
            比较：一定使用串行垃圾收集器 -XX:+UseSerialGC
                 * 1.不加比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC
                  *    def new generation   total 3072K, used 1703K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
                  *   eden space 2752K,  61% used [0x00000000ff600000, 0x00000000ff7a9e08, 0x00000000ff8b0000)
                  *   from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
                  *   to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
                  *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
                  *      约为 3072K/6848K 约等于1:2
                  * 2.添加比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=8
                             def new generation   total 1856K, used 1658K [0x00000000ff600000, 0x00000000ff800000, 0x00000000ff800000)
                             eden space 1664K,  99% used [0x00000000ff600000, 0x00000000ff79e940, 0x00000000ff7a0000)
                             from space 192K,   0% used [0x00000000ff7a0000, 0x00000000ff7a0000, 0x00000000ff7d0000)
                             to   space 192K,   0% used [0x00000000ff7d0000, 0x00000000ff7d0000, 0x00000000ff800000)
                             tenured generation   total 8192K, used 0K [0x00000000ff800000, 0x0000000100000000, 0x0000000100000000)
                  *      约为1856K/8192K 约等于1:4

## 71 常用基础参数Max Tenuring Threshold
    
    -接70：-> 在串行垃圾回收器下测试
        -XX:MaxTenuringThreshold = 设置垃圾最大年龄，从新生代到老生代最少要逃过15次垃圾回收
            默认：-XX:MaxTenuringThreshold=15
            假设：-XX:MaxTenuringThreshold=0，则新生区对象不会经过Survivor区，直接进入老年代，对于老年代比较多的应用，可以提高效率，若将此值设置为一个较大值，则年轻对象
                会在survior区进行多次复制，这样可以增加对象在年轻代的存活时间，增加年轻代即被回收的概论
            java8设置限制：必须在0-15，【在java8之前可设置该值为31，让对象尽量在新生代中被gc】
            
## 72 强引用

    1.四大引用：90%使用的强引用，软引用用于一些缓存。。，弱引用，虚引用很少用
    2.架构：
        Object：
            Reference 以前仅学到这里
                SoftReference       软
                WeakReference       弱
                PhantomReference    虚
            ReferenceQueue
    3.强引用：Object o = new Object()
        ·回收情况：当内存不足，jvm开始垃圾回收，对于强引用的对象，就算OOM也不会进行垃圾回收 
        ·强引用是我们最常见的普遍对象引用，只要还有强引用指向对象，就表示对象还活着，就不会被回收。
        ·java中最常见的就是强引用，把一个对象赋值给一个引用变量，这个引用变量就是一个强引用，当一个对象被强引用变量引用时，他处于可达状态，他是不可能被垃圾回收机制回收的
            即使该对象以后永远不会被jvm回收，因此强引用是造成jvm内存泄漏的主要原因之一
        ·对于一个普通对象，如果没有其他的引用关系，只要超过了引用的作用域或显示的将强引用赋值为null，一般认为就是可以被垃圾回收的，（具体回收时机还要看垃圾回收策略）  
    
## 73 软引用
    
    1.回收情况：内存足的情况下不回收，不足的情况下，回收
    2.实现：java.lang.ref.SoftRefenence类来实现，可以让对象豁免一些垃圾收集
    3.使用：通常用于对内存敏感的程序中，例如高速缓存就有用到软引用
    
## 74 弱引用

    1.回收情况：只要有gc，一定回收
    
## 75 软引用和弱引用的使用场景
    
    1.场景1：
        假设有一个应用需要读取大量本地图片：
            ·若每次读取图片都从硬盘中读取，则会严重影响性能
            ·若一次性全部加载到内存中，有可能造成内存溢出
        此时，使用软引用或弱引用可以解决这个问题
        解决：
            使用HashMap来保存图片路径和相应图片对象关联的软引用之间的映射关系，在内存不足时，jvm会自动回收这些缓存图片对象所占的空间，从而有效避免oom问题
            Map<String,SoftReference<Bitmap>> map = new HashMap<>();
            mybatis中就大量的使用到了软引用和弱引用
    
## 76 WeakHashMap案例演示

    1.了解了软，弱引用，有用过WeakHashMap吗？
    2.key置为null，下次回收就会回收掉
    
## 77 虚引用

    1.虚引用：java.lang.ref.PhantomReference类来实现，不会决定对象的生命周期
    2.如果一个对象仅持有虚引用，那他就和没有任何引用一样，任何时候都可能被垃圾回收，他不能单独使用，也不能通过他访问对象，虚引用必须和引用队列(RefenenceQueue)联合使用
    3.虚引用的主要作用是跟踪对象被垃圾回收的状态，仅仅提供了一种确保对象被finalize之后，做某些事情的机制
    4.PhantomReference的get()方法总是返回null，因此无法访问对应的引用对象，其意义在于说明一个对象已经进入了finalization阶段，可以被gc回收，用来实现比finalization更加灵活的回收操作
    5.设置虚引用关联的唯一目的，就是这个对象在被垃圾回收时，收到一个系统通知或者后续添加进一步处理
    6.java技术允许使用finalize()方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作
    
    7.跟队列联合使用，get()总为null，虚引用在被回收之前要被引用队列保存一下
    
## 78 ReferenceQueue引用队列

    gc之前为null，gc之后将引用放入队列中！适用于虚引用，弱引用，软引用
    
## 79 虚引用PhantomReference -面试用，工作基本用不到
    
    在被gc之前可以做点事，垃圾回收通知机制，类似于aop后置通知
    
## 80 GCRoots和四大引用小结

    枚举根节点做可达性分析（根搜索路径）看图A58 -> 不可达的会被回收
        哪些作为GC ROOTs对象？
            1.虚拟机栈（栈帧中的局部变量区，也叫着局部变量表）  方法中new对象
            2.方法区中类静态属性引用的对象                      类的静态属性
            3.方法区中常量引用的对象                            类的常量属性
            4.本地方法栈中JIN（Native方法）引用的对象         
    强引用：正常赋值        不回收
        软引用：SoftReference   内存紧张时回收
        弱引用：WeakReference   只要gc就回收
        虚引用：PhantomReference随时被回收
        软引用和弱引用适合保存可有可无的缓存数据
        在日常程序编写中，我们对一些不重要的数据，例如缓存数据等，可以采用软引用，弱引用可以加快对象所占用的内存的回收速度
        
## 81 SOFE-StackOverflowError

    异常：Exception in thread "main" java.lang.StackOverflowError
    模拟代码：方法栈溢出：递归调用
    栈空间默认：512k-1024k
    类层级：
        Throwable：
            Error：
                VirtualMachineError：
                    java.lang.StackOverflowError
                    java.lang.OutOfMemoryError
            Exception： 
    所以StackOverflowError和OutOfMemoryError属于错误，而非异常
    
## 82 OOM-java heap space

    异常：Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    模拟代码：堆溢出：字符串无限累加/无线new对象/或者申请一个大于最大堆内存的空间new byte[]

## 83 OOM-GC overhead limit exceeded
    
    异常：java.lang.OutOfMemoryError: GC overhead limit exceeded
    解释：gc回收超过异常警戒（内存急剧上升，大多数资源都用来做垃圾回收了，回收结果也比较差）
        gc回收时间过长抛出，过长的定义是，超过98%的时间都用来做gc，并且回收了不到2%的堆内存，连续多次gc都只回收了不到2%的极端情况下才会抛出。
        加入不抛出此错误会发生什么？
        gc清理的那小快内存很快会被再次填满，迫使gc再次执行，这样就形成了恶性循环
    造成的结果：cpu使用率一直100%，但是gc却没有任何成果，事倍功一小半
    模拟代码：无限循环往常量池中添加字符串 
    
## 84 OOM-Direct buffer memory -直接内存溢出
    
    异常：Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
    解释：直接内存挂了，（元空间并不在虚拟机中，而是使用本地内存，此处挂的就是本地内存，主要是由nio引起的）
    导致原因：
        写nio程序经常使用到ByteBuffer来读取或者写入数据，这是一种基于通道和缓冲区的I/O方式
        他可以使用native函数库直接分配堆外内存，然后通过一个存储在java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作
        这样能在一些场景中显著提高性能，因为避免了在Java堆和native堆中来回复制数据
            ·ByteBuffer.allocate(capability)分配jvm堆内存，属于gc管辖范围，需要拷贝，所以速度较慢
            ·ByteBuffer.allocateDirect(capability)分配os本地内存，不属于gc管辖，不需要内存拷贝，速度快
            但是如果不断分配本地内存，堆内存很少使用，那么jvm就不会执行gc，DirectByteBuffer对象就不会被回收
            此时堆内存充足，但是本地内存可能使用光了，再次尝试分配本地内存就会出现此异常，程序就崩溃了
    总结：引用在jvm，实例在本地内存，避免了来回复制
    模拟代码：
        ·ByteBuffer.allocateDirect(capability)分配os本地内存，不属于gc管辖，不需要内存拷贝，速度快
    
## 85 OOM-unable to create new native thread故障演示 

    异常：不能创建新的本地线程
    解释：一个系统能够创建多少个线程是有上限的，超过报这个错误
    场景：高并发请求服务器时，常见此异常，与对应的平台有关
    导致原因：
        1.一个应用创建了太多线程，超过系统承载极限
        2.服务器不允许应用创建过多线程，linux系统默认允许单个进程创建的线程数是1024个，应用创建超过这个数量，就会报此错误，实际中大概到2/3的时候就会报错，大概900多
    解决：
        1.降低创建线程数量
        2.对于有的应用确实不够，需要创建很多线程，则需要修改linux服务器配置，调高进程的默认线程数
    模拟代码：
        while (true){new Thread().start();}
        liunx演示：
            javac xxx.java
            java 包名.类名 
            运行后报错，无法退出
            ps -ef |grep java
            kill -9 进程号
        
## 86 OOM-unable to create new native thread上限调整

    1.非root用户登录linux系统进行测试
        用户：zhangsan
    2.服务器级别的调优参数：
        查看：ulimit -u
        文件：vim /etc/security/limits.d/90-nproc.conf
            #*          soft    nproc     4096          我的服务器非1024是4069
            #root       soft    nproc     unlimited     root用户无限制
            zhangsan    soft    nproc     100000        给张三用户添加限制

## 87 OOM-Metaspace

    1.使用 java -XX:+PrintFlagsInitial命令查看本机的初始化参数，-XX:MetaspaceSize为218103768 约为21M
    2.介绍：java8之后使用元空间替代永久代
        metaspace是方法区在hotspot中的实现，他与持久代最大的区别在于Metaspace不在虚拟机内存中，而是在本地内存中
        即在java8中class metadata被存储在叫做Metaspace的native memory中
        永久代中存放了以下信息：（java8后被Metaspace替代）
            ·虚拟机加载的类信息
            ·常量池
            ·静态变量
            ·即时编译后的代码
    3.模拟代码：
        模拟Metaspace溢出，不断进行类加载即可

## 88 垃圾收集器回收种类
    
    1.垃圾回收算法和垃圾回收器的关系？分别是什么？
        1.GC算法：包含引用计数，复制，标清，标整，他是内存回收的方法论，而垃圾收集器就是算法的落地实现
        2.目前为止还没有完美的收集器出现，只能根据不同的场景选择合适的收集器，进行分代收集
        3.四种主要的收集器
    2.垃圾回收的方式：- 四种主要的收集器
        1.Serial    - 串行回收
            ·为单线程环境设计且只使用一个线程进行垃圾回收，会暂停所有的用户线程，所以不适合服务器环境，【一人扫地，都出去】
        2.Parallel  - 并行回收
            ·多个垃圾收集线程并行工作，此时用户线程时暂停的，适用于科学计算，大数据处理首台处理等弱交互场景，【多人扫地，都出去】
        3.CMS       - 并发标记清除 -会产生内存碎片
            ·用户线程和垃圾收集线程同时执行，（不一定是并行，可能会交替执行），不需要停顿用户的线程，互联网公司多用他，【多人扫地，不用出去，先换地工作，扫完，再换回来】
                适用对响应时间有要求的场景 - 强交互场景
        4.G1（Garbage） - G1，java10以前就这些，java10以后多了ZGC
           ·jdk7诞生，java8开始使用
           ·G1将堆内存分割为不同的区域，然后并发的对其进行垃圾回收【分割，并发回收】
           
## 89 串行，并行，并发G1四大垃圾回收方式
    
    1.互联网公司基本上要改为：CMS垃圾回收器，
    2.java8之后可能会主要用G1

## 90 如何查看默认的垃圾收集器
    
    1.怎么查看服务器默认垃圾收集器？
        java -XX:+PrintCommandLineFlags -version
            -XX:InitialHeapSize=199473984 -XX:MaxHeapSize=3191583744 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLar
            gePagesIndividualAllocation -XX:+UseParallelGC【java8默认使用并行垃圾回收器】
            java version "1.8.0_231"
            Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
            Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)
        -XX:+UseParallelGC
        -XX:+UseSerialGC        串行垃圾收集器
    2.生产上如何配置垃圾收集器？
    3.谈谈对垃圾收集器的理解？
    
## 91 JVM默认的垃圾收集器有哪些 - java8里面6种，一种没了

    -XX:+UseSerialGC        串行垃圾收集器
    -XX:+UseSerialOldGC     串行垃圾收集器老生代 - 在java8后已经没了
    -XX:+UseParallelGC      并行垃圾收集器：包含新生代及老年代
    -XX:+UseConcMarkSweepGC 并发标记清除收集器-CMS简写
    -XX:UseParNewGC         新生代的垃圾回收-并行收集器
    -XX:UserParallelOldGC   老生代的垃圾回收-并行收集器
    -XX:UseG1GC             分割-并行-回收 
    
    查看运行程序中的垃圾收集器：
        jps -l
        jinfo -flag UseSerialGC 进程号
        -XX:+UseSerialGC 加号表示用了
        
## 92 GC-7大垃圾收集器概述 - 重要
    
    分代收集：
        新生代 使用：serial,ParNew,parallel
        老年代使用：CMS，serialOld,ParalllelOld
        两个都包含：G1

## 93 GC-约定参数说明
    
    1.部分参数预先说明：
        ·DefNew     Default New Generation  默认新生代
        ·Tenured    Old                     老
        ·ParNew     Parallel New Generation 新生代 并行回收
        ·PSYoungGen Parallel Scavenge       
        ·ParOldGen  Parallel Old Generation 老生代 并行回收
    2.Server/Client模式分别是什么意思？
        D:\go-20191030\interview\src\main\java\A20200814_bili第二季\code>java -XX:+PrintCommandLineFlags -version
        -XX:InitialHeapSize=199473984 -XX:MaxHeapSize=3191583744 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLar
        gePagesIndividualAllocation -XX:+UseParallelGC
        java version "1.8.0_231"
        Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
        Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)【Server模式】
        1.适用范围：仅掌握Server模式即可，Client基本不用
        2.操作系统：
            ·32位windows：不管硬件怎么样，都使用的是Client的jvm模式
            ·32位其他操作系统：2G内存且有2CPU时，使用Server，否则使用Client
            ·64位：Only Server 
    3.新生代：
        ·串行 Serial/Serial copying
        ·并行 ParNew
        ·并行回收   Parallel/Parallel Scavenge
    4.老年代
    5.垃圾收集器配置代码总结【重要】
    
## 94 GC-Serial收集器

    1.串行收集器是最古老，最稳定且效率高的收集器，只使用一个线程去回收，垃圾回收的过程中可能会产生较长的停顿（stop-the-word状态）
        在收集过程中需要暂停用户线程，对于限定单个cpu而言，没有环境交互的开销可以获得更高的单线程垃圾收集效率，因此Serial垃圾收集器
        依然是java虚拟机运行在Client模式下的默认的新生代垃圾收集器
    2.对应jvm参数：
        -XX:+UseSerialGC
    3.开启后会使用：Serial（新生代） + SerialOld（老年代）的收集器组合
    4.表示：新生代，老年代都会使用串行垃圾回收器，新生代使用复制算法，老生代使用标记-整理算法
    5.代码：-- VM options
    
## 95 GC-ParNew收集器

    1.使用多线程进行垃圾回收，回收过程中暂停用户线程
        ParNew实际上就是Serial的多线程版本，最常见的应用场景是配合老年代的CMS 进行GC，其余的行为就跟Serial相同
        是很多java虚拟机运行在Server模式下的默认垃圾收集器
    2.对应jvm参数：
        -XX:+UseParNewGC    启动这个只影响新生代的收集，不影响老年代
    3.开启后会使用：ParNew（新生代） + SerialOld（老年代）的收集器组合，新生代使用复制算法，老生代使用标记-整理算法
        但是,ParNew+Tenured 这样的搭配在java8中已经不再被推荐
            Java HotSpot(TM) 64-Bit Server VM warning：
                Using the ParNew young collector whit the Serial Old collector is depredated and whill likely be removed in a future release
    备注：
        -XX:ParallelGCThreads 限制线程数量，默认开启和cpu相同的线程数
        
## 96 GC-Parallel收集器

    1.java8默认使用
        类似ParNew也是一个新生代垃圾收集器，使用复制算法，也是一个并行的多线程的垃圾收集器，俗称吞吐量优先收集器。
        一句话：串行收集器在新生代，老年代的并行化
    2.看图

## 97 GC-ParallelOld收集器

    1.新生代Parallel和老年代ParallelOld可以互相激活
    2.看图
    
## 98 GC-CMS收集器

    1.互联网或B/S架构应用上，若java8最应该优化的地方就是使用CMS - 并发标记清除收集器
    2.ParNew（新生代）+ CMS（老年代） + SerialOld将作为CMS出错后的备用收集器
    3.过程：
        1.初始标记 CMS initial mark
            只是标记一下GC ROOTS能直接关联的对象，速度很快，仍然需要暂停所有工作线程
        2.并发标记 CMS concurrent mark 和用户线程一起
            进行GC ROOTS跟踪的过程，和用户线程一起工作，不需要暂停工作线程，主要标记过程，标记全部对象
        3.重新标记 CMS remark
            为了修正在并发标记期间，因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录，仍然需要暂停所有的工作线程
            由于并发标记时，用户线程依然在运行，因此在正式清理前，再做修正
        4.并发清除 CMS concurrent sweep
            清除GC ROOTS不可达对象，和用户线程一起工作，不需要暂停工作线程。基于标记结果直接清理对象
            由于耗时最长的并发标记和并发清除过程中，垃圾的收集线程可以和用户现在一起并发工作，
            所以总体上来看，CMS收集器的内存回收和用户线程是一起并发的执行
    4.优缺点：
        优点：并发收集低停顿
        缺点：
            1.并发执行对cpu资源压力大
                由于并发进行，CMS在收集与应用线程会同时增加对堆内存的占用，也就是说，CMS必须要在老年代堆内存用尽之前完成垃圾回收，否则cms回收失败时
                将触发担保机制，串行老年代收集器将会以SWT方式进行一次GC，从而造成较大停顿时间
            2.采用标记清除算法会导致大量碎片
                标记清除算法无法整理空间碎片，老年代空间会随着应用时长被逐步耗尽，最后将不得不通过担保机制，对堆内存进行压缩，
                CMS也提供了参数-XX:CMSFullGCsBeForeCompaction(默认为0，即每次都进行内存整理)来指定，多少次CMS收集之后，进行一次压缩的Full GC
                
## 99 GC-SerialOld收集器
    
    1.java8后被优化掉了
    2.可以老年代，新生代分开配置 - 互相关联的只需要配置一个就行
    
## 100 GC-如何选择垃圾收集器
    
    1.组合选择：
        ·单cpu或小内存，单机程序
            -XX:+UseSerialGC
        ·多cpu，需要最大吞吐量，如后台计算型应用，跟前端交互少，可以允许有停顿的
            -XX:+UseParallelGC 或者 【相互激活】
            -XX:+UseParallelOldGC
        ·多cpu，追求低停顿时间，需要快速响应，例如联网应用
            -XX:+UseConcMarkSweepGC
            -XX:+ParNewGC
    
## 101 GC-G1收集器
   
    1.G1垃圾回收器-横跨新生代，老生代 -> 面试常问【重要】
    2.以前收集器特点
        ·新生代和老年代是各自独立且连续的内存块
        ·新生代收集使用单eden+S0+s进行复制算法
        ·老年代收集必须扫描整个老年代区域
        ·都是以尽可能少而快速的执行GC为设计原则
    3.G1是什么（Garbage-First）
        ·是一款面向服务端的收集器，从官网描述，应用在多处理器和大容量内存环境中，在实现高吞吐量的同时，尽可能的满足垃圾收集暂停时间的要求。还具有以下特性：
            1.像CMS收集器一样，能与应用程序线程并发执行
            2.整理空闲空间更快
            3.需要更多的时间来预测GC停顿时间
            4.不希望牺牲大量吞吐性能
            5.不需要更大的JAVA heap
        ·G1收集器的设计目标是取代CMS收集器，他同cms相比，在以下方面表现出色
            1.G1是一个有整理内存过程的垃圾收集器，不会产生很多内存碎片
            2.G1的stop-the-word（STW）更可控，G1在停顿时间上加了预测机制，用户可以指定期望停顿时间   
        ·CMS垃圾收集器虽然减少了暂停应用的运行时间，但是他还是存在着内存碎片的问题。于是为了去除内存碎片的问题，且同时保留CMS垃圾收集器低暂停时间的优点，java7
            发布了一个新的垃圾回收器G1.
        ·G1在2012年在jdk1.7u4中可用，oracle官方计划在jdk9中将默认的垃圾回收器更新为G1以替代CMS。他是一款面向服务端应有的收集器，主要应用在多cpu和大内存服务器环境下
            极大的减少垃圾收集的停顿时间，全面提升服务器性能，逐步替换java8以前的cms收集器
        ·主要改变：-化整为零
            Eden，Survivor，Tenured等内存区域不在是连续的了，而是变成了一个个大小一样的region，每个region从1M到32M不等。
            一个region有可能属于eden，Survivor，Tenured内存区域
        ·特点：
            ·充分利用cpu，多核硬件优势，尽量缩短STW
            ·整体采用标记整理算法，局部是通过复制算法，不会产生内存碎片
            ·宏观上看G1不在区分新生代，老年代，把内存划分为多个独立的子区域（Regin），可以近似理解为一个围棋的棋盘
            ·G1收集器里面将整个的内存区都混合在了一起，但其本身依然在小范围内要进行年轻代和老年代的区分，保留了新生代和老生代，但是他们
                不再是物理隔离的，而是一部分region的集合且不需要region是连续的，也就是说依然会采用不同的gc方式来处理不同的区域
            ·G1虽然也是分代收集器，但是整个内存分区不存在物理上的年轻代与老年代之分，也不需要完全独立的survivor堆做复制准备
                G1只有逻辑上的分代概念，或者说每个分区都可能随着G1的运行在不同代之间前后切换
    4.底层原理
    5.case案例
    6.常用配置参数（了解）
    7.和CMS相比的优势
    8.总结
    
    9.测试参数：
        -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
    10.测试结果
        -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 
        [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0017716 secs]
           [Parallel Time: 1.2 ms, GC Workers: 4]
              [GC Worker Start (ms): Min: 153.6, Avg: 153.7, Max: 153.8, Diff: 0.2]
              [Ext Root Scanning (ms): Min: 0.1, Avg: 0.3, Max: 0.3, Diff: 0.2, Sum: 1.1]
              [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
                 [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
              [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Object Copy (ms): Min: 0.7, Avg: 0.8, Max: 0.8, Diff: 0.1, Sum: 3.1]
              [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.1]
                 [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
              [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
              [GC Worker Total (ms): Min: 1.0, Avg: 1.1, Max: 1.2, Diff: 0.2, Sum: 4.4]
              [GC Worker End (ms): Min: 154.8, Avg: 154.8, Max: 154.8, Diff: 0.0]
           [Code Root Fixup: 0.0 ms]
           [Code Root Purge: 0.0 ms]
           [Clear CT: 0.0 ms]
           [Other: 0.6 ms]
              [Choose CSet: 0.0 ms]
              [Ref Proc: 0.1 ms]
              [Ref Enq: 0.0 ms]
              [Redirty Cards: 0.0 ms]
              [Humongous Register: 0.0 ms]
              [Humongous Reclaim: 0.0 ms]
              [Free CSet: 0.0 ms]
           [Eden: 4096.0K(4096.0K)->0.0B(3072.0K) Survivors: 0.0B->1024.0K Heap: 6912.1K(10.0M)->2616.1K(10.0M)]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [GC concurrent-root-region-scan-start]
        [GC pause (G1 Humongous Allocation) (young)[GC concurrent-root-region-scan-end, 0.0034050 secs]
        [GC concurrent-mark-start]
        , 0.0039835 secs]
           [Root Region Scan Waiting: 2.9 ms]
           [Parallel Time: 0.9 ms, GC Workers: 4]
              [GC Worker Start (ms): Min: 160.0, Avg: 160.3, Max: 160.9, Diff: 0.9]
              [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
              [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
                 [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
              [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Object Copy (ms): Min: 0.0, Avg: 0.5, Max: 0.6, Diff: 0.6, Sum: 1.8]
              [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.2]
                 [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
              [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [GC Worker Total (ms): Min: 0.0, Avg: 0.6, Max: 0.9, Diff: 0.9, Sum: 2.5]
              [GC Worker End (ms): Min: 160.9, Avg: 160.9, Max: 160.9, Diff: 0.0]
           [Code Root Fixup: 0.0 ms]
           [Code Root Purge: 0.0 ms]
           [Clear CT: 0.0 ms]
           [Other: 0.1 ms]
              [Choose CSet: 0.0 ms]
              [Ref Proc: 0.1 ms]
              [Ref Enq: 0.0 ms]
              [Redirty Cards: 0.0 ms]
              [Humongous Register: 0.0 ms]
              [Humongous Reclaim: 0.0 ms]
              [Free CSet: 0.0 ms]
           [Eden: 1024.0K(3072.0K)->0.0B(3072.0K) Survivors: 1024.0K->1024.0K Heap: 3957.6K(10.0M)->2051.9K(10.0M)]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [GC concurrent-mark-end, 0.0012521 secs]
        [GC remark [Finalize Marking, 0.0000615 secs] [GC ref-proc, 0.0000362 secs] [Unloading, 0.0004784 secs], 0.0006381 secs]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [GC cleanup 3393K->3393K(10M), 0.0001072 secs]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [GC pause (G1 Humongous Allocation) (young), 0.0008076 secs]
           [Parallel Time: 0.6 ms, GC Workers: 4]
              [GC Worker Start (ms): Min: 163.7, Avg: 163.8, Max: 163.9, Diff: 0.2]
              [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.5]
              [Update RS (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.8]
                 [Processed Buffers: Min: 0, Avg: 0.5, Max: 1, Diff: 1, Sum: 2]
              [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Object Copy (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 0.8]
                 [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 4]
              [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
              [GC Worker Total (ms): Min: 0.4, Avg: 0.5, Max: 0.6, Diff: 0.2, Sum: 2.1]
              [GC Worker End (ms): Min: 164.3, Avg: 164.3, Max: 164.3, Diff: 0.0]
           [Code Root Fixup: 0.0 ms]
           [Code Root Purge: 0.0 ms]
           [Clear CT: 0.0 ms]
           [Other: 0.2 ms]
              [Choose CSet: 0.0 ms]
              [Ref Proc: 0.1 ms]
              [Ref Enq: 0.0 ms]
              [Redirty Cards: 0.0 ms]
              [Humongous Register: 0.0 ms]
              [Humongous Reclaim: 0.0 ms]
              [Free CSet: 0.0 ms]
           [Eden: 1024.0K(3072.0K)->0.0B(2048.0K) Survivors: 1024.0K->0.0B Heap: 6014.9K(10.0M)->4553.0K(10.0M)]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [Full GC (Allocation Failure)  4553K->4461K(10M), 0.0027711 secs]
           [Eden: 0.0B(2048.0K)->0.0B(2048.0K) Survivors: 0.0B->0.0B Heap: 4553.0K(10.0M)->4461.0K(10.0M)], [Metaspace: 3215K->3215K(1056768K)]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        [Full GC (Allocation Failure)  4461K->4442K(10M), 0.0020325 secs]
           [Eden: 0.0B(2048.0K)->0.0B(2048.0K) Survivors: 0.0B->0.0B Heap: 4461.0K(10.0M)->4442.9K(10.0M)], [Metaspace: 3215K->3215K(1056768K)]
         [Times: user=0.00 sys=0.00, real=0.00 secs] 
        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        	at java.util.Arrays.copyOfRange(Arrays.java:3664)
        	at java.lang.String.<init>(String.java:207)
        	at java.lang.StringBuilder.toString(StringBuilder.java:407)
        	at A20200814_bili第二季.code.A101_G1.exam1(A101_G1.java:26)
        	at A20200814_bili第二季.code.A101_G1.main(A101_G1.java:18)
        Heap
         garbage-first heap   total 10240K, used 4442K [0x00000000ff600000, 0x00000000ff700050, 0x0000000100000000)
          region size 1024K, 1 young (1024K), 0 survivors (0K)
         Metaspace       used 3247K, capacity 4496K, committed 4864K, reserved 1056768K
          class space    used 351K, capacity 388K, committed 512K, reserved 1048576K
        Heap变成了两层：
            garbage-first heap  -G1区
            Metaspace           -元数据区
    11.
    
    
## 102 GC-G1底层原理
    
    1.见图
    
## 103 GC-G1参数配置及CMS的比较

    1.常用参数配置：
        -XX:+UseG1GC
        -XX:G1HeapRegionSize=n 设置G1的region大小，值是2的幂，范围1M-32M，目标是根据最小的java堆大小划分出约2048个区域，大概最多有64G
        -XX:MaxGCPauseMills=n  最大GC停顿时间，这是个软目标，jvm将尽可能（但不保证）停顿小于这个时间 
        -XX:InitiatingHeapOccupancyPercent=n    堆占用了多少的时候就触发GC，默认为45
        -XX:ConcGCThreads=n 并发gc使用的线程数
        -XX:G1ReservePercent=n  设置作为空闲空间的预留内存百分比，以降低目标空间移除的风险，默认为10%
    2.G1的生产建议配置：
        -XX:+UseG1GC -Xms32g -XX:MaxGCPauseMills=100
    3.目前常用的还是：CMS，新技术暂不敢用，可以尝试使用
    
## 104 JVM-GC结合SpringBoot微服务优化简介
    
    1.调优过程：
        1.idea开发完代码
        2.maven进行clean package打成war包或者jar包
        3.要求为服务启动的时候，同时配置JVM/GC的调优参数
            3.1 内 - 指在idea开发工具中配置 VM Option
            3.2 外 - war包或者jar包的启动【重点】
        4.公式：
            java -server 【jvm的各种参数】 -jar 【xxx.jar/xxx.war】        
            示例：
                java -server -Xms1024m -Xmx1024m -XX:+UseG1GC -jar seckill.jar
                jps -l
                jinfo -flags 进程号
        5.Undertow服务器替换tomcat，每秒的QPS比tomcat高出1-3K
    
## 105 linux命令-top

    1.生产服务器变慢了，诊断思路和性能评估？
        整机：     top
        CPU:       vmstat 
        内存：     free
        硬盘：     df
        磁盘io：   iostat
        网络io：   ifstat
    2.top
        主要看的是：
            %CPU
            %MEM
            load average
        看核数：
            按【1】
            看%cpu的个数
        [root@VM_0_4_centos ~]# top
            【load average：系统负载，三个值平均代表系统1分钟，5分钟，15分钟系统的平均负载，如果三个值相加除以3 若高于60%，则系统的负载压力重】
            top - 19:29:32 up 66 days,  5:11,  1 user,  load average: 0.02, 0.03, 0.05
            Tasks: 120 total,   1 running, 119 sleeping,   0 stopped,   0 zombie
            %Cpu(s):  1.2 us,  1.2 sy,  0.0 ni, 97.7 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st【按1之前】
            KiB Mem :  3880316 total,   188604 free,  1057012 used,  2634700 buff/cache
            KiB Swap:        0 total,        0 free,        0 used.  2534972 avail Mem 
            
              PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND                                                                                                   
            22248 root      20   0  611616  13752   2220 S   1.7  0.4 153:51.50 barad_agent 
        在top命令执行的情况下，按【1】可见
            top - 19:35:38 up 64 days,  9:15,  1 user,  load average: 0.00, 0.01, 0.05
            Tasks: 110 total,   1 running, 109 sleeping,   0 stopped,   0 zombie
            %Cpu0  :  1.0 us,  1.0 sy,  0.0 ni, 98.0 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st【按1之后】可知只有cpu0和cpu1表示 此服务器为2核
            %Cpu1  :  0.7 us,  1.0 sy,  0.0 ni, 98.3 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st【按1之后】可知只有cpu0和cpu1表示 此服务器为2核
            KiB Mem :  3880316 total,   133532 free,  2461928 used,  1284856 buff/cache
            KiB Swap:        0 total,        0 free,        0 used.  1130052 avail Mem 
    3.uptime：- 查看负载
        [root@VM_0_4_centos ~]# uptime
         19:39:50 up 66 days,  5:21,  1 user,  load average: 1.18, 0.33, 0.14 
         【第一个插过0.6过多，第二个低，第三个低，某段时间高负载】

## 106 linux命令-cpu查看vmstat
    
    1.vmstat -n 2 3  【每两秒采样一次，一共采样三次】
        [root@VM_0_4_centos ~]#  vmstat -n 2 3
        procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
         r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
         1  0      0 192228 187744 2447692    0    0     2    17    1    2  1  1 97  0  0
         0  0      0 192272 187744 2447692    0    0     0    36  426  875  1  1 98  0  0
         1  0      0 191416 187744 2447692    0    0     0     0  507  773  1  1 98  0  0
        vmstat 是通过两个参数来完成的，第一个参数是采样的时间间隔数，单位为秒，第二个参数是采样的次数
        -procs
            r表示运行runtime
                运行和等待cpu时间片的进程数，原则上1核的cpu的运行队列不要超过2，整个系统的运行队列不能超过总核数的两倍，否则代表系统压力过大
            b表示阻塞block
                等待资源的进程数，比如正在等待磁盘IO，网络IO等
        -cpu：
            ·us 用户进程消耗cpu时间百分比，us值高，用户进程消耗cpu时间多，如果长期大于50%，优化程序
            ·sy 内核进程消耗的cpu时间百分比
            ·us + sy: 参考值为80%，如果大于80%，可能cpu不足
            ·id 处于空闲的cpu百分比
            ·wa 系统等待io的cpu时间百分比
            ·st 来自于一个虚拟机偷取的cpu时间的百分比
            
## 107 linux命令-cpu查看pidstat

    1.查看所有cpu核信息：-更详细
        mpstat -P ALL 2 每隔两秒采样一次
    2.每个进程使用cpu的用量分解信息
        ps -ef |grep java
        pidstat -u 1 -p 进程编号 1代表每秒采样一次
    3.安装pidstat以及mpstat及iostat：
        pidstat和mpstat和iostat 是sysstat软件套件的一部分，sysstat包含很多监控linux系统状态的工具，它能够从大多数linux发行版的软件源中获得。
        在Debian/Ubuntu系统中可以使用下面的命令来安装
        　　# apt-get install sysstat
        CentOS/Fedora/RHEL版本的linux中则使用下面的命令：
        　　# yum install sysstat
        使用：
            [root@VM_0_4_centos ~]# ps -ef|grep java
                root     20181     1  0 Jul31 ?        01:17:26 java -jar api.jar --server.port=8080
            [root@VM_0_4_centos ~]# pidstat -u 1 -p 20181
                Linux 3.10.0-1127.8.2.el7.x86_64 (VM_0_4_centos) 	09/04/2020 	_x86_64_	(2 CPU)
                08:13:15 PM   UID       PID    %usr %system  %guest    %CPU   CPU  Command
                08:13:16 PM     0     20181    0.00    0.00    0.00    0.00     0  java
                08:13:17 PM     0     20181    0.00    1.00    0.00    1.00     0  java
            PID - 被监控的任务的进程号
            %usr - 当在用户层执行（应用程序）时这个任务的cpu使用率，和 nice 优先级无关。注意这个字段计算的cpu时间不包括在虚拟处理器中花去的时间
        　　%system - 这个任务在系统层使用时的cpu使用率。
            %guest - 任务花费在虚拟机上的cpu使用率（运行在虚拟处理器）
        　　%CPU - 任务总的cpu使用率。在SMP环境（多处理器）中，如果在命令行中输入-I参数的话，cpu使用率会除以你的cpu数量。
            CPU - 正在运行这个任务的处理器编号。
        　　Command - 这个任务的命令名称
        其他命令：
            1.pidstat -d -p 8472  【通过使用-d参数来得到I/O的统计数据】
                IO 输出会显示一些内的条目：
                    kB_rd/s - 任务从硬盘上的读取速度（kb）
                    kB_wr/s - 任务向硬盘中的写入速度（kb）
                    kB_ccwr/s - 任务写入磁盘被取消的速率（kb）    
            2.pidstat -d -p 8472 【使用-r标记你能够得到内存使用情况的数据】
                重要的条目：
                　　minflt/s - 从内存中加载数据时每秒出现的小的错误的数目，这些不要求从磁盘载入内存页面。
                　　majflt/s - 从内存中加载数据时每秒出现的较大错误的数目，这些要求从磁盘载入内存页面。
                　　VSZ - 虚拟容量：整个进程的虚拟内存使用（kb）
                　　RSS - 长期内存使用：任务的不可交换物理内存的使用量（kb）
            3.常用：
                1. 你可以通过使用下面的命令来监测内存使用,这会给你5份关于page faults的统计数据结果，间隔2秒。这将会更容易的定位出现问题的进程。
                　　# pidstat -r 2 5 
                2. 显示所有mysql服务器的子进程
                　　# pidstat -T CHILD -C mysql
                3. 将所有的统计数据结合到一个便于阅读的单一报告中：
                　　# pidstat -urd -h
                
## 108 linux命令-内存查看free和pidstat

    1.内存查看
        free        按单位字节展示
        free -g     按单位G展示
        free -m     按单位兆（m）展示
            [root@VM_0_4_centos ~]# free -m
                          total        used        free      shared  buff/cache   available
            Mem:           3789        1027         224           1        2537        2480
            Swap:             0           0           0
        经验值：
            应用程序可用内存/系统物理内存>70% 内存充足
            应用程序可用内存/系统物理内存<20% 内存不足，需要增加内存
            介于中间表示基本够用
    2.查看进程所占内存：
        pidstat -p 进程号 -r 采样间隔秒数
                
## 109 linux命令-硬盘查看df

    0.查看磁盘剩余空间数
    1.df
    2.df -h 【-h表示用人眼可见的数字展示】
    
## 110 linux命令-磁盘io查看iostat，pidstat

    1.磁盘IO性能评估：网络系统慢，一般不是cpu慢就是磁盘io慢，mysql大表存储时就会大量占用磁盘io
    2.命令 iostat
        iostat -xdk 2 3     2秒一次一共三次
        [root@VM_0_4_centos ~]# iostat -xdk 2 3
        Linux 3.10.0-1127.8.2.el7.x86_64 (VM_0_4_centos) 	09/05/2020 	_x86_64_	(2 CPU)
        Device:         rrqm/s   wrqm/s     r/s     w/s    rkB/s    wkB/s avgrq-sz avgqu-sz   await r_await w_await  svctm  %util
        vda               0.00     3.49    0.14    3.78     3.06    33.06    18.44     0.02    7.00    4.85    7.08   0.48   0.19
            rKB/s每秒读取数据Kb
            wKB/s每秒写入数据Kb
            svctm IO请求的平均服务时间，单位毫秒
            await IO请求的平均等待时间，单位毫秒，值越小，性能越好
            util  一秒中有百分之几的时间用于IO操作。接近100%时，表示磁盘带宽跑满，需要优化程序或者增加磁盘
    3.命令 pidstat 查看进程所占IO
        pidstat -d 采样间隔秒数 -p 进程号
        
## 111 linux命令-网络io查看ifstat

    1.网络io：ifstat，如果没有，默认要下载
        wget http://gael.roualland.free.fr/ifstat/ifstat-1.1.tar.gz
        tar zxvf ifstat-1.1.tar.gz
        cd ifstat-1.1
        ./configure
        make
        make install
    2.观察各个网卡的in ，out
        观察网络负载情况
        程度网络读写是否正常
        程序网络IO优化
        增加网络IO带宽
    3.命令：
        ifstat l
        ifstat
    
## 112 cpu占用过高的定位分析思路

    1.结合linux和jdk命令一块分析
    2.分析步骤：
        1.先用top命令找出cpu占用最高的程序
            top -S 以累积模式显示程序信息
            根据以上命令能获取到Pid
        2.ps -ef 或者jps进一步定位到具体程序
            jps -l
            或者
            ps -ef|grep java|grep -v grep
                ps -ef|grep cpu会把grep cpu的进程也统计进来，因此用ps -ef|grep cpu|grep -v grep去除grep进程
        3.定位到具体线程或者代码
            ps -mp 进程号 -o THREAD,tid,time
                [root@VM_0_4_centos ~]# ps -mp 7690 -o THREAD,tid,time
                USER     %CPU PRI SCNT WCHAN  USER SYSTEM   TID     TIME
                polkitd   0.6   -    - -         -      -     - 09:59:45
                polkitd   0.0  19    - poll_s    -      -  7690 00:00:00
            -m 显示所有的线程
            -p pid进程使用的cpu的时间
            -o 该参数后是用户自定义格式     
        4.将需要的线程id转换为16进制格式（英文小写格式）
            命令：printf "%x\n" 有问题的线程id
            直接换算：电脑计算机中输入后 ，点击Rsh ，可在左上角看到 HEX 的值即为对应16进制
        5.jstack 进程id| grep tid(16进制线程id小写英文) -A60（打印出前60行）
    3.实例测试：
        [root@VM_0_4_centos ~]# top -S
              PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND                                                                                                   
            20181 root      20   0 3564196 508064   7096 S   0.3 13.1  78:41.99 java   
        [root@VM_0_4_centos ~]# jps -l
            20181 api.jar
            28021 sun.tools.jps.Jps
        [root@VM_0_4_centos ~]# ps -mp 20181 -o THREAD,tid,time
            USER     %CPU PRI SCNT WCHAN  USER SYSTEM   TID     TIME
            root      0.0  19    - futex_    -      - 22113 00:00:00
            root      0.0  19    - futex_    -      - 22114 00:00:00
        [root@VM_0_4_centos ~]# printf "%x\n" 22113
            5661
        [root@VM_0_4_centos ~]# jstack 20181| grep 5661 -A60    【从进程20181的程序中，找到某线程问题..在以下内容中找出属于本项目包内的问题，定位到行】
            "http-nio-8080-exec-13" #44 daemon prio=5 os_prio=0 tid=0x00007fdcd0005000 nid=0x5661 waiting on condition [0x00007fdcd462d000]
               java.lang.Thread.State: WAITING (parking)
                at sun.misc.Unsafe.park(Native Method)
                - parking to wait for  <0x00000000c7f34af0> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
                at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
                at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
                at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
                at org.apache.tomcat.util.threads.TaskQueue.take(TaskQueue.java:103)
                at org.apache.tomcat.util.threads.TaskQueue.take(TaskQueue.java:31)
                at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
                at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
                at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
                at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
                at java.lang.Thread.run(Thread.java:748)
    4.内存过高定位：
        1.定位进程
            通过top -c（然后按Shift+M按内存排序），或者htop等工具定位到具体的高内存进程。假设定位到的进程ID为14279。
        2.定位线程
            2.1 通过top查看线程
                top -H -p 14279（然后按Shift+M按内存排序）定位占内存的线程：
            2.2 通过ps统计下当前进程的线程数
                ps p 14279 -L -o pcpu,pmem,pid,tid,time,tname,cmd |wc -l
        3.初步判断
            通过以上二步确认是否线程开多了，还是单个线程内存占用过多导致。
            如果是线程过多，那么就要去排查具体原因。是服务器线程，还是业务代码中的多线程导致？
            如果是单线程内存占用，那么就要dump快照或者本地尝试模拟重现
        4.Java提供了一个很好的内存监控工具：jmap命令
            jmap [pid]
            jmap -histo:live [pid] >a.log
            jmap -dump:live,format=b,file=xxx.xxx [pid]
             用得最多是后面两个。其中，jmap -histo:live [pid] 可以查看当前Java进程创建的活跃对象数目和占用内存大小。
             jmap -dump:live,format=b,file=xxx.xxx [pid] 则可以将当前Java进程的内存占用情况导出来，方便用专门的内存分析工具（例如：MAT）来分析。这个命令对于分析是否有内存泄漏很有帮助
            命令：
               jmap -histo:live 14279 |head -n 100
       5.jmap内存快照：
            如果是线上环境，注意dump之前必须先将流量切走，否则大内存dump是直接卡死服务。
            # dump当前快照
                jmap -dump:live,format=b,file=dump.hprof <pid>
                # 触发full gc，然后再dump一次
                jmap -dump:live,format=b,file=dump_gc.hprof <pid>
                dump:live的作用是会触发Full GC，然后再dump数据，用作gc前后的数据做对比。
       6.上传到fastthread.io分析
             如果快照文件不大，也可以上传到https://fastthread.io/分析。
       7.通过jhat分析
             如果快照文件很大，可以在服务器上直接分析：
                 faceless@ttg12:~/tmp$ jhat dump.hprof
                 Reading from dump.hprof...
                 Dump file created Mon Jun 22 14:33:00 CST 2020
                 Snapshot read, resolving...
                 Resolving 36246 objects...
                 Chasing references, expect 7 dots.......
                 Eliminating duplicate references.......
                 Snapshot resolved.
                 Started HTTP server on port 7000
                 Server is ready.
             分析完成后，访问http://ttg12:7000，如下图：    
       8.监视垃圾回收次数，时间
            jstat -gcutil 进程号
            jstat -gcutil 20181 3000 10 【监控进程20181，每间隔3秒，记录一次，共计10次】
                [root@VM_0_4_centos ~]# jstat -gcutil 20181
                  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
                  0.00   0.00   1.28  26.50  97.08  94.64     58    0.584     6    0.987    1.571
                [root@VM_0_4_centos ~]# jstat -gcutil 20181 3000 10
                  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
                  0.00   0.00   1.28  26.50  97.08  94.64     58    0.584     6    0.987    1.571
                    S0: 幸存区0
                    S1: 幸存区1
                    E：年轻代
                    O：年老代
                    M：持久代
                        （以上都是已使用所占百分比）
                    YGC ： 年轻代YGC的次数
                    YGCT ：年轻代YGC所消耗的时间
                    FGC ： 年老代full GC的次数
                    FGCT ：年老代full GC所消耗的时间
                    GCT : 用于GC所消耗的总时间
    5.内存过高查询：
        jmap -heap 20181       
    6.一般出故障了，怎么调试+排查+检索：内存快照+MIT分析等
            
## 113 github-开启

    1.github上优秀的框架+源码，提升自己

## 114 github-常用词

    1.面试题
    2.寻找优秀源码+框架深度解读+学习代码+贡献
    
## 115 github-in限制搜索
    
    1.例如搜索秒杀的代码学习
    2.in限制搜索：-> 阿里对这部分使用考察较多
        公式1：
            关键字 in:name或description或readme 
            seckill in:name         名称包含seckill的项目
            seckill in:description  描述包含seckill的项目
            seckill in:readme       自述文件包含seckill的项目
            seckill in:name,readme  名称和自述文件都包含seckill的项目【组合使用】
       
## 116 github-star和fork范围搜索
    
    1.公式1：
        关键词 stars 通配符 【:> 或者 :>=】
    2.公式2：
        区间范围数字 【数字1..数字2】
    3.查找stars数大于等于5000的springboot项目
        springboot stars:>=5000
    4.查找fork数大于500的springcloud项目
        springcloud forks:>500
    5.查找fork在100-200之间并且star数在80-100之间的springboot项目
        springboot stars:80..100 forks:100..200
    
## 117 github-awesome搜索

    1.公式：
        awesome 关键字 【此查询一般用来收集学习，工具，书籍类相关项目，awesome-令人敬畏】

## 118 github-#L数字
    
    1.高亮显示某行代码：告诉别人代码的具体位置
    2.例如：
        逃逸分析示例代码：
        https://github.com/a982338665/lf-interview/blob/master/src/main/java/A20200807_bili%E7%AC%AC%E4%B8%80%E5%AD%A3/Test24.java
        告诉别人的时候：地址+#L第几行  即表示定位到文件第七行
        https://github.com/a982338665/lf-interview/blob/master/src/main/java/A20200807_bili%E7%AC%AC%E4%B8%80%E5%AD%A3/Test24.java#L7
        高亮显示第7到第18行
        https://github.com/a982338665/lf-interview/blob/master/src/main/java/A20200807_bili%E7%AC%AC%E4%B8%80%E5%AD%A3/Test24.java#L7-L18
    
## 119 github-T搜索

    1，点击进入仓库后，按键t，然后就能够全局搜索
    
## 120 github-搜索区域活跃用户

    1.公式：
        location:地区
        language:语言
    2.北京地区java用户
        location:beijing language:java
    
  
