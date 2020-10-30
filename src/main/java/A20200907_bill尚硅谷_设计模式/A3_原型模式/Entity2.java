package A20200907_bill尚硅谷_设计模式.A3_原型模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 11:17
 * @Description :
 */
public class Entity2 extends BaseEntity {

    public Entity2() {
        System.err.println("entity2...instance!!!");
    }

    @Override
    void print() {
        System.err.println("entity2=====");
    }
}
