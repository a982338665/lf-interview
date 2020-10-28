package A20200907_bill尚硅谷_设计模式.A1_单例模式.$3;

public class Singleton {
    private static Singleton instance;


    private Singleton() {
    }

    //提供一个静态的公有方法，当使用到该方法时，才去创建 instance
    //即懒汉式
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
