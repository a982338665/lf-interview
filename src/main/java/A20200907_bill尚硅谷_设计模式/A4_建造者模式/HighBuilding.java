package A20200907_bill尚硅谷_设计模式.A4_建造者模式;

public class HighBuilding extends HouseBuilder {


    @Override
    public void buildBasic() {
// TODO Auto-generated method stub
        System.out.println(" 高楼的打地基 100 米 ");
    }


    @Override
    public void buildWalls() {
// TODO Auto-generated method stub
        System.out.println(" 高楼的砌墙 20cm ");
    }


    @Override
    public void roofed() {
// TODO Auto-generated method stub
        System.out.println(" 高楼的透明屋顶 ");
    }


}
