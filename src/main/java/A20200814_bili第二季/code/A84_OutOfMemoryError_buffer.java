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
public class A84_OutOfMemoryError_buffer {

    public static void main(String[] args) {
        //VM.maxDirectMemory是rt.jar中的内容
        //java中能用到的最大内存默认为 1/4 ，本机内存为8g，打印内容为2g，约为  1/4
        System.err.println("配置的MaxDirectMemory：\t" + (VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //分配os本地内存,-XX:MaxDirectMemorySize=5m 配置5M，实际使用6M
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}

