package A20200907_bill尚硅谷_设计模式.A1_单例模式.$8;

//推荐使用
////使用枚举，可以实现单例, 推荐

/**
 * 优缺点说明：
 * 1)这借助 JDK1.5 中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 * 2)这种方式是 Effective Java 作者 Josh Bloch  提倡的方式
 */
public enum  Singleton {
    INSTANCE; //属性
    public void sayOK() {
        System.out.println("ok~");
    }

}
