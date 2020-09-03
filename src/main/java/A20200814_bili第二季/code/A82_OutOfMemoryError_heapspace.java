package A20200814_bili第二季.code;


/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description :
 *
 * -Xms1m -Xmx1m
 */
public class A82_OutOfMemoryError_heapspace {

    static class HeapObject {
    }

    public static void main(String[] args)  {
//        exam1();
        exam2();
        return;
    }

    private static void exam2() {
        byte[] bytes = new byte[2*1024*1024];
//        List<HeapObject> list = new ArrayList<>();
//
//        while (true) {
//            list.add(new HeapObject());
//            System.out.println(list.size());
//        }
    }

    private static void exam1() {
        String string = "xxxxx";
        while (true) {
            string += string;
//            string.intern();
        }
    }

}

