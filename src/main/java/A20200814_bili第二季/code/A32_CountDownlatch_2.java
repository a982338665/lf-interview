package A20200814_bili第二季.code;

import java.util.concurrent.CountDownLatch;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 */
public enum A32_CountDownlatch_2 {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "韩"),
    SIX(6, "魏");
    private Integer retCode;
    private String retMsg;

    public static A32_CountDownlatch_2 foreach_CountryEnum(int index) {
        A32_CountDownlatch_2[] values = A32_CountDownlatch_2.values();
        for (A32_CountDownlatch_2 element : values
        ) {
            if(index == element.getRetCode()){
                return element;
            }
        }
        return null;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    A32_CountDownlatch_2(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }
}

