package A20200828_设计模式.A4_单例模式.cn;

import javax.jws.Oneway;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:19
 * @Description :
 */
public class SIngle2 {
    private static SIngle2 sIngle2 = new SIngle2();

    private SIngle2() {
    }

    public static SIngle2 getsIngle2() {
        return sIngle2;
    }
}
