package A20200828_设计模式.A7_适配器模式.cn.impl;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 14:05
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        Target adapterAll = new AdapterAll(new Chatou220V());
        MaChine2 maChine = new MaChine2();
        maChine.work(adapterAll);
    }
}
