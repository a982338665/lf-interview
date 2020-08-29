package A20200828_设计模式.A6_原型模式.cn.深拷贝.serializableimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

class Student implements Serializable {
	String name;// 常量对象
	int age;
	Teacher t;// 学生1和学生2的引用值都是一样的。
 
	Student(String name, int age, Teacher t) {
		this.name = name;
		this.age = age;
		this.t = t;
	}
 
	public Object deepClone() throws IOException, OptionalDataException,
			ClassNotFoundException {
		// 将对象写到流里
		OutputStream bo = new ByteArrayOutputStream();
		//OutputStream op = new ObjectOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);
 
		// 从流里读出来
		InputStream bi = new ByteArrayInputStream(((ByteArrayOutputStream) bo).toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (oi.readObject());
	}
 

}
