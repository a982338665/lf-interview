
# 为什么阿里巴巴禁止使用Apache Beanutils进行属性的copy？
>阿里巴巴开发手册指出：
    
    【强制】避免用 Apache Beanutils 进行属性的 copy。
     说明：Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意均是浅拷贝。
    
## 1.使用场景
    
    属性复制赋值：拷贝能节省代码量和时间
    
## 2.常用工具
    
    1.Spring BeanUtils 
    2.Cglib BeanCopier 
    3.Apache BeanUtils 
    4.Apache PropertyUtils 
    5.Dozer

## 3.代码举例

    综上，我们基本可以得出结论，在性能方面，Spring BeanUtils和Cglib BeanCopier表现比较不错，而Apache PropertyUtils、Apache BeanUtils以及Dozer则表现的很不好。
    所以，如果考虑性能情况的话，建议大家不要选择Apache PropertyUtils、Apache BeanUtils以及Dozer等工具类。
    很多人会不理解，为什么大名鼎鼎的Apache开源出来的的类库性能确不高呢？这不像是Apache的风格呀，这背后导致性能低下的原因又是什么呢？
    其实，是因为Apache BeanUtils力求做得完美, 在代码中增加了非常多的校验、兼容、日志打印等代码，过度的包装导致性能下降严重。
