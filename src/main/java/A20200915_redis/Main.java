package A20200915_redis;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 从键盘接收数据

        // next方式接收字符串
        System.out.println("输入字符串：");
        String str = scanner.nextLine();
        scanner.close();
        String[] strings = str.split("\\s+");//从空白字符处分割字符串，“+”表示匹配1到无穷，“*”表示匹配0到无穷。
        int length = strings[strings.length - 1].length();//最后一个字符串的长度
        System.out.println(length);
        // 判断是否还有输入
//        if (scan.hasNext()) {
//            String str1 = scan.next();
//            System.out.println("输入的数据为：" + str1);
//            String[] strings = str1.split(" ");//从空白字符处分割字符串，“+”表示匹配1到无穷，“*”表示匹配0到无穷。
////            System.err.println(strings[0]+strings[1]);
//            int length = strings[strings.length - 1].length();//最后一个字符串的长度
//            System.out.println(length);
//        }
//        scan.close();
    }
}
