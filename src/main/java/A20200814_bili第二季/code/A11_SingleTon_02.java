package A20200814_bili第二季.code;

/**
 * 线程安全的懒汉式：双检锁 + volatile
 * 1.多线程时，可能会有多个线程同时执行步骤2，所以可以考虑在步骤2加锁限制，如下
 * 2.步骤2加锁后，仍旧有问题：
 *      1.当多个线程同时执行到 synchronized时，程序由并行转为串行执行，此时已经解决了创建多个对象的问题
 *      2.当线程A执行到步骤2，线程B执行到步骤1的时候，可能会由于JVM指令重排的原因，造成步骤3中返回不完整的实例对象
 *      3.对上一步的解释：
 *          ·指令重排序：是编译器在不改变执行效果的前提下，对指令顺序进行调整，从而提高执行效率的过程
 *          ·new对象包含的过程：步骤2中  hungrySingleton = new LazySingletonSafe2();
 *              1.分配内存
 *              2.引用指向内存        hungrySingleton
 *              3.内存中存放实例      new LazySingletonSafe2()
 *          ·由上可知2,3依赖于1，所以指令重排时，2,3可能会交换，交换后会出现问题：
 *              当A线程执行到步骤2的时候，指令重排序，导致引用指向了空内存（此时1,3执行，2未执行）
 *              同时B线程到达步骤1，发现其引用指向不为null，就会返回一个不完整的实例对象
 *          ·解决方法：
 *              对私有静态属性 添加volatile关键字，保证内存可见性和防止指令重排
 *      4.综上：懒汉式线程不安全的两个问题主要来源于：
 *          1.创建多个对象    synchronized
 *          2.指令重排        volatile
 *
 */
public class A11_SingleTon_02 {

    //1.构造方法私有化
    private A11_SingleTon_02() {
    }

    //2.私有静态属性
    private static volatile A11_SingleTon_02 hungrySingleton = null;

    //3.对外公开获取方法
    public static A11_SingleTon_02 getInstance() {
        //步骤1
        if (hungrySingleton == null) {
            synchronized (A11_SingleTon_02.class) {
	            //第二次检查，当多线程时，两个线程同时经过了第一次检查，到达synchronized
                //这时将没有限制的创建两次对象，所以在经过第一次判断后，synchronized后
                //仍旧需要一次判断来确认该对象只会被创建一次
                if (hungrySingleton == null) {
                    //步骤2
                    hungrySingleton = new A11_SingleTon_02();
                }
            }
        }
        //步骤3
        return hungrySingleton;
    }
}
