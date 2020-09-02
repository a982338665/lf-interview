package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * VM options:-XX:+PrintGCDetails
 *
 * 核数	4
 * java虚拟机中的内存总量	184.0MB
 * java虚拟机试图使用的最大内存量	2706.0MB
 */
public class A60_JVM {
    public static void main(String[] args) throws InterruptedException {
        System.err.println("核数\t"+Runtime.getRuntime().availableProcessors());
        System.err.println("java虚拟机中的内存总量\t"+(Runtime.getRuntime().totalMemory()/(double)1024/1024)+"MB");
        System.err.println("java虚拟机试图使用的最大内存量\t"+(Runtime.getRuntime().maxMemory()/(double)1024/1024)+"MB");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

