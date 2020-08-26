package A20200807_bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:01
 * @Description :
 * <p>
 * <p>
 * 编程题：有N步台阶，每次只能1步或2步，共有多少种走法、
 * 1.递归
 * 2.循环迭代
 */
public class Test4 {

    public static void main(String[] args) {
        System.err.println(f(3));
        System.err.println(f(4));
        System.err.println(f(5));
        System.err.println(f2(5));
    }


    public static int f(int x) {
        if (x < 1) {
            throw new IllegalArgumentException(x + "can not be less than 1");
        }
        if (x == 1 || x == 2) {
            return x;
        } else {
            return f(x - 2) + f(x - 1);
        }
    }

    public static int f2(int x) {
        if (x < 1) {
            throw new IllegalArgumentException(x + "can not be less than 1");
        }
        if (x == 1 || x == 2) {
            return x;
        } else {
            int one = 2; //初始化为走到第二季台阶的走法
            int two = 1; //初始化为走到第一级台阶的走法
            int sum = 0; //总和走法

            //从第三级台阶开始
            // x = 3 返回3
            // x = 5 时
            //  第一次循环：sum=3 two=2 one=3
            //  第二次循环：sum=5 two=3 one=5
            //  第三次循环：sum=8 two=5 one=8
            for (int i = 3; i <= x; i++) {
                //最后跨两部+跨一步走法
                sum = two + one;
                two = one;
                one = sum;
            }

            return sum;
        }
    }

}
