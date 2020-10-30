package A20200907_bill尚硅谷_设计模式.A3_原型模式.浅拷贝;

import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 9:34
 * @Description :
 */
public class OldEntity implements Cloneable {

    private InnerEntity innerEntity;
    private int id;
    private List<String> array;


    @Override
    public String toString() {
        return "OldEntity{" +
                "innerEntity=" + innerEntity +
                ", id=" + id +
                ", array=" + array +
                '}';
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getArray() {
        return array;
    }

    public void setArray(List<String> array) {
        this.array = array;
    }

    public InnerEntity getInnerEntity() {
        return innerEntity;
    }

    public void setInnerEntity(InnerEntity innerEntity) {
        this.innerEntity = innerEntity;
    }
}
