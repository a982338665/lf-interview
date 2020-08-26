package A20200821_注解;

@Table(name = "some_table")
public class SomeEntity {

    public static void main(String[] args) {

        System.out.println(SomeEntity.class.isAnnotationPresent(Table.class)); // true
        System.out.println(new SomeEntity().getClass().isAnnotationPresent(Table.class)); // true

        Class<?> o1 = SomeEntity.class;
        System.out.println(o1.getClass().isAnnotationPresent(Table.class)); // false

        Class<SomeEntity> o2 = SomeEntity.class;
        System.out.println(o2.getClass().isAnnotationPresent(Table.class)); // false

        Class o3 = SomeEntity.class;
        System.out.println(o3.getClass().isAnnotationPresent(Table.class)); // false

        System.out.println(o3.isAnnotationPresent(Table.class)); // true

        SomeEntity someEntity = new SomeEntity();//true
        Class<? extends SomeEntity> aClass = someEntity.getClass();
        System.err.println(aClass.isAnnotationPresent(Table.class));
    }
}
