package A20200814_bili第二季.code;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 生产消费者 - 阻塞队列
 */
public class A44_ProdConsumer {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strings = new ArrayBlockingQueue<>(3);
        MyResource myResource = new MyResource(strings);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动...");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动...");
            try {
                myResource.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();
        //5秒后暂停
        TimeUnit.SECONDS.sleep(5);
        myResource.stop();
    }
}


class MyResource {

    //默认开启进行生产和消费 - 保证内存可见性， 不保证原子性
    private volatile boolean FLAG = true;
    //保证原子性，进行生产和消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    //一般在构造注入时，传入接口，为了保证适配，强烈推荐
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.err.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String data = null;
        boolean retValue;
        while (FLAG) {
            //data提出去，防止指针创建过多
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t老板叫停。。。。停止生产");
    }

    public void myConsumer() throws InterruptedException {
        String data = null;
        String retValue;
        while (FLAG) {
            retValue = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == retValue || retValue.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t移出队列超过两秒");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费队列成功：" + retValue);
        }
    }


    public void stop(){
        this.FLAG = false;
    }
}

