package A20200814_bili第二季.code;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 手写自旋锁 - 通过反复的循环比较保值安全性，锁
 * 通过CAS操作完成自旋锁，A线程先进来调用mylock方法自己持有锁5秒钟，B随后进来发现当前线程持有锁，不是null
 * 所以只能通过自旋等待，直到A释放锁后B再抢到
 */
public class A29_SpinLock {

    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();


    public void mylock(){
        Thread thread = Thread.currentThread();
        System.err.println(thread.getName()+"\t come in ..." );
        //期望值为null，真实值为null，则吧thread扔进去
        while(!atomicReference.compareAndSet(null, thread)){

        }
//        boolean b = atomicReference.compareAndSet(null, thread);
//        //若 thread替换了原来的值，则返回true，没替换上则为false
//        //!b == !false 时，继续循环比较替换，直到成功为止
//        while (!b){
//
//        }
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.err.println(thread.getName()+"\t unlock ..." );
    }

    public static void main(String[] args) throws InterruptedException {

        A29_SpinLock a29_spinLock = new A29_SpinLock();

        new Thread(() -> {
            try {
                //占用锁
                a29_spinLock.mylock();
                TimeUnit.SECONDS.sleep(5);
                a29_spinLock.myUnlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();
        //main线程保值AA线程一定执行了
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                a29_spinLock.mylock();
                a29_spinLock.myUnlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }
}

