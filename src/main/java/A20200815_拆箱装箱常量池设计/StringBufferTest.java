package A20200815_拆箱装箱常量池设计;

/**
 String耗时：1496
 StringBuffer耗时：8
 StringBuilder耗时：2
 StringConcat耗时：632
 */
public class StringBufferTest {

    public static void main(String[] args) {
        String a ="1";
        StringBuffer stringBuffer = new StringBuffer(a);
        StringBuilder stringBuilder = new StringBuilder(a);
        int j = 50000;
        long q1 = System.currentTimeMillis();
        for (int i = 0; i < j ; i++) {
            a = a + ",";
        }
        long q2 = System.currentTimeMillis();
        for (int i = 0; i < j ; i++) {
            stringBuffer.append(",");
        }
        long q3 = System.currentTimeMillis();
        for (int i = 0; i < j ; i++) {
            stringBuilder.append(",");
        }
        long q4 = System.currentTimeMillis();
        for (int i = 0; i < j ; i++) {
            a.concat(",");
        }
        long q5 = System.currentTimeMillis();
        System.err.println("String耗时："+(q2-q1));
        System.err.println("StringBuffer耗时："+(q3-q2));
        System.err.println("StringBuilder耗时："+(q4-q3));
        System.err.println("StringConcat耗时："+(q5-q4));
    }


}
