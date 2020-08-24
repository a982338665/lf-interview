package A20200814bili第二季.code;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 集合类不安全演示
 */
public class A20_ContainerNotSafe {

    public static void main(String[] args) {
        //1.new ArrayList<>()本质上是new了一个数组，看源码可知构建了一个空list，且初始大小为10
        ArrayList<Integer> integers = new ArrayList<>();

        //2.初始化时是在add方法中，public boolean add(E e) {}
        //3.grow，每次扩容为上次的一半，10会扩为 10+10/2 = 15
        //4.扩容方式：Arrays.copyOf()
        integers.add(1);

        //5.线程安全还是不安全？不安全,方法add未加synchronized
        List<String> list2 = Arrays.asList("a", "b", "c");
        list2.stream().forEach(System.err::println);

        /*
        * 6.线程安全测试:多运行几次会出现异常信息 ：java.util.ConcurrentModificationException 并发修改异常
        *  异常信息：java.util.ConcurrentModificationException 并发修改异常
        *  导致原因：无锁
        *  解决方案：见下文
        *   1.Vector
        *   2.Collections.synchronizedList
        *   3.CopyOnWriteArrayList
        *  优化建议：
        *
        *  */
//        List<String> list = new ArrayList<>();
        //方案分析1：使用Vector，可以保证并发性，不会报错，因为add方法包含synchronized，但是由于同步原因并发性会急剧下降，而且Vector产生于jdk1.0,ArrayList产生于jdk1.2
        //      原来的结论：线程安全用Vector（不建议使用，面试题，禁止使用vector的其他处理方式？）
        //                  线程不安全用ArrayList
//        List<String> list = new Vector<>();
        //方案分析2：使用集合工具类保证线程安全-Collections.synchronizedList,synchronizedMap,synchronizedSet
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        //方案分析3：new CopyOnWriteArrayList<>();多并发环境下，变量大多都会加volatile
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.err.println(list);
            }, String.valueOf(i)).start();
        }
        list.get(0);

        //7.
    }
}
