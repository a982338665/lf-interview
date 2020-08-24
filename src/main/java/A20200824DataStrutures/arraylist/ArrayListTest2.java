package A20200824DataStrutures.arraylist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * create by lishengbo on 2018-07-04 15:21
 *
 * ArrayList遍历时移除元素的问题及陷阱：
 * 正确方式：
 *  1.倒叙fori
 *  2.迭代器遍历-迭代器-remove
 * 错误方式：
 *  1.正序fori
 *  2.增强for(迭代器遍历-list-remove)
 */
public class ArrayListTest2 {

    public static void main(String[] args)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("c");
        remove(list);

        for (String s : list)
        {
            System.out.println("element : " + s);
        }
    }

    /**
     * 利用此种遍历删除元素，由于指针再删除第一个元素后会进行前移，所以第二个“b”的字符串没有被遍历到，故没被删掉。
     * 优化方案:倒叙删除遍历--不会涉及到元素移动问题
     * @param list
     */
    /*public static void remove(ArrayList<String> list)
    {
        //正序移除
        *//*for (int i = 0; i < list.size(); i++)
        {
            String s = list.get(i);
            if (s.equals("b"))
            {
                list.remove(s);
            }
        }*//*
        //倒叙移除
        for (int i = list.size() - 1; i >= 0; i--)
        {
            String s = list.get(i);
            if (s.equals("b"))
            {
                list.remove(s);
            }
        }
    }*/

    /**
     * for-each写法会报出著名的并发修改异常：java.util.ConcurrentModificationException
     * 增强for循环的原理使用的是迭代器：即是对实际的Iterable、hasNext、next方法的简写
     * 这里会做迭代器内部修改次数检查，因为上面的remove(Object)方法修改了modCount的值，所以才会报出并发修改异常。
     * 要避免这种情况的出现则在使用迭代器迭代时（显示或for-each的隐式）不要使用ArrayList的remove，改为用Iterator的remove即可。
     * @param list
     */
    /*public static void remove(ArrayList<String> list)
    {
        for (String s : list)
        {
            if (s.equals("b"))
            {
                list.remove(s);
            }
        }
    }*/

    /**
     * 等同于增强for循环
     * @param list
     */
    public static void remove(ArrayList<String> list)
    {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("b")){
                list.remove(next);
            }
        }
    }

    /**
     * 迭代器解决删除：
     * 要避免这种情况的出现则在使用迭代器迭代时（显示或for-each的隐式）不要使用ArrayList的remove，改为用Iterator的remove即可
     * @param list
     */
    /*public static void remove(ArrayList<String> list)
    {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("b")){
                iterator.remove();
            }
        }
    }*/


}
