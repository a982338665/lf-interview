package A20200814bili第二季.code;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  cas:compareAndSet
 */
public class A13_CAS_01 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * AtomicInteger.java:
         *     //1.获取Unsafe类，方法都是native修饰的，可直接操作底层资源
         *     private static final Unsafe unsafe = Unsafe.getUnsafe();
         *     //2.表示该变量值在内存中的偏移地址，因为unsafe就是根据此地址获取数据的
         *     private static final long valueOffset;
         *     static {
         *         try {
         *             //3.从unsafe获取偏移地址
         *             valueOffset = unsafe.objectFieldOffset
         *                 (AtomicInteger.class.getDeclaredField("value"));
         *         } catch (Exception ex) { throw new Error(ex); }
         *     }
         *     //4.保证内存可见性
         *     private volatile int value;
         *     //5.执行的方法
         *     public final int getAndIncrement() {
         *         //6.this指当前对象，valueOffset指地址，1指具体操作为加1
         *         return unsafe.getAndAddInt(this, valueOffset, 1);
         *     }
         *
         *Unsafe.java
         *
         *      public final int getAndAddInt(Object var1, long var2, int var4) {
         *      int var5;
                do {
                 //7.获取var1对象下，var2地址里的值是多少，即当前对象下，这个地址下的值的多少，即从主物理内存中拷贝值出来
                 var5 = this.getIntVolatile(var1, var2);
                 //8.当前对象（var1），这个地址（var2）的值（var5）等于var5，就执行操作var5+var4，即加1
                } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
                 //9.假设this.compareAndSwapInt(var1, var2, var5, var5 + var4)返回true，即满足条件且加一，则while(!true)==false,跳出循环返回最新值var5
                    //假设this.compareAndSwapInt(var1, var2, var5, var5 + var4)返回false，即不满足条件说明var1中var2里的值不等于var5，主内存数据已被动过
                    //  此时while(!false) == true,即继续循环重新获取地址下的数据值，重新相加
                 return var5;
                }
         *
         */
        atomicInteger.getAndIncrement();
    }

}
