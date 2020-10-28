package A20200907_bill尚硅谷_设计模式.A1_单例模式.$1;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/28 17:06
 * @Description ://饿汉式(静态变量)
 */
public class Singleton {
    //1. 构造器私有化,  外部能
    private Singleton() {
    }

    //2.本类内部创建对象实例
    private final static Singleton instance = new Singleton();

    //3. 提供一个公有的静态方法，返回实例对象
    public static Singleton getInstance() {
        return instance;
    }
}
