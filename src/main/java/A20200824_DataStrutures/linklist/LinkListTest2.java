package A20200824_DataStrutures.linklist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * create by lishengbo on 2018-07-04 17:06
 *
 * ArrayList查询效率与LinkList查询效率比较:
 * 结果比较：
 *   LinkedList add cost time :35
     LinkedList get fori--> cost time :2886
     LinkedList get foreach--> cost time :31
     LinkedList get iterator--> cost time :6
     LinkedList remove cost time :7
     --------------------------------------------------------
     ArrayList add cost time :294
     ArrayList get fori--> cost time :1
     ArrayList get foreach--> cost time :5
     ArrayList get iterator--> cost time :1
     ArrayList remove cost time :530
     ——————————————————————————————————
    结论：
        1.ArrayList读取数据效率：fori=iterator > foreach
        2.LinkList读取数据效率：iterator>foreach>fori(切不可使用fori读取LinkList)
        3.插入删除效率比较：LinkList>ArrayList
        4.综合选用：
            ·读：ArrayList
            ·写：LinkList

 *
 *
 */
public class LinkListTest2 {

    public static void main(String[] args) {
        testLinkedList();
        testArrayList();
    }

    private static void testLinkedList(){
        LinkedList<String> list=new LinkedList<String>();
        int maxTestCount=50000;

        //测试添加时间
        long start =System.currentTimeMillis();

        for(int i =0;i<maxTestCount;i++){
            list.add(0,String.valueOf(i));
        }

        long end =System.currentTimeMillis();

        System.out.println("LinkedList add cost time :"+(end-start));

        //测试查询时间===普通for循环
        start =System.currentTimeMillis();

        for(int i =0;i<maxTestCount;i++){
            list.get(i);
        }

        end =System.currentTimeMillis();

        System.out.println("LinkedList get fori--> cost time :"+(end-start));
        //测试查询时间===增强for循环
        start =System.currentTimeMillis();
        for (String s:list
             ) {
            s=s+"";
        }
        end =System.currentTimeMillis();
        System.out.println("LinkedList get foreach--> cost time :"+(end-start));
        //测试查询时间===迭代器循环
        start =System.currentTimeMillis();
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String s = it.next();
        }
        end =System.currentTimeMillis();
        System.out.println("LinkedList get iterator--> cost time :"+(end-start));


        //测试删除时间
        start =System.currentTimeMillis();

        for(int i =maxTestCount;i>0;i--){
            list.remove(0);
        }

        end =System.currentTimeMillis();

        System.out.println("LinkedList remove cost time :"+(end-start));
        System.out.println("--------------------------------------------------------");

    }


    private static void testArrayList(){
        ArrayList<String> list=new ArrayList<String>();
        int maxTestCount=50000;

        //测试添加
        long start =System.currentTimeMillis();

        for(int i =0;i<maxTestCount;i++){
            list.add(0,String.valueOf(i));
        }

        long end =System.currentTimeMillis();

        System.out.println("ArrayList add cost time :"+(end-start));

        //测试查询
        start =System.currentTimeMillis();

        for(int i =0;i<maxTestCount;i++){
            list.get(i);
        }

        end =System.currentTimeMillis();

        System.out.println("ArrayList get fori--> cost time :"+(end-start));
        //测试查询时间===增强for循环
        start =System.currentTimeMillis();
        for (String s:list
                ) {
            s=s+"";
        }
        end =System.currentTimeMillis();
        System.out.println("ArrayList get foreach--> cost time :"+(end-start));
        //测试查询时间===迭代器循环
        start =System.currentTimeMillis();
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String s = it.next();
        }
        end =System.currentTimeMillis();
        System.out.println("ArrayList get iterator--> cost time :"+(end-start));

        //测试查询
        start =System.currentTimeMillis();

        for(int i =maxTestCount;i>0;i--){
            list.remove(0);
        }

        end =System.currentTimeMillis();

        System.out.println("ArrayList remove cost time :"+(end-start));

    }
}
