package A20200815_拆箱装箱常量池设计.literal;

public class BinaryNumberTest {

	public static void main(String[] args) {
		
		byte a1 = (byte) 0b00100001;
		short a2 = (short) 0b1010000101000101;
		int a3 = 0b10100001010001011010000101000101;
		int a4 = 0b101;
		int a5 = 0B101; //B可以大小写
		long a6 = 0b1010000101000101101000010100010110100001010001011010000101000101L;

		final int[] s1 = { 0b00110001, 0b01100010, 0b11000100, 0b10000100 };

	}

}
