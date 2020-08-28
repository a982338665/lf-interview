package A20200828_设计模式.A3_抽象工厂模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:13
 * @Description :
 */
public class Test {

    public static void main(String[] args) {
        AbstracFactory1 factory = FactoryProducer.getFactory("1");
        jiekou1 impl1 = factory.getJieko1("impl1");
        impl1.jiekou1();
    }
}
