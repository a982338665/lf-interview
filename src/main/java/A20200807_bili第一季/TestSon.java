package A20200807_bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:44
 * @Description :
 */
public class TestSon extends TestFather {

    private int x = test();
    private static int xx = test2();

    static {
        System.err.println(6);
    }

    TestSon() {
        System.err.println(7);
    }

    {
        System.err.println(8);
    }

    public int test() {
        System.err.println(9);
        return 1;
    }

    public static int test2() {
        System.err.println(10);
        return 1;
    }

    public static void main(String[] args) {
        System.err.println("类初始化结束==============================================");
        TestSon testSon = new TestSon();
        System.err.println("实例1初始化结束==============================================");
        TestSon testSon2 = new TestSon();
        System.err.println("实例2初始化结束==============================================");
    }
}
