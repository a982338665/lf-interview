package A20200828_设计模式.A7_适配器模式.cn.extend;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/29 14:04
 * @Description : 进口机器
 */
public class MaChine {

    public void work(Target target){
        target.ConvertToXXX();
        System.err.println("正常工作");
    }
}
