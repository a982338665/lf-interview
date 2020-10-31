package A20200907_bill尚硅谷_设计模式.A6_桥接模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 16:06
 * @Description :
 */
public abstract class Phone {

    private Brand brand;

    public Phone(Brand brand) {
        super();
        this.brand = brand;
    }

    protected void call() {
        brand.call();
    }

}
