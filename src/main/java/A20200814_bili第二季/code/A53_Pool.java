package A20200814_bili第二季.code;


import java.util.concurrent.*;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 第四种获取java多线程的方法
 */
public class A53_Pool {
    public static void main(String[] args) {

        ExecutorService executorService = new ThreadPoolExecutor
                (2, 5, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),
//                        new ThreadPoolExecutor.AbortPolicy());//5+3后超出的抛异常
                        new ThreadPoolExecutor.CallerRunsPolicy());//5+3后超出的退回原任务main
//                        new ThreadPoolExecutor.DiscardOldestPolicy());//5+3后的占用旧位置抛弃旧任务 ==8
//                        new ThreadPoolExecutor.DiscardPolicy());//5+3后的抛弃 ==8
        //模拟十个用户办理业务，每个用户就是一个来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                final int tmp = i;
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务,服务号："+tmp);
//                    try {
//                        TimeUnit.SECONDS.sleep(3);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}

