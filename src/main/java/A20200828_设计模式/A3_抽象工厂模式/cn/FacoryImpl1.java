package A20200828_设计模式.A3_抽象工厂模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:06
 * @Description :
 */
public class FacoryImpl1 extends AbstracFactory1 {
    @Override
    public jiekou1 getJieko1(String name) {
        if (name.equals("impl1")) {
            return new jiekou1impl1();
        } else if (name.equals("impl2")) {
            return new jiekou1impl2();
        }
        return null;
    }

    @Override
    public jiekou2 getJieko2(String name) {
        if (name.equals("impl1")) {
            return new jiekou2impl1();
        } else if (name.equals("impl2")) {
            return new jiekou2impl2();
        }
        return null;
    }
}
