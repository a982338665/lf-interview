package A20200828_设计模式.A7_适配器模式.cn.extend;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 14:00
 * @Description :
 */
public class Adapter220VTo440V extends Chatou220V implements Target{

    @Override
    public void ConvertToXXX(){
        this.V220();
        System.err.println("转换为440V的插头");
    }
}
