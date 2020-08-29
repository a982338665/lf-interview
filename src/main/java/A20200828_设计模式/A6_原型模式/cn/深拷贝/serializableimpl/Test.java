package A20200828_设计模式.A6_原型模式.cn.深拷贝.serializableimpl;

import java.io.IOException;
import java.io.OptionalDataException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 10:21
 * @Description :
 * 利用串行化来做深复制
 * 把对象写到流里的过程是串行化（Serilization）过程，但是在Java程序师圈子里又非常形象地称为“冷冻”或者“腌咸菜（picking）”过程；
 * 而把对象从流中读出来的并行化（Deserialization）过程则叫做 “解冻”或者“回鲜(depicking)”过程。应当指出的是，写在流里的是对象的一个拷贝，
 * 而原对象仍然存在于JVM里面，因此“腌成咸菜”的只是对象的一个拷贝，Java咸菜还可以回鲜。
 * 在Java语言里深复制一个对象，常常可以先使对象实现Serializable接口，然后把对象（实际上只是对象的一个拷贝）写到一个流里（腌成咸菜），再从流里读出来（把咸菜回鲜），便可以重建对象。
 * 这样做的前提是对象以及对象内部所有引用到的对象都是可串行化的，否则，就需要仔细考察那些不可串行化的对象可否设成transient
 */
public class Test {
    public static void main(String[] args) throws OptionalDataException, ClassNotFoundException, IOException {
        Teacher t = new Teacher("tangliang", 30);
        Student s1 = new Student("zhangsan", 18, t);
        Student s2 = (Student) s1.deepClone();
        s2.t.setName( "tony");
        s2.t.setAge(40);
        System.out.println("name=" + s1.t.getName() + "," + "age=" + s1.t.getAge());// 学生1的老师不改变
    }
}
