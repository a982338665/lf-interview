package A20200815_拆箱装箱常量池设计.boxing;

public class NumberTest {

	public static void main(String[] args) {
		Integer a1 = 1000;
		int a2 = 1000;
		Integer a3 = 2000;
		Long a4 = 2000L;
		long a5 = 2000L;
		
		System.out.println(a1 == a2);  //拆箱再进行数值比较			true
		System.out.println(a3 == (a1 + a2));  //拆箱再进行数值比较	true
		System.out.println(a4 == (a1 + a2));  //拆箱再进行数值比较	true
		System.out.println(a5 == (a1 + a2));  //拆箱再进行数值比较	true
		
		
		System.out.println(a3.equals(a1+a2)); //equals要求同类，且内容相同	true
		System.out.println(a4.equals(a1+a2)); //equals要求同类，且内容相同	false integer和long
		System.out.println(a4.equals((long) (a1+a2))); //equals要求同类，且值相同	true
		
//		System.out.println(a3 == a4); //不同类型不能比较，两个对象不比较
	}

}
