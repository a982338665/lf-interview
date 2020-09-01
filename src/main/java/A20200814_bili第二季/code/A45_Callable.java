package A20200814_bili第二季.code;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 */
public class A45_Callable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        two();
        TimeUnit.SECONDS.sleep(3);
        System.err.println("---------------------------------------------------");
        one();

    }

    private static void two() throws InterruptedException, ExecutionException {
        //此处使用的是适配器模式
        //两个任务则取两次
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread2());
        Thread thread = new Thread(futureTask, "AA");
        Thread thread2 = new Thread(futureTask2, "BB");
        thread.start();
        //只算一次
        thread2.start();

        //当任务完成后，再取结果
        while (!futureTask.isDone()){

        }
        System.err.println("*********result************" + futureTask.get());
    }

    private static void one() throws InterruptedException, ExecutionException {
        //此处使用的是适配器模式
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        Thread thread = new Thread(futureTask, "AA");
        Thread thread2 = new Thread(futureTask, "AA");
        thread.start();
        //只算一次
        thread2.start();

        //当任务完成后，再取结果
        while (!futureTask.isDone()){

        }
        System.err.println("*********result************" + futureTask.get());
    }
}

class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t");
        System.err.println("*************************come in callable");
        return 1024;
    }
}


