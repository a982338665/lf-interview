package A20200814_bili第二季.code;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import sun.misc.VM;
import sun.rmi.runtime.NewThreadAction;

import java.lang.reflect.Method;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 * <p>
 * MaxDirectMemorySize表示直接内存最大给5M
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 */
public class A87_OutOfMemoryError_metaspace {

    public static void main(String[] args) {
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(TestClass.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.err.println("88888888888888888888888888888多少次后发生了异常：" + i);
            e.printStackTrace();
            throw e;
        }
    }

    static class TestClass {
    }
}

