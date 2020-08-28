package A20200828_设计模式.A1_享元模式.cn;

import java.util.HashMap;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:28
 * @Description :
 */
public class Cache {
    private static final HashMap<String, String> circleMap = new HashMap<>();
    public static String getCircle(String color) {
        String circle = circleMap.get(color);
        if(circle == null) {
            circleMap.put(color, "1");
        }
        return color;
    }
}
