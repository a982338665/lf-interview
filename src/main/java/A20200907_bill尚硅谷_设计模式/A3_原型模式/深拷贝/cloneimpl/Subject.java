package A20200907_bill尚硅谷_设计模式.A3_原型模式.深拷贝.cloneimpl;

public class Subject implements Cloneable {

    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        //Subject 如果也有引用类型的成员属性，也应该和 Student 类一样实现
        return super.clone();
    }

    @Override
    public String toString() {
        return "[Subject: " + this.hashCode() + ",name:" + name + "]";
    }
}
