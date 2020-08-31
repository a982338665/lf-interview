package A20200814_bili第二季.code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A37_BlockingQueueException {
    public static void main(String[] args) {
        //创建包含三个位置的队列
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(3);
        System.err.println(strings.add("a"));
        System.err.println(strings.add("b"));
        System.err.println(strings.add("c"));

        //检查队列空不空，第一个是多少
        System.err.println(strings.element());

        try{
            //位置不够，队列已满报错 Exception in thread "main" java.lang.IllegalStateException: Queue full
            System.err.println(strings.add("c"));
        }catch(Exception e){
            e.printStackTrace();
        }

        //无参数时，先进先出，所以先移除的是 a
        System.err.println(strings.remove());
        System.err.println(strings.remove("b"));
        System.err.println(strings.remove("c"));

        //移除报错：Exception in thread "main" java.util.NoSuchElementException
        try{
            System.err.println(strings.remove());
        }catch(Exception e){
            e.printStackTrace();
        }
        //移除返回false
        System.err.println(strings.remove("d"));
        //检查队列空不空，第一个是多少,有显示第一个，无报错Exception in thread "main" java.util.NoSuchElementException
        System.err.println(strings.element());

    }
}

