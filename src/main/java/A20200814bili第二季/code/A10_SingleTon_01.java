package A20200814bili第二季.code;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/20 19:39
 * @Description : 单线程环境下
 */
public class A10_SingleTon_01 {

    private static A10_SingleTon_01 instance = null;

    private A10_SingleTon_01() {
        System.err.println(Thread.currentThread().getName()+ "   每次创建对象时调用。。。");
    }

    public static A10_SingleTon_01 getInstance() {
        if (instance == null) {
            instance = new A10_SingleTon_01();
        }
        return instance;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.err.println("单线程下的单例模式======================================");
        //单线程下的单例模式,仅会调用一次构造函数
//        getOneThread();
        System.err.println("多线程下 错误的======================================");
        //多线程下可能会调用多次构造函数
        //简单解决方式：加synchronized，但是不建议使用
        getMuiltThread();
    }

    private static void getMuiltThread() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
//                System.err.println(Thread.currentThread().getName()+"|||"+A10_SingleTon_01.getInstance());
                A10_SingleTon_01.getInstance();
            }, i + "").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
//                System.err.println(Thread.currentThread().getName()+"|||"+A10_SingleTon_01.getInstance());
                A10_SingleTon_01.getInstance();
            }, i + "").start();
        }
    }

    private static void getOneThread() {
        System.err.println(A10_SingleTon_01.getInstance() == A10_SingleTon_01.getInstance());
        System.err.println(A10_SingleTon_01.getInstance() == A10_SingleTon_01.getInstance());
        System.err.println(A10_SingleTon_01.getInstance() == A10_SingleTon_01.getInstance());
    }
}
