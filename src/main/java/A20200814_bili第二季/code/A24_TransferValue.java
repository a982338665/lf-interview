package A20200814_bili第二季.code;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 值传递及引用传递问题
 */
public class A24_TransferValue {

    public void changeVal1(int age) {
        age = 30;
    }

    public void changeVal2(Person pr) {
        pr.setPersonName("XXX");
    }

    public void changeVal3(String str) {
        str = "xxx";
    }

    public static void main(String[] args) {
        A24_TransferValue value = new A24_TransferValue();
        int age = 20;
        value.changeVal1(age);
        System.err.println("age============="+age);
        String yyy = "yyy";
        value.changeVal3(yyy);
        System.err.println("yyy============="+yyy);
        Person llll = new Person("llll");
        value.changeVal2(llll);
        System.err.println(llll.getPersonName());
    }

}

class Person {
    private Integer id;
    private String personName;

    public Person(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}


