package A20200814_bili第二季.code;


import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

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
public class A74_弱引用 {
    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();//此种定义默认就是强引用
        WeakReference<Object> objectSoftReference = new WeakReference<>(o);
        System.err.println(o);
        System.err.println(objectSoftReference.get());

        o = null;
        System.gc();
        System.err.println(o);
        System.err.println(objectSoftReference.get());
    }

}

