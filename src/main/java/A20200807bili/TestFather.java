package A20200807bili;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:44
 * @Description :
 */
public class TestFather {


    //非静态方法的前面其实有一个默认对象 this
    //this在构造器(或<init>)表示正在创建的对象，因为这里是在创建Son对象，所以
    //test()执行的是子类重写的代码（面向对象多态）
    private int x = test();



    private static int xx = test2();

    static {
        System.err.println(1);
    }

    TestFather() {
        System.err.println(2);
    }

    {
        System.err.println(3);
    }

    public int test() {
        System.err.println(4);
        return 1;
    }

    public static int test2() {
        System.err.println(5);
        return 1;
    }

    public static int test3() {
        System.err.println(11);
        return 1;
    }
}
