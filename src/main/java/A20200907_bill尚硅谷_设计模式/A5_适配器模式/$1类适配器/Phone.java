package A20200907_bill尚硅谷_设计模式.A5_适配器模式.$1类适配器;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 14:29
 * @Description :
 */
public class Phone {
    //充电
    public void charging(IVoltage5V iVoltage5V) {
        if (iVoltage5V.output5V() == 5) {
            System.out.println("电压为 5V, 可以充电~~");
        } else if (iVoltage5V.output5V() > 5) {
            System.out.println("电压大于 5V, 不能充电~~");
        }
    }
}
