package A20200907_bill尚硅谷_设计模式.A5_适配器模式.$3接口适配器;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 14:47
 * @Description :
 */
public class Client {

    public static void main(String[] args) {
        AbsAdapter absAdapter = new AbsAdapter() {
            //只需要去覆盖我们 需要使用 接口方法
            @Override
            public void m1() {
                // TODO Auto-generated method stub
                System.out.println("使用了 m1 的方法");
            }
        };
        absAdapter.m1();
    }
}
