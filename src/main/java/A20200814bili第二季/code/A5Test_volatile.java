package A20200814bili第二季.code;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/19 10:55
 * @Description : 可见性的代码验证
 */
public class A5Test_volatile {

    /**
     * 线程操作资源类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mydata5 mydata = new Mydata5();//资源类，初始化共享变量num
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
            }, "线程"+i).start();
        }
        //需要等待20个线程都计算完毕，使用main函数打印结果
        //        try{
        //            TimeUnit.SECONDS.sleep(5);
        //            System.err.println(mydata.num);
        //        }catch(Exception e){
        //
        //        }
        //第二种方式,若活动线程数大于2
        while(Thread.activeCount()>2){
            //礼让线程
            Thread.yield();
        }
        System.err.println(mydata.num);

    }

}

/**
 * 验证volatile不保证原子性：
 * 1.数值少于20000，出现了数据丢失的情况
 */
class Mydata5 {//Mydata5.java => Mydata5.class => JVM字节码

    volatile int num = 0;

    public void init() {
        this.num++;
    }
}
