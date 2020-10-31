
## 1.介绍
    
    1.适配器模式(Adapter Pattern)将某个类的接口转换成客户端期望的另一个接口表示，主的目的是兼容性，让原本因接口不匹配不能一起工作的两个类可以协同工作。其别名为包装器(Wrapper)
    2.适配器模式属于结构型模式
    3.主要分为三类：类适配器模式、对象适配器模式、接口适配器模式

## 2.类适配器模式
    
    示例：220V  ->  5v 通过适配器Adapter将220V转5V
    解决：
        class VoltageAdapter extends Voltage220V implements IVoltage5V { 
         //适配器里面将220V转5V【通过继承+接口实现】
         public int output5V() {
                // TODO Auto-generated method stub
                //获取到 220V 电压
                int srcV = output220V();
                int dstV = srcV / 44; //转成 5v
                return dstV;
         }
    }
    9.4.3类适配器模式注意事项和细节
        1)Java 是单继承机制，所以类适配器需要继承 src 类这一点算是一个缺点, 因为这要求 dst 必须是接口，有一定局限性;
        2)src 类的方法在 Adapter 中都会暴露出来，也增加了使用的成本。
        3)由于其继承了 src 类，所以它可以根据需求重写 src 类的方法，使得 Adapter 的灵活性增强了。

## 3.对象适配器模式
    
    介绍：
        1.将继承类改为持有类，解决兼容性问题
        2.根据“合成复用原则”，在系统中尽量使用关联关系（聚合）来替代继承关系。
        3.对象适配器模式是适配器模式常用的一种
    解决：
        class VoltageAdapter implements IVoltage5V {
            private Voltage220V voltage220V; // 关联关系-聚合
            //通过构造器，传入一个 Voltage220V 实例
            public VoltageAdapter(Voltage220V voltage220v) {
                this.voltage220V = voltage220v;
            }
            @Override
            public int output5V() {
                int dst = 0;
                if(null != voltage220V) {
                    int src = voltage220V.output220V();//获取 220V 电压
                    System.out.println("使用对象适配器，进行适配~~"); 
                    dst = src / 44;
                    System.out.println("适配完成，输出的电压为=" + dst);
                }
            return dst;
        }
    对象适配器模式注意事项和细节
        1)对象适配器和类适配器其实算是同一种思想，只不过实现方式不同。
            根据合成复用原则，使用组合替代继承， 所以它解决了类适配器必须继承 src 的局限性问题，也不再要求 dst必须是接口。
        2)使用成本更低，更灵活。

## 4.接口适配器模型
    
    介绍：
        1)一些书籍称为：适配器模式(Default Adapter Pattern)或缺省适配器模式。
        2)核心思路：当不需要全部实现接口提供的方法时，可先设计一个抽象类实现接口，并为该接口中每个方法提供一个默认实现（空方法），
            那么该抽象类的子类可有选择地覆盖父类的某些方法来实现需求
        3)适用于一个接口不想使用其所有的方法的情况。
    示例：-
    使用场景：
        1.原来在公司，一个项目对接多个数据传输平台时，使用此种方式：
            将公有方法实现放在 Adapter中，作为统一使用
            若果有个性化需求只需要重写该方法自己实现即可
        2.在springmvc中的使用：
            1)SpringMvc 中的 HandlerAdapter, 就使用了适配器模式
                //通过适配器，将接口入参中特定的参数或类型，统一转换格式后传入接口，在进入接口之前执行，例如接口入参中有日期类型
                //Date date，但是前端传来的时候是字符串，无法进行赋值，则需要使用此种方式预先转换成日期类型再赋值
                @Configuration 
                public class WebConfig  extends WebMvcConfigurerAdapter{}
            3)使用 HandlerAdapter 的原因分析:
                可以看到处理器的类型不同，有多重实现方式，那么调用方式就不是确定的，如果需要直接调用 Controller 方法，
                需要调用的时候就得不断是使用 if else 来进行判断是哪一种子类然后执行。那么如果后面要扩展 Controller， 就得修改原来的代码，这样违背了 OCP 原则。
    适配器模式的注意事项和细节
        1)三种命名方式，是根据 src 是以怎样的形式给到 Adapter（在 Adapter 里的形式）来命名的。
        2)类适配器：以类给到，在 Adapter 里，就是将 src 当做类，继承
        对象适配器：以对象给到，在 Adapter 里，将 src 作为一个对象，持有接口适配器：以接口给到，在 Adapter 里，将 src 作为一个接口，实现
        3)Adapter 模式最大的作用还是将原本不兼容的接口融合在一起工作。
        4)实际开发中，实现起来不拘泥于我们讲解的三种经典形式
