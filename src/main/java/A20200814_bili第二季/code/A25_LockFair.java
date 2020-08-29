package A20200814_bili第二季.code;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 值传递及引用传递问题
 */
public class A25_LockFair {
    volatile int n = 0;
    public void add(){
        n++;
    }

    public static void main(String[] args) {
        //NonfairSync - > 默认非公平锁
        // public ReentrantLock() {sync = new NonfairSync();}
        ReentrantLock reentrantLock = new ReentrantLock();

        //fair == true FairSync() 公平锁
        //public ReentrantLock(boolean fair) {sync = fair ? new FairSync() : new NonfairSync();}
        ReentrantLock fairLock = new ReentrantLock(true);
    }
}
