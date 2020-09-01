package A20200814_bili第二季.code;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A40_SynchronousQueue {
    public static void main(String[] args) {
        BlockingQueue<String> strings = new SynchronousQueue<String>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                strings.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                strings.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                strings.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }, "AAA").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + strings.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + strings.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t" + strings.take());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}

