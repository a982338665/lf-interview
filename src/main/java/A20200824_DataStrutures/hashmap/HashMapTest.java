package A20200824_DataStrutures.hashmap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by lishengbo 2018/7/4
 *
 * 0.HashMap的key和value可为null
 * 1.HashMap底层实现数组+链表
 * 2.默认初始容量：16(即下标0-15)
 * 3.加载因子：0.75f
 * 4.扩容时机：当hashMap的容量达到总容量的0.75时(16*0.75=12)扩容为原来两倍（2*16=32），下次使用容量达到为32*0.75=24时，扩容为32*2=64
 *
 * 5.相同的HashCode，相同的key，在put时，新值覆盖旧值，并返回旧值,不会存在链表中
 * 6.相同的hashcode，不同的key，Hash冲突,Hash冲突后，那么HashMap的单个bucket里存储的不是一个 Entry，而是一个 Entry 链。
 *
 * 7.系统只能必须按顺序遍历每个 Entry，直到找到想搜索的 Entry 为止——如果恰好要搜索的 Entry 位于该 Entry 链的最末端
 *  （该 Entry 是最早放入该 bucket 中），那系统必须循环到最后才能找到该元素。
 * 8.HashMap不是线程安全的
 * 9.获取线程安全的hashMap:
 *      ·Map map = Collections.synchronizedMap(new HashMap());
 *      ·ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<String, String>();
 *      ·HashTable的性能要比以上两种差，以上两种用了分段锁
 * 10.HashMap的容量必须为2的指数倍，最大容量为2^30
 * 11.HashMap实际就是一个Entry数组：Entry<K,V> [],单个Entry<K,V>对象有持有链表的下一个对象Entry<K,V>及当前key,value,hash值
 * 12.Entry是单向链表。它是 “HashMap链式存储法”对应的链表。
 * ——————————————————————————————————————————————————————————————————————
 * 源码分析:
 * 1.关键属性：
 *  transient Entry<K,V>[] table;//存储元素的实体数组
 3  transient int size;//存放元素的个数
 5  int threshold; //临界值   当实际大小超过临界值时，会进行扩容threshold = 加载因子*容量
 7  final float   loadFactor; //加载因子表示Hsah表中元素的填满的程度.
 9  transient int modCount;//被修改的次数
 *
 * 2.加载因子越大,填满的元素越多,好处是,空间利用率高了,但:冲突的机会加大了.链表长度会越来越长,查找效率降低--时间以空间的取舍
 * 3.Entry<K,V>[]数组的索引值是根据hash值和数组长度算出的：hash &(length-1)--此计算保证算出来的索引是在数组大小范围内（除法散列法）
 * 4.对哈希表的散列很自然地会想到用hash值对length取模（即除法散列法），Hashtable中也是这样实现的，
 *  这种方法基本能保证元素在哈希表中散列的比较均匀，但取模会用到除法运算，效率很低，HashMap中则通过h&(length-1)的方法来代替取模，
 *  同样实现了均匀的散列，但效率要高很多，这也是HashMap对Hashtable的一个改进
 *  (当length总是2的n次方时，h& (length-1)运算等价于对length取模，也就是h%length，但是&比%具有更高的效率。)
 * 5.为什么hash表的容量一定为2的整数次幂：
 *      1.length为2的整数次幂的话，h&(length-1)就相当于对length取模，这样便保证了散列的均匀，同时也提升了效率
 *      2.length为2的整数次幂的话，为偶数，这样length-1为奇数，奇数的最后一位是1，这样便保证了h&(length-1)的最后一位可能为0，
 *          也可能为1，，即与后的结果可能为偶数，也可能为奇数，这样便可以保证散列的均匀性
 *      3.如果length为奇数的话，很明显length-1为偶数，它的最后一位是0，这样h&(length-1)的最后一位肯定为0，即只能为偶数，
 *          这样任何hash值都只会被散列到数组的偶数下标位置上，这便浪费了近一半的空间，因此，length取2的整数次幂，
 *          是为了使不同hash值发生碰撞的概率较小，这样就能使元素在哈希表中均匀地散列
 * ————————————————————————————————————————————————————————————————————————
 *    根据上面 put 方法的源代码可以看出，当程序试图将一个key-value对放入HashMap中时，程序首先根据该 key 的 hashCode()
 *    返回值决定该 Entry 的存储位置：如果两个 Entry 的 key 的 hashCode() 返回值相同，那它们的存储位置相同。
 *    如果这两个 Entry 的 key 通过 equals 比较返回 true，新添加 Entry 的 value 将覆盖集合中原有 Entry 的 value，
 *    但key不会覆盖。如果这两个 Entry 的 key 通过 equals 比较返回 false，新添加的 Entry 将与集合中原有Entry形成Entry链，
 *    而且新添加的 Entry 位于 Entry 链的头部——具体说明继续看 addEntry() 方法的说明。
    2.//如果要加入的位置有值，将该位置原先的值设置为新entry的next,也就是新entry链表的下一个节点

 *  3.扩容过程：
 *      ·默认情况下，数组大小为16，那么当HashMap中元素个数超过16*0.75=12的时候，就把数组的大小扩展为 2*16=32，即扩大一倍，
 *       然后重新计算每个元素在数组中的位置，扩容是需要进行数组复制的，复制数组是非常消耗性能的操作，
 *       所以如果我们已经预知HashMap中元素的个数，那么预设元素的个数能够有效的提高HashMap的性能。
 *      ·新建了一个HashMap的底层数组(即Entry<K,V>[])，调用transfer方法，
 *       将HashMap的全部元素添加到新的HashMap中,并重新计算元素在新的数组中的索引位置
 *      ·-当HashMap中的元素越来越多的时候，hash冲突的几率也就越来越高，因为数组的长度是固定的。
 *       所以为了提高查询的效率，就要对HashMap的数组进行扩容，数组扩容这个操作也会出现在ArrayList中，
 *       这是一个常用的操作，而在HashMap数组扩容之后，最消耗性能的点就出现了：原数组中的数据必须重新计算其在新数组中的位置，
 *       并放进去，这就是resize。
 *      ·单向列表向后遍历查询value-效率低
 *  ————————————————————————————————————————————————————————————————————————
 *  1.LinkedHashMap：LinkedHashMap是HashMap的一个子类，保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，
 *      先得到的记录肯定是先插入的，也可以在构造时带参数，按照访问次序排序。
 *  2.TreeMap：TreeMap实现SortedMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序，也可以指定排序的比较器，
 *      当用Iterator遍历TreeMap时，得到的记录是排过序的。如果使用排序的映射，建议使用TreeMap。在使用TreeMap时，
 *      key必须实现Comparable接口或者在构造TreeMap传入自定义的Comparator，
 *      否则会在运行时抛出java.lang.ClassCastException类型的异常。
 *  ————————————————————————————————————————————————————————————————————————
 *  Hash算法本质上就是三步：取key的hashCode值、高位运算、取模运算。
 *  相比于JDK1.8--加入红黑树概念优化：
 *      1.当链表长度大于8时转换为红黑树
 *
 */
public class HashMapTest
{

    public static void main(String[] args) {
        Map<String,String > map=new HashMap<String,String >();
        String s= map.put("10", "xxx");
        String s1 = map.put("10", "xxx2");
        System.out.println(map.get("10"));




        System.out.println(s+"|"+s1);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();

            System.out.println(next.getKey()+"|"+next.getValue());
        }


        System.out.println(map.remove("10"));
        System.out.println(map.get("10"));
        System.out.println(map.size());


        Map map2 = Collections.synchronizedMap(new HashMap());
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<String, String>();
    }
}
