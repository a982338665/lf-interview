package A20200814bili第二季.code;

import java.util.concurrent.atomic.AtomicReference;

class User{
    String userName;
    int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}

/**
 *  cas:compareAndSet
 */
public class A17_AtomicReference_01 {

    public static void main(String[] args) {
        //JUC中虽然提供了基本数据类型的原子型操作类，但是对于一般对象，或者说引用对象，例如User来说，他的原子类将会出现问题
        // class AtomicReference<V> 原子引用的包装类
        User z3 = new User("z3", 12);
        User l4 = new User("l4", 13);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        System.err.println(atomicReference.compareAndSet(z3, l4)+"\t"+atomicReference.get().toString());
        System.err.println(atomicReference.compareAndSet(z3, l4)+"\t"+atomicReference.get().toString());

    }
}
