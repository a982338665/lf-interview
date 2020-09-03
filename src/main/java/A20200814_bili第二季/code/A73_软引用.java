package A20200814_bili第二季.code;


import java.lang.ref.SoftReference;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public class A73_软引用 {
    public static void main(String[] args) throws InterruptedException {

        //内存够用，软引用不被回收++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//        java.lang.Object@1b6d3586
//        java.lang.Object@1b6d3586
//        null
//        java.lang.Object@1b6d3586
        exam();

        //内存不够用，软引被回收++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //配置大对象，导致内存不够用，进行测试
        // -Xms5m -Xmx5m
        //java.lang.Object@4554617c
        //java.lang.Object@4554617c
        //null
        //null
        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        Object o = new Object();//此种定义默认就是强引用
        SoftReference<Object> objectSoftReference = new SoftReference<>(o);
        System.err.println(o);
        System.err.println(objectSoftReference.get());

        o=null;
        System.gc();
        try{
            byte[] bytes = new byte[10 * 1024 * 1024];
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.err.println(o);
            System.err.println(objectSoftReference.get());
        }
    }

    private static void exam() {
        Object o = new Object();//此种定义默认就是强引用
        SoftReference<Object> objectSoftReference = new SoftReference<>(o);
        System.err.println(o);
        System.err.println(objectSoftReference.get());

        o=null;
        System.gc();

        System.err.println(o);
        System.err.println(objectSoftReference.get());
    }
}

