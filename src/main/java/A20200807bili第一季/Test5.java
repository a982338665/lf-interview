package A20200807bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:01
 * @Description :
 * <p>
 * <p>
 */
public class Test5 {

    static int s;
    int i;
    int j;

    {
        int i = 1;
//        this.i++;//若指定了this则指的是当前对象的i，而非局部变量i
        i++;//i因为就近原则会累加局部变量i
        j++;
        s++;
    }

    public void test(int j) {
        j++;//j因为就近原则，取形参列表j
        i++;
        s++;
    }

    public static void main(String[] args) {
        //0 1 1
        Test5 test5 = new Test5();
        //0 1 2
        Test5 test51 = new Test5();
        //1 1 3
        test5.test(10);
        //2 1 4
        test5.test(20);
        //1 1 5
        test51.test(20);
        //合并后为：
        // 2 1 5
        // 1 1 5
        System.err.println(test5.i);
        System.err.println(test5.j);
        System.err.println(test5.s);
        System.err.println("++++++++++++++++++++++=");
        System.err.println(test51.i);
        System.err.println(test51.j);
        System.err.println(test51.s);
    }

}
