package A20200828_设计模式.A6_原型模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 11:17
 * @Description :
 */
public class Entity2 extends BaseEntity{

    public Entity2() {
        System.err.println("entity2...instance!!!");
    }

    @Override
    void print() {
        System.err.println("entity2=====");
    }
}
