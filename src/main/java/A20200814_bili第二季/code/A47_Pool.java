package A20200814_bili第二季.code;


import java.util.concurrent.*;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 第四种获取java多线程的方法
 */
public class A47_Pool {
    public static void main(String[] args) {

        //一池5个线程
        ExecutorService executorService1 = Executors.newFixedThreadPool(5);
        //一池一线程
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        //一池N线程
        ExecutorService executorService = Executors.newCachedThreadPool();
        //自定义一个
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor
                (2, 5, 100L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        //模拟十个用户办理业务，每个用户就是一个来自外部的请求线程
        try {
//            executorService.submit()
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}

