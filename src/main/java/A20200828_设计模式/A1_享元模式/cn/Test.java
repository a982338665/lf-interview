package A20200828_设计模式.A1_享元模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:30
 * @Description :
 */
public class Test {

    public static void main(String[] args) {
        String circle = Cache.getCircle("1");
        System.err.println(circle);
        String circle2 = Cache.getCircle(new String("1"));
        System.err.println(circle.equals(circle2));
    }
}
