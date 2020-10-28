package A20200907_bill尚硅谷_设计模式.A1_单例模式.$2;

public class SingletonTest02 {


public static void main(String[] args) {
//测试
Singleton instance = Singleton.getInstance(); Singleton instance2 = Singleton.getInstance(); System.out.println(instance == instance2); // true
System.out.println("instance.hashCode=" + instance.hashCode()); System.out.println("instance2.hashCode=" + instance2.hashCode());
}


}
