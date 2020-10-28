package A20200907_bill尚硅谷_设计模式.A1_单例模式.$3;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/28 17:11
 * @Description :
 */
public class SingletonTest03 {
    public static void main(String[] args) {
        System.out.println("懒汉式 1 ， 线程不安全~");
        Singleton instance = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance == instance2); // true
        System.out.println("instance.hashCode=" + instance.hashCode());
        System.out.println("instance2.hashCode=" + instance2.hashCode());
    }
}
