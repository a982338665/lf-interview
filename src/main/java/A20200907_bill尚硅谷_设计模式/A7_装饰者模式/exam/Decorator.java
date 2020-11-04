package A20200907_bill尚硅谷_设计模式.A7_装饰者模式.exam;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 17:09
 * @Description :
 */
public class Decorator extends Drink {

    private Drink obj;

    public Decorator(Drink obj) { //组合
// TODO Auto-generated constructor stub
        this.obj = obj;
    }


    @Override
    public float cost() {
// TODO Auto-generated method stub
// getPrice 自己价格
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDes() {
// TODO Auto-generated method stub
// obj.getDes() 输出被装饰者的信息
        return des + " " + getPrice() + " && " + obj.getDes();
    }
}
