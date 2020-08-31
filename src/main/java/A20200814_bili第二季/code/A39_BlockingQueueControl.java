package A20200814_bili第二季.code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A39_BlockingQueueControl {
    public static void main(String[] args) {
        //创建包含三个位置的队列
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(3);
        try{
            System.err.println(strings.offer("a", 2L, TimeUnit.SECONDS));
            System.err.println(strings.offer("b", 2L, TimeUnit.SECONDS));
            System.err.println(strings.offer("c", 2L, TimeUnit.SECONDS));
            //阻塞的时候，等两秒过时不候
            System.err.println(strings.offer("d", 2L, TimeUnit.SECONDS));

            System.err.println(strings.poll(2L, TimeUnit.SECONDS));
        }catch(Exception e){
            e.printStackTrace();
        }
//        zuse(strings);
    }

    private static void zuse(BlockingQueue<String> strings) {
        try {
            strings.put("a");
            strings.put("b");
            strings.put("c");
            //插不进去就会阻塞，导致程序不向下运行，所以先注释掉
//            strings.put("d");
            System.err.println("================================");
            System.err.println(strings.take());
            System.err.println(strings.take());
            System.err.println(strings.take());
            System.err.println(strings.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

