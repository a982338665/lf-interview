package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * 1.不加幸存区比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC
 *     def new generation   total 3072K, used 1703K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
     *   eden space 2752K,  61% used [0x00000000ff600000, 0x00000000ff7a9e18, 0x00000000ff8b0000)
     *   from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
     *   to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
     *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
 *      默认 约为8:1:1
 * 2.添加加幸存区比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:SurvivorRatio=4
 *  def new generation   total 2880K, used 1694K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
 *   eden space 2368K,  71% used [0x00000000ff600000, 0x00000000ff7a7850, 0x00000000ff850000)
 *   from space 512K,   0% used [0x00000000ff850000, 0x00000000ff850000, 0x00000000ff8d0000)
 *   to   space 512K,   0% used [0x00000000ff8d0000, 0x00000000ff8d0000, 0x00000000ff950000)
 *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
 *      约为4:1:1
 */
public class A69_JVMSurvivorRatio {
    public static void main(String[] args) throws InterruptedException {
        System.err.println("hello");
    }
}

