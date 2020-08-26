package A20200815_拆箱装箱常量池设计.literal;

public class NumberTest {
	public static void main(String[] args) {
		
		long a1 = 9999999999L;
		long a2 = 9_999_999_999L;
		
		int a3 = 0b0111_1011_0001; //二进制, 0b开头
		int a4 = 0_214;           //八进制, 0开头
		int a5 = 123___45;         //可以多个下划线
		int a6 = 0x7_B_1;          //十六进制
		float a7 = 3.56_78f;       //float
		double a8 = 1.3_45__67;    //double
		
//		int b1 = 0b_123_4;  //_必须在数字之间
//		int b2 = 0123_4_;   //_不能在末尾
//		int b3 = _123;      //_不能在开头
//		int b4 = 0_x_123;   //不能拆开0x
//		int b5 = 0x_51;     //_必须在数字之间
//		long b6 = 1000_L;   //_必须在数字之间
//		float b7 = 1.34f_;  //_不能在末尾
		
		
	}
}
