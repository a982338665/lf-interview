package A20200828_设计模式.A7_适配器模式.cn.impl;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 14:16
 * @Description :
 */
public class AdapterAll implements Target {

    //持有待适配的插头，也就是真正存在的插头
    private Chatou220V chatou220V;

    //通过构造方法告诉适配器要适配的内容
    public AdapterAll(Chatou220V chatou220V){
        this.chatou220V = chatou220V;
    }

    /**
     * 重写适配器要实现的转换功能
     */
    @Override
    public void ConvertToXXX() {
        this.chatou220V.V220();
        System.err.println("适配为...");
    }
}
