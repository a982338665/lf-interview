package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A72_强引用 {
    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();//此种定义默认就是强引用
        Object o2 = o;//o2引用赋值
        o = null;
        System.gc();
        System.err.println(o);
        System.err.println(o2);
    }
}

