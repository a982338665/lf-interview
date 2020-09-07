
# spring创建对象的过程
    
    1.在Spring中，对象的实例化是通过反射实现的
    2.而对象的属性则是在对象实例化之后通过一定的方式设置的

# 循环依赖实例说明
    
    1.代码：
        @Component
        public class A {
        
          private B b;
        
          public void setB(B b) {
            this.b = b;
          }
        }
        @Component
        public class B {
        
          private A a;
        
          public void setA(A a) {
            this.a = a;
          }
        }
    2.过程：
        从A开始：
            1.创建A对象：
                Spring尝试通过ApplicationContext.getBean()方法获取A对象的实例
                    -无A实例，创建A实例对象
            2.属性赋值A对象：
                -依赖B对象，递归获取B实例
                    -无B实例，创建B实例对象 【创建B对象】
                    -将不完整的A对象（A对象中的属性未赋值）赋值给B实例对象属性【赋值B对象】
                    -此时B对象创建完成，赋值完成，即getBean可返回完整的实例给A对象
                -将完整B对象赋值给A实例属性，完成循环依赖
             
                
