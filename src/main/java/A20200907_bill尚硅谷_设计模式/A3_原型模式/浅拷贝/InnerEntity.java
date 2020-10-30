package A20200907_bill尚硅谷_设计模式.A3_原型模式.浅拷贝;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 9:49
 * @Description :
 */
public class InnerEntity {

    private String name;

    public InnerEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InnerEntity{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
