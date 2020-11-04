package A20200907_bill尚硅谷_设计模式.A7_装饰者模式.exam;

public class Milk extends Decorator {


    public Milk(Drink obj) {
        super(obj);
// TODO Auto-generated constructor stub
        setDes(" 牛 奶 ");
        setPrice(2.0f);
    }


}
