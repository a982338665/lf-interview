package A20200907_bill尚硅谷_设计模式.A4_建造者模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 10:44
 * @Description :
 */
public class CommonHouse extends HouseBuilder {


    @Override
    public void buildBasic() {
// TODO Auto-generated method stub
        StringBuilder sg = new StringBuilder("bu");
        System.out.println(" 普通房子打地基 5 米 ");
    }


    @Override
    public void buildWalls() {
// TODO Auto-generated method stub
        System.out.println(" 普通房子砌墙 10cm ");
    }


    @Override
    public void roofed() {
// TODO Auto-generated method stub
        System.out.println(" 普通房子屋顶 ");
    }
}
