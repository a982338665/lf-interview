package A20200814_bili第二季.code;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 锁中套锁
 */
public class A27_LockDiGui_ReentrantLock {

    public static void main(String[] args) {
        Phone2 phone = new Phone2();
        new Thread(phone, "t1").start();
        new Thread(phone, "t2").start();
    }
}

//资源类
class Phone2 implements Runnable {

    //        lock.lock();与unlock必须成对出现，加几次锁几次
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        try {
            System.err.println(Thread.currentThread().getName() + "\t send sms!!!");
            set();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.err.println(Thread.currentThread().getName() + "\t send email!!!");
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}
