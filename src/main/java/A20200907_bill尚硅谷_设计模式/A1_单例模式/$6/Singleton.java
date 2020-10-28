package A20200907_bill尚硅谷_设计模式.A1_单例模式.$6;

//推荐使用
public class Singleton {
    private static volatile Singleton instance;


    private Singleton() {
    }

//提供一个静态的公有方法，加入双重检查代码，解决线程安全问题, 同时解决懒加载问题
//同时保证了效率, 推荐使用


    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }


        }
        return instance;
    }
}
