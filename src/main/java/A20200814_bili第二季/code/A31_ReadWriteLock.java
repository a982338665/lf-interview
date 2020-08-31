package A20200814_bili第二季.code;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 读写锁
 * <p>
 * 原子加独占：中间是统一的，不能被分割
 */
class MyCache {

    /**
     * 写数据一定会用到可见性
     */
    private volatile HashMap<String, Object> map = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, Object val) throws InterruptedException {
        lock.writeLock().lock();
        try{
            System.err.println(Thread.currentThread().getName() + "\t正在写入：" + key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key, val);
            System.err.println(Thread.currentThread().getName() + "\t写入完成：" + key);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.writeLock().unlock();
        }
    }

    public Object get(String key) throws InterruptedException {
        lock.readLock().lock();
        try{
            System.err.println(Thread.currentThread().getName() + "\t正在读取：" + key);
            TimeUnit.MILLISECONDS.sleep(300);
            Object o = map.get(key);
            System.err.println(Thread.currentThread().getName() + "\t读取完成：" + o);
            return o;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.readLock().unlock();
        }
        return null;
    }
}

public class A31_ReadWriteLock {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    myCache.put(temp + "", temp + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    myCache.get(temp + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}

