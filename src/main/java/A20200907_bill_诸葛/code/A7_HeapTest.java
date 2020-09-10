package A20200907_bill_诸葛.code;

import java.util.ArrayList;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/9/10 9:25
 * @Description :
 */
public class A7_HeapTest {

    byte[] bytes = new byte[1024*100];//100k

    /**
     *
     * @param args
     * @throws InterruptedException
     * 死循环添加对象会触发minor GC，那么这些对象会被回收吗？
     *      不会，因为添加进去的对象依旧在被List引用，而list又被局部变量所引用-GC ROOTS
     *      而死循环方法永远无法执行完，
     * 调优工具：jvisualvm
     *      当老年代也放满的时候，程序会怎么样？--> 会做一次Full GC，会对整个堆区域内的所有对象做垃圾回收，耗费时间较长
     *      针对于本程序，年轻代不会被回收，因为是GC-root可达对象，所以最后木得回收就会触发OOM
     *
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayList<A7_HeapTest> objects = new ArrayList<>();
        while (true){
            objects.add(new A7_HeapTest());
            Thread.sleep(10);
        }
    }
}
