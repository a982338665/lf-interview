package A20200814_bili第二季.code;


import java.lang.ref.PhantomReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * java.lang.Object@1b6d3586
 * java.lang.Object@1b6d3586
 * null
 * null
 */
public class A76_WeakhashMap {
    public static void main(String[] args) throws InterruptedException {
        //普通map===============================================
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer(1);
        String val = "hashmap";
        map.put(key, val);
        System.err.println(map);
        key = null;
        System.err.println(map);
        System.gc();
        System.err.println(map);
        //WeakHashMap===============================================
        System.err.println("===============================================");
        Map<Integer,String> map2 = new WeakHashMap<>();
        Integer key2 = new Integer(1);
        String val2 = "hashmap";
        map2.put(key2, val2);
        System.err.println(map2);
        key2 = null;
        System.err.println(map2);
        System.gc();
        System.err.println(map2);

    }

}

