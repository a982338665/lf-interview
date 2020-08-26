package A20200814_bili第二季.code;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  cas:compareAndSet
 */
public class A12_CAS_01 {

    public static void main(String[] args) {
        //
        AtomicInteger atomicInteger = new AtomicInteger(5);

        //第一个参数期望值，第二个参数更新值
        //当期望值不符合预期时，重新获取主内存的最新值再次判断
        //true	 current data:2019
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\t current data:"+atomicInteger.get());
        //false	 current data:2019
        System.out.println(atomicInteger.compareAndSet(5, 2010)+"\t current data:"+atomicInteger.get()) ;
    }

}
