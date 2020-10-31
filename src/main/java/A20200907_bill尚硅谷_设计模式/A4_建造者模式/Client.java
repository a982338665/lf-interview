package A20200907_bill尚硅谷_设计模式.A4_建造者模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/10/31 10:42
 * @Description :
 */
public class Client {

    public static void main(String[] args) {
        //盖普通房子
        CommonHouse commonHouse = new CommonHouse();
//准备创建房子的指挥者
        HouseDirector houseDirector = new HouseDirector(commonHouse);

//完成盖房子，返回产品(普通房子)
        House house = houseDirector.constructHouse();
//System.out.println(" 输 出 流 程 "); System.out.println("--------------------------");
//盖高楼
        HighBuilding highBuilding = new HighBuilding();
//重置建造者houseDirector.setHouseBuilder(highBuilding);
//完成盖房子，返回产品(高楼) houseDirector.constructHouse()
    }
}
