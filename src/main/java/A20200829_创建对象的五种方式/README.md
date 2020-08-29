
## 简介

    使用new关键字	}                       → 调用了构造函数
    使用Class类的newInstance方法	}       → 调用了构造函数
    使用Constructor类的newInstance方法	}   → 调用了构造函数
    使用clone方法	}                       → 没有调用构造函数
    使用反序列化	}                       → 没有调用构造函数

## 1.new
## 2.使用Class类的newInstance方法
    
    Employee emp2 = (Employee) Class.forName("org.programming.mitra.exercises.Employee").newInstance();
    或者
    Employee emp2 = Employee.class.newInstance();

## 3.使用Constructor类的newInstance方法
    
    Constructor<Employee> constructor = Employee.class.getConstructor();
    Employee emp3 = constructor.newInstance();

## 4.无论何时我们调用一个对象的clone方法，jvm就会创建一个新的对象，将前面对象的内容全部拷贝进去。用clone方法创建对象并不会调用任何构造函数。
     
    要使用clone方法，我们需要先实现Cloneable接口并实现其定义的clone方法。
    Employee emp4 = (Employee) emp3.clone();

## 5.使用反序列化
    
    当我们序列化和反序列化一个对象，jvm会给我们创建一个单独的对象。在反序列化时，jvm创建对象并不会调用任何构造函数。
    为了反序列化一个对象，我们需要让我们的类实现Serializable接口
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"));
    Employee emp5 = (Employee) in.readObject();
    
## 实例
    
    class Employee implements Cloneable, Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        public Employee() {
            System.out.println("Employee Constructor Called...");
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Employee other = (Employee) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }
        @Override
        public String toString() {
            return "Employee [name=" + name + "]";
        }
        @Override
        public Object clone() {
            Object obj = null;
            try {
                obj = super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return obj;
        }
    }
    
    
    
    public class ObjectCreation {
        public static void main(String... args) throws Exception {
            // By using new keyword
            Employee emp1 = new Employee();
            emp1.setName("Naresh");
            System.out.println(emp1 + ", hashcode : " + emp1.hashCode());
            // By using Class class's newInstance() method
            Employee emp2 = (Employee) Class.forName("org.programming.mitra.exercises.Employee")
                                   .newInstance();
            // Or we can simply do this
            // Employee emp2 = Employee.class.newInstance();
            emp2.setName("Rishi");
            System.out.println(emp2 + ", hashcode : " + emp2.hashCode());
            // By using Constructor class's newInstance() method
            Constructor<Employee> constructor = Employee.class.getConstructor();
            Employee emp3 = constructor.newInstance();
            emp3.setName("Yogesh");
            System.out.println(emp3 + ", hashcode : " + emp3.hashCode());
            // By using clone() method
            Employee emp4 = (Employee) emp3.clone();
            emp4.setName("Atul");
            System.out.println(emp4 + ", hashcode : " + emp4.hashCode());
            // By using Deserialization
            // Serialization
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
            out.writeObject(emp4);
            out.close();
            //Deserialization
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"));
            Employee emp5 = (Employee) in.readObject();
            in.close();
            emp5.setName("Akash");
            System.out.println(emp5 + ", hashcode : " + emp5.hashCode());
        }
    }
    
