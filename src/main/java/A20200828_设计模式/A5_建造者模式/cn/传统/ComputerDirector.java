package A20200828_设计模式.A5_建造者模式.cn.传统;

/**
 * 第四步：指导者类（Director）
 */
public class ComputerDirector {
    public void makeComputer(ComputerBuilder builder){
        builder.setUsbCount();
        builder.setDisplay();
        builder.setKeyboard();
    }
}
