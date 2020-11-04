package A20200907_bill尚硅谷_设计模式.A7_装饰者模式.mine;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 17:05
 * @Description :
 */
public class EatImpl1 implements Eat {
    @Override
    public void des() {
        System.err.println("吃的食物01...");
    }

    @Override
    public void cost() {
        System.err.println("吃的食物01...花费：");
    }
}
