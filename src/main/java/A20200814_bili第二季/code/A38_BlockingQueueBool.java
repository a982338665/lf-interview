package A20200814_bili第二季.code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A38_BlockingQueueBool {
    public static void main(String[] args) {
        //创建包含三个位置的队列
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(3);
        System.err.println(strings.offer("a"));
        System.err.println(strings.offer("b"));
        System.err.println(strings.offer("c"));

        //插入失败-返回false
        System.err.println(strings.offer("x"));

        //检查队列空不空，第一个是多少
        System.err.println(strings.peek());

        //无参数时，先进先出，所以先移除的是 a
        System.err.println(strings.poll());
        System.err.println(strings.poll());
        System.err.println(strings.poll());

        //没有值了返回null
        System.err.println(strings.poll());

        //移除返回false
        System.err.println(strings.remove("d"));
        //检查-返回null
        System.err.println(strings.peek());

    }
}

