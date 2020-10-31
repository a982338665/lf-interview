package A20200907_bill尚硅谷_设计模式.A6_桥接模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 16:11
 * @Description :
 */
public class Client {
    public static void main(String[] args) {
        Phone phone1 = new FolderPhone(new XiaoMiBrand());
        phone1.call();
        Phone phone2 = new UpRightPhone(new XiaoMiBrand());
        phone2.call();
    }
}
