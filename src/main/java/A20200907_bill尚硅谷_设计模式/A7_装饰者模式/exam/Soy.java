package A20200907_bill尚硅谷_设计模式.A7_装饰者模式.exam;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 17:09
 * @Description :
 */
public class Soy extends Decorator {
    public Soy(Drink obj) {
        super(obj);
        setDes(" 豆浆	");
        setPrice(1.5f);
    }
}
