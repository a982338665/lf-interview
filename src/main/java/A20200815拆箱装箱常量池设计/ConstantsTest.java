package A20200815拆箱装箱常量池设计;

/**
 * 常量池
 *  Boolean    Boolean     true/false
 *             Byte        -128 到 127
 *             Short       -128 到 127
 *             Integer     -128 到 127
 *             Long        -128 到 127
 *             Character   0 到 127
 *             Float       没有缓存-常量池
 *             Double      没有缓存-常量池
 */
public class ConstantsTest {
    public static void main(String[] args) {
        Integer a1 = 127;
        Integer a2 = 127;
        System.err.println(a1 == a2);//true
        Integer a4 = 128;
        Integer a5 = 128;
        //因为128超过常量池缓存范围 所以是两个地址，不相等
        System.err.println(a4 == a5);//false
        Integer a3 = new Integer(127);
        System.err.println(a1 == a3);//false
        String a6 = "abc";
        String a7 = "a"+"bc";//此处在编译的时候 会被认定为 abc 所以不会再分配空间
        String a8 = "a"+"b"+"c";
        System.err.println(a6==a7);//true
        System.err.println(a6==a8);//true


        int q1 = 4;
        Integer q2 = 4;//自动装箱：默认int类型，自动装箱成为Integer类型
        System.err.println(q1==q2);//自动拆箱：包装类和基本类型比较时，包装类自动拆箱为int  true

        Integer q3 = new Integer(4);
        System.err.println(q1==q3);//自动拆箱：包装类和基本类型比较时，包装类自动拆箱int  true
        System.err.println(q2==q3);//两个包装类比较，不会自动拆箱，new对象无常量池，所以  false

        Integer q4 = new Integer(2);
        Integer q5 = new Integer(2);
        System.err.println(q1 == (q4+q5));//true
        System.err.println(q2 == (q4+q5));//true
        System.err.println(q3 == (q4+q5));//true
        //(q4+q5)的操作会使得 q4 q5自动拆箱为基本类型并运算得到 4
        //运算的到的基本类型 4 不管与基本类型q1比较还是包装类型 q4 q5比较，都会先拆箱，在比较，所以都为 true
        //如果将q4+q5提出来用Integer接收的话，实际上就是加的时候拆箱，接数据的时候装箱，成为赋值创建
        Integer q6 = q4+q5;
        System.err.println(q1==q6);//int 和integer比较会自动拆箱 true
        System.err.println(q2==q6);//integer和integer比较，没有new，在范围内 为true
        System.err.println(q3==q6);//integer和new 对象比较 false


        String s1 = "1";
        String s2 = "1";
        String s3 = new String("1");
        String s4 = new String("1");
        System.err.println(s1==s2);//true 常量池
        System.err.println(s1==s3);//false 一个栈内存，一个堆内存
        System.err.println(s4==s3);//false 两个堆内存

        String s5= s1+"1";//设计到变量s1 ，编译器不优化
        String s6= "1"+"1";//编译器优化
        String s8= "11";
        String s7= "1"+new String("1");//new 对象编译器不优化
        System.err.println(s5==s6);//false
        System.err.println(s7==s6);//false
        System.err.println(s8==s6);//true
    }
}
