package A20200907_bill_诸葛.code;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/9/9 15:54
 * @Description :
 * 执行过程 - 对应有图片示意
 * 1.main线程开始运行时，java虚拟机会在当前线程栈【S】上分配一块专属内存区域【s0】
 * 2.【s0】区域是用来放方法内的局部变量，且只在方法作用域中有效
 *      A3_栈帧 a3_栈帧 = new A3_栈帧();
 * 3.执行方法compute时，java虚拟机会在当前线程栈【S】上分配另一块专属内存区域【s1】，【s1中存放其自己的局部变量】
 *      a3_栈帧.compute();
 * 4.执行完方法compute，释放资源【s1】，后调用，先释放，出栈
 * 5.执行完释放【s0】
 */
public class A3_栈帧 {

    public static final int initData = 66;
    public static A3_User a3_user =new A3_User();

    public int compute(){
        int a = 1;
        int b = 2;
        int c = (a+b)*10;
        return c;
    }

    public static void main(String[] args) {
        A3_栈帧 a3_栈帧 = new A3_栈帧();
        a3_栈帧.compute();
        System.err.println("test");
    }
}
