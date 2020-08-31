package A20200814_bili第二季.code;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A34_Semaphore {
    public static void main(String[] args) {
        //多个线程抢多个资源
        Semaphore semaphore = new Semaphore(3);//模拟三个停车位
        for (int i = 1; i <= 6; i++) {//模拟六部机车
            new Thread(() -> {
                try {
                    //6车抢3个车位，抢到车位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t抢到车位！");
                    //停止三秒钟
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t停止三秒后，离开车位！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //最后释放车位
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}

