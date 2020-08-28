package A20200828_设计模式.A3_抽象工厂模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:11
 * @Description :
 */
public class FactoryProducer {

    public static AbstracFactory1 getFactory(String name) {
        if (name.equals("1")) {
            return new FacoryImpl1();
        } else if (name.equals("2")) {
            return new FacoryImpl2();
        }
        return null;
    }
}
