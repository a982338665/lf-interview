package A20200828_设计模式.A4_单例模式.cn;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 17:19
 * @Description :
 */
public class SIngle1 {
    //防止指令重排导致对象多创建
    private volatile static SIngle1 sIngle1;
    private SIngle1(){};
    public static SIngle1 getsIngle1() {
        if(sIngle1 == null){
            synchronized (SIngle1.class){
                if(sIngle1 == null){
                    sIngle1 = new SIngle1();
                }
            }
        }
        return sIngle1;
    }
}
