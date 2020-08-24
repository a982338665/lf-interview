package A20200814bili第二季.code;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 集合类不安全演示
 */
public class A23_ContainerNotSafe {

    public static void main(String[] args) {
        Map<String,String> list = new HashMap<>();
        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                list.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
            }, String.valueOf(i)).start();
        }
        list.get(0);
    }
}
