
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
    
## 56 JVM-GC下半场技术加强说明和前提知识要求
    
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
## 59 JVM的标配参数和X参数
## 60 JVM的XX参数及布尔类型
## 61 JVM的XX参数及设值类型
## 62 JVM的XX参数及Xms，Xmx坑题
## 63 JVM盘点家底查看初始默认值
## 64 JVM盘点家底查看修改变更值
## 65 堆内存初始大小快速复习 
## 66 常用基础参数栈内存Xss
## 67 常用基础参数元空间MetaspaceSize
## 68 常用基础参数PrintGCDetails回收前后对比
## 69 常用基础参数SurvivorRatio
## 70 常用基础参数NewRatio
## 71 常用基础参数Max Tenuring Threshold
## 72 强引用
## 73 软引用
## 74 弱引用
## 75 软引用和弱引用的使用场景
## 76 WeakHashMap案例演示
## 77 虚引用
## 78 ReferenceQueue引用队列
## 79 虚引用PhantomReference
## 80 GCRoots和四大引用小结
## 81 SOFE-StackOverflowError
## 82 OOM-java heap space
## 83 OOM-GC overhead limit exceeded
## 84 OOM-Direct buffer memory
## 85 OOM-unable to create new native thread故障演示
## 86 OOM-unable to create new native thread上限调整
## 87 OOM-Metaspace
## 88 垃圾收集器回收种类
## 89 串行，并行，并发G1四大垃圾回收方式
## 90 如何查看默认的垃圾收集器
## 91 JVM默认的垃圾收集器有哪些
## 92 GC-7大垃圾收集器概述
## 93 GC-约定参数说明
## 94 GC-Serial收集器
## 95 GC-ParNew收集器
## 96 GC-Parallel收集器
## 97 GC-ParallelOld收集器
## 98 GC-CMS收集器
## 99 GC-SerialOld收集器
## 100 GC-如何选择垃圾收集器
## 101 GC-G1收集器
## 102 GC-G1底层原理
## 103 GC-G1参数配置及CMS的比较
## 104 JVM-GC结合SpringBoot微服务优化简介
## 105 linux命令-top
## 106 linux命令-cpu查看vmstat
## 107 linux命令-cpu查看pidstat
## 108 linux命令-内存查看free和pidstat
## 109 linux命令-硬盘查看df
## 110 linux命令-磁盘io查看iostat，pidstat
## 111 linux命令-网络io查看ifstat
## 112 cpu占用过高的定位分析思路
## 113 github-开启
## 114 github-常用词
## 115 github-in限制搜索
## 116 github-star和fork范围搜索
## 117 github-awesome搜索
## 118 github-#L数字
## 119 github-T搜索
## 120 github-搜索区域活跃用户
    
