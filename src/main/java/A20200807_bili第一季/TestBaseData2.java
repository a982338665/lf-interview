package A20200807_bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 9:59
 * @Description :
 */
public class TestBaseData2 {

    Integer anInt = 1;

    public void test() {
        Integer y = 1;
        System.err.println(y == this.anInt);
    }

    public Integer getAnInt(){
        int x = 1;
        return x;
    }

    public static void main(String[] args) {

        Integer c = 1;
        TestBaseData2 testBaseData2 = new TestBaseData2();
        System.err.println(c == testBaseData2.anInt);
        System.err.println(c == testBaseData2.getAnInt());
    }
}
