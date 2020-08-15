package A20200815拆箱装箱常量池设计;

public class DisabledOBJ {

    public static void main(String[] args) {

        String a = "a";//a指针指向地址 "a"
        String b=a;    //b指针指向 a指针指向的地址 "a"
        System.err.println(b); // "a"
        a ="a2";//给a重新赋值，此时a指针指向别的地方，b仍指向a的原地址
        System.err.println(b); // "a"

        String c = "1";
        change(c);//值传递只传递内容，不传递引用，所以值不变，里面的临时变量改变
        System.err.println(c);

    }

    /**
     * 值传递测试
     * @param a
     */
    public static void change(String a){
        a = "23";
    }
}
