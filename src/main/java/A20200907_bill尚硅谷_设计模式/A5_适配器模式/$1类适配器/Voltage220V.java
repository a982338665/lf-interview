package A20200907_bill尚硅谷_设计模式.A5_适配器模式.$1类适配器;

//被适配的类
public class Voltage220V {
    //输出 220V 的电压
    public int output220V() {
        int src = 220;
        System.out.println("电压=" + src + "伏");
        return src;
    }
}
