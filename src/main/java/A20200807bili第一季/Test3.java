package A20200807bili第一季;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/7 11:01
 * @Description :
 */
public class Test3 {
    public static void main(String[] args) {
        int i = 1;
        String string = "hello";
        Integer num = 200;
        int[] arr = {1, 2, 3, 4, 5};
        Mydata mydata = new Mydata();
        change(i,string,num,arr,mydata);
        System.err.println(i);
        System.err.println(string);
        System.err.println(num);
        System.err.println(arr[0]);
        System.err.println(mydata.anInt);
    }

    private static void change(int i, String string, Integer num, int[] arr, Mydata mydata) {
        i+=1;
        string+="world";
        num+=1;
        arr[0]+=1;
        mydata.anInt +=1;
    }

}

class Mydata {
    int anInt = 10;
}
