package A20200814bili第二季.code;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

class User2 {
    String userName;
    int age;

    public User2(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}

/**
 * cas:compareAndSet
 */
public class A18_AtomicReference_01 {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //初始化值为100，初始化版本号为1
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.err.println("以下为ABA问题的产生=========================================================================");
        new Thread(() -> {
            //对于无版本号的原子引用类，只关心开始和结尾，仍为100，即可，中间的操作可忽略不计
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();
        new Thread(() -> {
            try{
                //t2暂停1秒，保证t1线程完成了一次ABA操作
                TimeUnit.SECONDS.sleep(1);
                //所以以下结果为true
                System.err.println(atomicReference.compareAndSet(100, 2019)+"\t"+atomicReference.get());
            }catch(Exception e){

            }
        }, "t2").start();
        try{
            //暂停2秒，保证以上任务都正常完成
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){

        }
        System.err.println("以下为ABA问题的解决=========================================================================");
        new Thread(() -> {
            //获取到初始版本号
            int stamp = atomicStampedReference.getStamp();
            System.err.println(Thread.currentThread().getName()+"\t第一次版本号：\t"+stamp);
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception e){
            }
            //若为100改为101，若为初始版本号，则改为版本号+1
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.err.println(Thread.currentThread().getName()+"\t第二次版本号：\t"+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
            System.err.println(Thread.currentThread().getName()+"\t第三次版本号：\t"+atomicStampedReference.getStamp());
        }, "t3").start();
        new Thread(() -> {
            //获取到初始版本号
            int stamp = atomicStampedReference.getStamp();
            System.err.println(Thread.currentThread().getName()+"\t第一次版本号：\t"+stamp);
            try{
                TimeUnit.SECONDS.sleep(3);
            }catch(Exception e){

            }
            boolean b = atomicStampedReference.compareAndSet(100, 101, stamp, stamp + 1);
            System.err.println(Thread.currentThread().getName()+"\t修改成功否：\t"+b+"\t当前最新版本号："+atomicStampedReference.getStamp()+
                    "\t当前最新值："+atomicStampedReference.getReference());
        }, "t4").start();


    }
}
