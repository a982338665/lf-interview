package A20200814_bili第二季.code;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A81_StackOverFlowError {

    public static void main(String[] args) throws InterruptedException {
        sss();
    }

    private static void sss() {
        sss();

    }

}

