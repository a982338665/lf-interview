
#https://www.cnblogs.com/tietazhan/articles/5771886.html
**1.实例方法静态方法区别：**
    
    静态方法针对于类，不用实例化对象即可使用
    实例方法针对于对象，new 对象后可调用方法
    
**2.java异常分类：**
    
    1.检出异常：编译时无法通过 io sqlException 需要try-catch
    2.非检出异常：可编译，运行时报错 RuntimeException
    
**3.常用集合类：list如何排序：**

    1.Set接口:
        --1.所有set接口通用性质:
            -1.不重复：两个相同元素加入同一个集合中，add方法返回false
            -2.判断Set对象是否重复:是equal方法,而不是==,即两个对象如果equals返回true，则不接收
        --2.TreeSet:
        --3.HashSet:
            -1.无序
            -2.不同步
            -3.集合元素可为null,但只能有一个
            -4.存值:根据hashCode值来决定该对象在HashSet中存储位置
        --4.LinkedHashSet:
            -1.存值:据元素的hashCode值来决定元素的存储位置
            -2.维护:使用链表维护元素的次序,即按顺序保存-->遍历时会以添加顺序展示
    2.List接口:
        ArrayList,
        LinkedList,
        Vector(线程安全)。
    3.排序:
        JDK7以前用collections.sort(list,Comparator).
        JDK8直接用List.sort(Comparator).
    