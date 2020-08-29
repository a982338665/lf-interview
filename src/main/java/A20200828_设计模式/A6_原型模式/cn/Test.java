package A20200828_设计模式.A6_原型模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 11:29
 * @Description :
 */
public class Test {

    public static void main(String[] args) {

        Entity1 entity1 = new Entity1();
        entity1.setId(1);
        Entity2 entity2 = new Entity2();
        entity2.setId(2);
        EntityCache.loadCache(entity1);
        EntityCache.loadCache(entity2);

        BaseEntity shape = EntityCache.getShape(1);
        BaseEntity shape2 = EntityCache.getShape(1);
        System.err.println(shape);
        System.err.println(shape2);
    }
}
