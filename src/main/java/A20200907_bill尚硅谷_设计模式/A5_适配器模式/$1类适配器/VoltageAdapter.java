package A20200907_bill尚硅谷_设计模式.A5_适配器模式.$1类适配器;

public class VoltageAdapter extends Voltage220V implements IVoltage5V {

    @Override
    public int output5V() {
// TODO Auto-generated method stub
//获取到 220V 电压
        int srcV = output220V();
        int dstV = srcV / 44; //转成 5v
        return dstV;
    }

}
