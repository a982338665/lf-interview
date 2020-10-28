package A20200907_bill尚硅谷_设计模式.A1_单例模式.$5;

public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    //提供一个静态的公有方法，加入同步处理的代码，解决线程安全问题
//即懒汉式
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class){
                instance = new Singleton();
            }
        }
        return instance;
    }
}
