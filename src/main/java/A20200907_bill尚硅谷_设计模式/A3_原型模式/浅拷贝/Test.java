package A20200907_bill尚硅谷_设计模式.A3_原型模式.浅拷贝;

import java.util.Arrays;
import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 9:36
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        OldEntity oldEntity = new OldEntity();
        oldEntity.setId(1);
        oldEntity.setArray(Arrays.asList("2","3"));
        oldEntity.setInnerEntity(new InnerEntity("li"));

        //克隆创建的新对象
        OldEntity clone = (OldEntity) oldEntity.clone();
        System.err.println(oldEntity);
        System.err.println(clone);
        System.err.println(clone==oldEntity);
        System.err.println(oldEntity.hashCode());
        System.err.println(clone.hashCode());

        //修改新对象的引用数据类型:包含集合数组及其他对象
        //对于集合不能再新增元素，只能改值
        //对于对象可以修改其属性
        List<String> array = clone.getArray();
//        array.add("1");Exception in thread "main" java.lang.UnsupportedOperationException
        array.set(0, "25");
        clone.setId(4);
        clone.getInnerEntity().setName("long");
        //查看新对象引用数据类型是否变化
        System.err.println(clone);
        System.err.println(oldEntity);

    }
}
