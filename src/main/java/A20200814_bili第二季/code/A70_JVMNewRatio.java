package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * 1.不加比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC
 *    def new generation   total 3072K, used 1703K [0x00000000ff600000, 0x00000000ff950000, 0x00000000ff950000)
 *   eden space 2752K,  61% used [0x00000000ff600000, 0x00000000ff7a9e08, 0x00000000ff8b0000)
 *   from space 320K,   0% used [0x00000000ff8b0000, 0x00000000ff8b0000, 0x00000000ff900000)
 *   to   space 320K,   0% used [0x00000000ff900000, 0x00000000ff900000, 0x00000000ff950000)
 *  tenured generation   total 6848K, used 0K [0x00000000ff950000, 0x0000000100000000, 0x0000000100000000)
 *      约为 3072K/6848K 约等于1:2
 * 2.添加比例：VM options: -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=8
            def new generation   total 1856K, used 1658K [0x00000000ff600000, 0x00000000ff800000, 0x00000000ff800000)
            eden space 1664K,  99% used [0x00000000ff600000, 0x00000000ff79e940, 0x00000000ff7a0000)
            from space 192K,   0% used [0x00000000ff7a0000, 0x00000000ff7a0000, 0x00000000ff7d0000)
            to   space 192K,   0% used [0x00000000ff7d0000, 0x00000000ff7d0000, 0x00000000ff800000)
            tenured generation   total 8192K, used 0K [0x00000000ff800000, 0x0000000100000000, 0x0000000100000000)
 *      约为1856K/8192K 约等于1:4
 */
public class A70_JVMNewRatio {
    public static void main(String[] args) throws InterruptedException {
        System.err.println("hello");
    }
}

