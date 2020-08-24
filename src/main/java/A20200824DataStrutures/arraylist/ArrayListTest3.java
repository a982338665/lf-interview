package A20200824DataStrutures.arraylist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * create by lishengbo on 2018-07-04 15:46
 *
 * 关于迭代器遍历与fori遍历效率比较：-- 相差不多
 *
 */
public class ArrayListTest3 {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <100 ; i++) {
            list.add("a");
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i <list.size() ; i++) {
            System.out.println("fori遍历-->"+list.get(i));
        }
        long midd = System.currentTimeMillis();
        System.err.println("fori-----便利时间--"+(midd-start));
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println("iterator遍历-->"+iterator.next());
        }
        long end = System.currentTimeMillis();
        System.err.println("iterator遍历----便利时间--"+(end-midd));
    }
}
