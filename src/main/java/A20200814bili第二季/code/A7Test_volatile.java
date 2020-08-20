package A20200814bili第二季.code;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/19 10:55
 * @Description : 可见性的代码验证
 */
public class A7Test_volatile {

    /**
     * 线程操作资源类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mydata7 mydata = new Mydata7();//资源类，初始化共享变量num
        //开启20个线程
        for (int i = 1; i < 21; i++) {
            new Thread(() -> {
                System.err.println(Thread.currentThread().getName() + "\t come in");
                try {
                    //每个线程加1000次
                    for (int j = 0; j < 1000; j++) {
                        //修改共享变量
                        mydata.init();
                    }
                    System.err.println(Thread.currentThread().getName() + "\t update to " + mydata.num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "线程" + i).start();
        }
        while (Thread.activeCount() > 2) {
            //礼让线程
            Thread.yield();
        }
        System.err.println(mydata.num);

    }

}

/**
 * 验证AtomicInteger保证原子性：
 * 使用并发包（JUC）下的原子性数值对象
 */
class Mydata7 {//Mydata5.java => Mydata5.class => JVM字节码

    AtomicInteger num = new AtomicInteger();

    public void init() {
        num.getAndIncrement();
    }
}
