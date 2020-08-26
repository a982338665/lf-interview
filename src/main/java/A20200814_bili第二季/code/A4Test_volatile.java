package A20200814_bili第二季.code;

import java.util.concurrent.TimeUnit;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/19 10:55
 * @Description : 可见性的代码验证
 */
public class A4Test_volatile {

    /**
     * 线程操作资源类
     * @param args
     */
    public static void main(String[] args) {
        Mydata mydata = new Mydata();//资源类，初始化共享变量num
        //线程一
        new Thread(()->{
            System.err.println(Thread.currentThread().getName()+"\t come in");
            try{
                //暂停三秒
                TimeUnit.SECONDS.sleep(3);
                //修改共享变量
                mydata.init();
                System.err.println(Thread.currentThread().getName()+"\t update to "+mydata.num);
            }catch(Exception e){
                e.printStackTrace();
            }
        },"线程一：").start();

        //主线程main
        while(mydata.num == 0){

        }
        System.err.println(Thread.currentThread().getName()+" is over !");

    }

}

/**
 * 验证volatile的可见性：
 *  1.int num =0；变量不被volatile修饰,不能保证可见性，所以以上代码中主线程是不会停止的，一直会处于循环内
 *  2.int volatile num =0 保证可见性，被修改后会及时刷新回主内存，所以以上代码最后会停止运行
 */
class Mydata{


//    int num = 0;
    volatile int num = 0;

    public void init(){
        this.num = 60;
    }
}
