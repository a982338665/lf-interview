package A20200807_bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:01
 * @Description :
 */
public class Test2 {
    private Test2() {

    }

    private static class Inner {
        private static final Test2 test = new Test2();
    }

    public static Test2 getInstance() {
        return Inner.test;
    }
}
