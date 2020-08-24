package A20200824DataStrutures.arraylist;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lishengbo on 2018-07-04 14:14
 *
 * 以下分析均基于JDK1.8
 * 1.ArrayList底层的数据结构为数组，元素类型为Object ,Arraylist可存放null元素
 * ------------------
 * ·ArrayList的继承关系：
 *      public class ArrayList<E> extends AbstractList<E>
 *      implements List<E>, RandomAccess, Cloneable, java.io.Serializable
 *      ArrayList继承AbstractList抽象父类，
 *      实现了List接口（规定了List的操作规范）、
 *      RandomAccess（可随机访问）、Cloneable（可拷贝）、
 *      Serializable（可序列化）。
 * ·基本属性：
 *       // 版本号
         private static final long serialVersionUID = 8683452581122892189L;
         // 缺省容量
         private static final int DEFAULT_CAPACITY = 10;
         // 空对象数组
         private static final Object[] EMPTY_ELEMENTDATA = {};
         // 缺省空对象数组
         private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
         // 元素数组==核心内容存放实际元素，标记为transient，在序列化时，此字段忽略
         transient Object[] elementData;
         // 实际元素大小，默认为0
         private int size;
         // 最大数组容量
         private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
 *  ·构造函数：三种
 * ·综合以上：
 *      1.ArrayList插入有移位操作，故插入效率低
 *      2.可以按索引读取， 所以读取效率高
 *      3.底层数组-插入有序-可重复-可插入null
 *      4.jdk1.6 new对象时，初始容量=10 copyof扩容为原来的1.5倍+1,即16
 *        jdk1.7 new对象时，初始容量=0 ,真正添加时分配容量,copyof扩容为原来的1.5倍
 */
public class ArrayListTest {

    public static void main(String[] args) {
        //当未指定初始化大小时，会给elementData赋值为空集合
        //当执行 list.add("23"),elementData的大小变为10，之后再返回到add函数，把23放在elementData[0]中。
        List<Object> list=new ArrayList<Object>();
        list.add("23");
        list.add("25");
        list.add("26");
        list.add(null);
        list.add(null);
        System.out.println("ArrayList插入null后的集合大小：-->"+list.size());
        //设定指定下标索引的元素值。　===================
        list.set(1,2);
        //从头开始查找与指定元素相等的元素，注意，是可以查找null元素的，意味着ArrayList中可以存放null元素的。
        //与此函数对应的lastIndexOf，表示从尾部开始查找,返回下标，若不存在返回-1
        int i = list.indexOf("216");
        System.out.println(i);
        //get(int)会检查是否下标越界
        System.out.println(list.get(2));
        //remove(int)remove函数用户移除指定下标的元素，此时会把指定下标到数组末尾的元素向前移动一个单位，
        //并且会把数组最后一个元素设置为null，这样是为了方便之后将整个数组不被使用时，会被GC，可以作为小的技巧使用。
        list.remove("23");
        System.out.println("list移去下标为0的元素，此时取下标为0的元素值为："+list.get(0));


        //指定元素数组大小为18，指定elementData数组的大小，不允许初始化大小小于0，否则抛出异常。
        List<Object> list2=new ArrayList<Object>(18);
        //当传递的参数为集合类型时，会把集合类型转化为数组类型，并赋值给elementData==等同于数组复制
        List<Object> list3=new ArrayList<Object>(list);



        for (Object listt:list
             ) {
            System.out.println("无参构造ArrayList集合：--->"+listt);
        }
        for (Object listt:list3
             ) {
            System.out.println("ArrayList集合：--->"+listt);
        }


    }
}


































