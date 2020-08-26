package A20200824_DataStrutures.linklist;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * create by lishengbo on 2018-07-04 15:56
 *  以下分析均基于JDK1.8
 *  -----------------
 *  LinkedList底层使用的双向链表结构，有一个头结点和一个尾结点，双向链表意味着我们可以从头开始正向遍历，
 *  或者是从尾开始逆向遍历，并且可以针对头部和尾部进行相应的操作
 *  ·类的继承关系：
 *      public class LinkedList<E>extends
 *      AbstractSequentialList<E>implements
 *      List<E>,
 *      Deque<E>,   -Deque接口表示是一个双端队列，故基于双端队列的操作在LinkedList中全部有效
 *      Cloneable,
 *      java.io.Serializable
 *  ·类的内部类：-->内部类Node就是实际的结点，用于存放实际元素的地方。
 *      private static class Node<E> {
             E item; // 数据域
             Node<E> next; // 后继
             Node<E> prev; // 前驱

             // 构造函数，赋值前驱后继
             Node(Node<E> prev, E element, Node<E> next) {
             this.item = element;
             this.next = next;
             this.prev = prev;
             }
             }
 *  ·类的属性：--transient关键字修饰,序列化时该域是不会序列化的。
 *       // 实际元素个数
         transient int size = 0;
         // 头结点
         transient Node<E> first;
         // 尾结点
         transient Node<E> last;
 *  ·类的构造函数：
 *  ·优点：
 *      LinkedList是采用链表的方式来实现List接口的,它本身有自己特定的方法，
 *      如: addFirst(),addLast(),getFirst(),removeFirst()等. 由于是采用链表实现的,
 *      因此在进行insert和remove动作时在效率上要比ArrayList要好得多!适合用来实现Stack(堆栈)与Queue(队列),
 *      前者先进后出，后者是先进先出
 *      1.ArrayList插入或删除均会移动指针会耗费时间--所以效率慢
 *      2.LinkList插入或删除只需要改变前后节点指向即可，所以效率快
 *  关于查询效率：
 *      LinkedList是双向链表，注意是链表。要查询只能头结点开始逐步查询，没有什么给出下标即可直接查询的便利，需要遍历。
 *      由于LinkedList查询只能从头结点开始逐步查询的，可以使用 iterator 的方式，就不用每次都从头结点开始访问，
 *      因为它会缓存当前结点的前后结点。实测查询效率与ArrayList没有太大差别
 *  缺点：没有索引的便利，只能通过遍历找到想要的值
 */
public class LinkListTest {

    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("12--");
        strings.add("13--");
        strings.add("14--");
        strings.add("15--");
        //无参构造函数
        LinkedList<String> strings1 = new LinkedList<String>();
        //add函数用于向LinkedList中添加一个元素，并且添加到链表尾部。具体添加到尾部的逻辑是由linkLast函数完成的。
        //添加过程如下：
        //        void linkLast(E e) {
        //            // 保存尾结点，l为final类型，不可更改
        //            final Node<E> l = last;
        //            // 新生成结点的前驱为l,后继为null
        //            final Node<E> newNode = new Node<>(l, e, null);
        //            // 重新赋值尾结点
        //            last = newNode;
        //            if (l == null) // 尾结点为空
        //                first = newNode; // 赋值头结点
        //            else // 尾结点不为空
        //                l.next = newNode; // 尾结点的后继为新生成的结点
        //            // 大小加1
        //            size++;
        //            // 结构性修改加1
        //            modCount++;
        //        }
        //如下先添加5后添加6的过程：null 5 6 null
        //
        strings1.add("5");
        strings1.add("6");


        //在链表最后插入集合++++++++++++++++++++
        //实际也是调用addAll(1,strings)
        //addAll-内部也是将传过来的集合进行转换，转换为数组，然后遍历插入
        //addAll()中转为数组进行添加是为了提高多线程下 参数Collection 集合的访问效率
        strings1.addAll(strings);

        for (String s:strings1
             ) {
            System.out.println(s+"============");
        }
        //从下标为几的位置开始插入集合++++++++++++
        strings1.addAll(1,strings);
        for (String s:strings1
                ) {
            System.out.println(s);
        }





        //集合参数构造方法-->：会调用无参构造函数，并且会把集合中所有的元素添加到LinkedList中
        // // 调用无参构造函数
        //this();
        // 添加集合中所有的元素
        //addAll(c);
        LinkedList<String> strings2 = new LinkedList<String>();




    }






































}
