package A20200907_bill尚硅谷_设计模式.A6_桥接模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 16:05
 * @Description :
 */
public class XiaoMiBrand implements Brand {
    @Override
    public void call() {
        System.err.println("小米 call....");
    }
}
