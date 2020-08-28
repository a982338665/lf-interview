package A20200828_设计模式.A5_建造者模式.cn.传统;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/28 20:35
 * @Description :
 */
public class Test {

    /**
     * 首先生成一个director (1)，然后生成一个目标builder (2)，接着使用director组装builder (3),组装完毕后使用builder创建产品实例 (4)。
     * 可以看到，【简化版】文章最开始的使用方式是传统builder模式的变种， 首先其省略了director 这个角色，
     * 将构建算法交给了client端，其次将builder 写到了要构建的产品类里面，最后采用了链式调用。
     * @param args
     */
    public static void main(String[] args) {
        ComputerDirector director = new ComputerDirector();//1
        ComputerBuilder builder = new MacComputerBuilder("I5处理器", "三星125");//2
        director.makeComputer(builder);//3
        Computer macComputer = builder.getComputer();//4
        System.out.println("mac computer:" + macComputer.toString());

        ComputerBuilder lenovoBuilder = new LenovoComputerBuilder("I7处理器", "海力士222");
        director.makeComputer(lenovoBuilder);
        Computer lenovoComputer = lenovoBuilder.getComputer();
        System.out.println("lenovo computer:" + lenovoComputer.toString());
    }

}
