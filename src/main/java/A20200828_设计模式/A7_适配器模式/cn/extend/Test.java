package A20200828_设计模式.A7_适配器模式.cn.extend;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 14:05
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        Target adapter220VTo110V = new Adapter220VTo110V();
        MaChine maChine = new MaChine();
        maChine.work(adapter220VTo110V);
        Target adapter220VTo440V = new Adapter220VTo440V();
        MaChine maChine2 = new MaChine();
        maChine2.work(adapter220VTo440V);

    }
}
