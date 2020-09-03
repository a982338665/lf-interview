package A20200814_bili第二季.code;


import java.util.ArrayList;
import java.util.List;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 * <p>
 *     MaxDirectMemorySize表示直接内存最大给5M
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 */
public class A83_OutOfMemoryError_overhead {

    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                //intern添加到常量池
//                list.add(String.valueOf(++i));
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.err.println("(((((((((((((((((" + i);
            e.printStackTrace();
            throw e;
        }


    }
}

