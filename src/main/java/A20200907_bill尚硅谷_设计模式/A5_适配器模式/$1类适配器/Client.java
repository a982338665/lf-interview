package A20200907_bill尚硅谷_设计模式.A5_适配器模式.$1类适配器;

public class Client {

    public static void main(String[] args) {
// TODO Auto-generated method stub
        System.out.println(" === 类适配器模式 ====");
        Phone phone = new Phone();
        phone.charging(new VoltageAdapter());
    }

}
