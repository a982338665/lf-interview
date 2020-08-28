package A20200807_bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 9:59
 * @Description :
 */
public class TestBaseData {

    Integer anInt = 1;

    public void test() {
        Integer y = 1;
        System.err.println(y==this.anInt);
    }

    public static void main(String[] args) {
        new TestBaseData().test();

        Integer x = 128;
        Integer y = 128;
        System.err.println(x==128);
    }
}
