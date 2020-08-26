
# 闭包的意思：将一个函数作为参数传递
    
    lambda表达式，等同于大多说动态语言中常见的闭包、匿名函数的概念。其实这个概念并不是多么新鲜的技术，在C语言中的概念类似于一个函数指针，这个指针可以作为一个参数传递到另外一个函数中。
    由于Java是相对较为面向对象的语言，一个Java对象中可以包含属性和方法（函数），方法（函数）不能孤立于对象单独存在。这样就产生了一个问题，
    有时候需要把一个方法（函数）作为参数传到另外一个方法中的时候（比如回调功能），就需要创建一个包含这个方法的接口，传递的时候传递这个接口的实现类，一般是用匿名内部类的方式来。
    如下面代码，首先创建一个Runnable的接口，在构造Thread时，创建一个Runnable的匿名内部类作为参数：
        Java代码 
        new Thread(new Runnable() {  
            public void run() {  
                    System.out.println("hello");  
                }  
        }).start();  
    缺点：代码笨重，可读性差，不能引用外面的非final的变量等。lambda表达式就是为了解决这类问题而诞生的。
    在介绍Java8中的Lambda表达式之前，首先介绍一个概念叫“函数式接口”（functional interfaces）。对于任意一个Java接口，
    如果接口中只定义了唯一一个方法，那么这个接口就称之为“函数式接口”。比如JDK中的ActionListener、Runnable、Comparator等接口。
         先来看一下Java8中的lambda表达式的使用示例：
         创建一个线程：
    Java代码 
    new Thread(() -> {System.out.println("hello");}).start();
