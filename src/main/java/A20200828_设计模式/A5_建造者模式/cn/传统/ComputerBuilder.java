package A20200828_设计模式.A5_建造者模式.cn.传统;

/**
 * 第二步：抽象构建者类
 */
public abstract class ComputerBuilder {
    public abstract void setUsbCount();
    public abstract void setKeyboard();
    public abstract void setDisplay();

    public abstract Computer getComputer();
}
