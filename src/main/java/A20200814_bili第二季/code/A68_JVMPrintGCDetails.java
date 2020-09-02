package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails
 * 1.设置堆最大为 10m
 * 2.在main函数中申请50m内存的结果
 * 3.执行结果
 *      [GC (Allocation Failure) [PSYoungGen: 1550K->488K(2560K)] 1550K->668K(9728K), 0.0009466 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *      //++++++++++++++++++++++++++++++++++++++++++++++
 *      [GC 表示在新生代
 *      (Allocation Failure) 内存申请失败
 *      [PSYoungGen: GC类型-新生代
 *      1550K：gc前新生代的内存占用
 *      ->488K：gc后新生代的内存占用
 *      (2560K)]：新生代总共大小
 *      1550K：gc前jvm堆内存占用
 *      ->668K:：gc后jvm堆内存使用
 *      (9728K)：jvm堆总大小
 *      0.0009466 secs]：gc耗时
 *      [Times: user=0.00：gc用户耗时
 *      sys=0.00,：gc系统耗时
 *      real=0.00 secs]：gc实际耗时
 *      //++++++++++++++++++++++++++++++++++++++++++++++
 *      [GC (Allocation Failure) [PSYoungGen: 488K->504K(2560K)] 668K->684K(9728K), 0.0004350 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *      [Full GC (Allocation Failure) [PSYoungGen: 504K->0K(2560K)] [ParOldGen: 180K->609K(7168K)] 684K->609K(9728K), [Metaspace: 3126K->3126K(1056768K)], 0.0058359 secs] [Times: user=0.05 sys=0.00, real=0.01 secs]
 *      //++++++++++++++++++++++++++++++++++++++++++++++
 *      [Full GC 表示老生代，同理
 *      //++++++++++++++++++++++++++++++++++++++++++++++
 *      [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 609K->609K(9728K), 0.0003462 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *      [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 609K->591K(7168K)] 609K->591K(9728K), [Metaspace: 3126K->3126K(1056768K)], 0.0056701 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 *      Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 *      	at A20200814_bili第二季.code.A68_JVMPrintGCDetails.main(A68_JVMPrintGCDetails.java:19)
 *      Heap
 *       PSYoungGen      total 2560K, used 104K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
 *        eden space 2048K, 5% used [0x00000000ffd00000,0x00000000ffd1a208,0x00000000fff00000)
 *        from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 *        to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 *       ParOldGen       total 7168K, used 591K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
 *        object space 7168K, 8% used [0x00000000ff600000,0x00000000ff693d38,0x00000000ffd00000)
 *       Metaspace       used 3210K, capacity 4496K, committed 4864K, reserved 1056768K
 *        class space    used 345K, capacity 388K, committed 512K, reserved 1048576K
 */
public class A68_JVMPrintGCDetails {
    public static void main(String[] args) throws InterruptedException {
        //分配50M空间
        byte[] bytes = new byte[50 * 1024 * 1024];
    }
}

