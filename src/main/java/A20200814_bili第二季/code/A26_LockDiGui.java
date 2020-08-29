package A20200814_bili第二季.code;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 锁中套锁
 */
public class A26_LockDiGui {

    //外层获得锁
    synchronized void setA() throws Exception {
        Thread.sleep(1000);
        //内层依旧还是这个锁
        setB();
    }

    synchronized void setB() throws Exception {
        Thread.sleep(1000);
    }
}
