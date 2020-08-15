package A20200815拆箱装箱常量池设计.multipleexception;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class MultipleExceptionTest {

	public static void main(String[] args) {
		try
		{
			test();
		}
		catch(IOException ex)
		{
			//异常处理
		}
		catch(SQLException ex)
		{
			//异常处理
		}
		
		
		try
		{
			test();
		}
		catch(IOException | SQLException ex)
		{
			//JDK7开始，支持一个catch写多个异常
			//异常处理
		}
		
		/*try
		{
			test2();
		}
		//通过管道函数，会报错，因为两者直接有继承关系
		catch(IOException | FileNotFoundException ex)
		{
			//JDK7开始，支持一个catch写多个异常
			//异常处理
		}*/

	}
	
	public static void test() throws IOException, SQLException {
    } 
	public static void test2() throws IOException, FileNotFoundException {
    } 
}
