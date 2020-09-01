package A20200814_bili第二季.code;


import java.util.concurrent.*;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 第四种获取java多线程的方法
 */
public class A49_Pool {
    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor
                (2, 5, 100L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        //模拟十个用户办理业务，每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 8; i++) {
                final int tmp = i;
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务,服务号："+tmp);
                    try {
                        TimeUnit.SECONDS.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}

