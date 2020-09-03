package A20200814_bili第二季.code;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 * <p>
 * java.lang.Object@1b6d3586
 * java.lang.Object@1b6d3586
 * null
 * null
 */
public class A79_虚引用 {

    /**
     * java.lang.Object@1b6d3586
     * java.lang.Object@1b6d3586
     * null
     * ==========================================================
     * null
     * null
     * java.lang.ref.WeakReference@4554617c
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();//此种定义默认就是强引用
        ReferenceQueue<Object> objectReferenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> objectSoftReference = new PhantomReference<>(o, objectReferenceQueue);

        System.err.println(o);
        System.err.println(objectSoftReference.get());
        System.err.println(objectReferenceQueue.poll());//gc之前为null，gc之后装进队列
        System.err.println("==========================================================");
        o = null;
        System.gc();
        System.err.println(o);
        System.err.println(objectSoftReference.get());
        System.err.println(objectReferenceQueue.poll());//gc之前为null，gc之后装进队列

    }

}

