package A20200815拆箱装箱常量池设计.boxing;
import java.util.ArrayList;

public class IntegerTest {

	public static void main(String[] args)
	{
		//没有自动装箱时
		Integer obj2 = Integer.valueOf(5);
		//等号右边是int类型，西东装箱后赋值给Integer对象，简化了写法
		Integer obj1 = 5;  //自动装箱

		int a1 = obj1;     //自动拆箱
		int a2 = obj1.intValue();
		
		
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);//自动装箱
		list.add(Integer.valueOf(2));
		
		int a3 = list.get(1);//自动拆箱
		int a4 = list.get(1).intValue();

	}
}
