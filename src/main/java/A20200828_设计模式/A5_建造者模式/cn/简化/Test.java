package A20200828_设计模式.A5_建造者模式.cn.简化;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 20:28
 * @Description :
 */
public class Test {
    public static void main(String[] args) {
        Computer computer=new Computer.Builder("因特尔","三星")
                .setDisplay("三星24寸")
                .setKeyboard("罗技")
                .setUsbCount(2)
                .build();
        System.err.println(computer);
    }
}
