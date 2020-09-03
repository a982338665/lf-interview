package A20200814_bili第二季.code;


import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 * <p>
 * MaxDirectMemorySize表示直接内存最大给5M
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 */
public class A85_OutOfMemoryError_native {

    public static void main(String[] args) {
        //一个线程不能两次start，会报错 【线程状态异常】
//        tuigu();
        while (true){
            new Thread(()->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private static void tuigu() {
        //throw new IllegalThreadStateException();
        Thread thread = new Thread();
        thread.start();
        thread.start();
    }
}

