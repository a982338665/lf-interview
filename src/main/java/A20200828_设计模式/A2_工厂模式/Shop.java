package A20200828_设计模式.A2_工厂模式;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 16:43
 * @Description :
 */
public class Shop {

    public Animal getAnimal(String name){
        if(name.equals("dog")){
            return new Dog();
        }else if(name.equals("pig")){
            return new Pig();
        }
        return null;
    }
}
