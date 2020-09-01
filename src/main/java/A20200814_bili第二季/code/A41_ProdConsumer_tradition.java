package A20200814_bili第二季.code;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 生产者-消费者模式 -传统版
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加一，一个减一，5轮这样的操作
 * 1.线程 操作（方法） 资源类
 * 2.判断 干活 通知
 * 3.防止虚假唤醒机制-多线程判断用while
 */
public class A41_ProdConsumer_tradition {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    shareData.increment();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }, "AAA").start();
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    shareData.decrement();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }, "BBB").start();
    }
}

/**
 * 资源类
 */
class ShareData {

    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            //1.判断使用while防止虚假唤醒，可以使用if测试
            while (num != 0) {
                //不是0，等待
                condition.await();
            }
            //2.干活
            //是0，则++
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            //3.通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            //1.判断
            while (num == 0) {
                //等待
                condition.await();
            }
            //2.干活
            //是0，则++
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            //3.通知唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

