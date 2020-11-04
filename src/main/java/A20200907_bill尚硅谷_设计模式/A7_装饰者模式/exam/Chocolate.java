package A20200907_bill尚硅谷_设计模式.A7_装饰者模式.exam;

public class Chocolate extends Decorator {


    public Chocolate(Drink obj) {
        super(obj);
        // TODO Auto-generated constructor stub
        setDes(" 巧克力 ");
        setPrice(2.2f);
    }


}
