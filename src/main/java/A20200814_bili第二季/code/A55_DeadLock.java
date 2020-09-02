package A20200814_bili第二季.code;

class HoldLock implements Runnable{

    String lockA ;
    String lockB ;

    public HoldLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t持有" +lockA +"\t想要获得"+lockB);
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t持有" +lockB +"\t想要获得"+lockA);
            }
        }
    }
}
/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 第四种获取java多线程的方法
 */
public class A55_DeadLock {
    public static void main(String[] args) {
        String locka = "lockA";
        String lockb = "lockB";
        new Thread(new HoldLock(locka, lockb),"lockA").start();
        new Thread(new HoldLock(lockb, locka),"lockB").start();

    }
}

