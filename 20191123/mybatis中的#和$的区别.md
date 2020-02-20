
**区别**：
    
    1.#只为占位符
        ·mapper中sql:
          select * from user where id = #{id}  
        ·正常传参获取sql日志打印为：参数：18
          Preparing: select * from user where id = ?
          Parameters: 18(Integer)  
        ·注入传参获取sql日志打印为：参数：18 or id = 16
          Preparing: select * from user where id = ?
          Parameters: 18 or id = 16 (Integer)  
    2.$直接拼接字符串:
        ·mapper中sql:
          select * from user where id = ${id}  
        ·正常传参获取sql日志打印为：参数：18
          select * from user where id = 18 
        ·注入传参获取sql日志打印为：参数：18 or id = 16
          select * from user where id = 18 or id = 16
    3.综上可知：
        #号-无论参数是什么都会作为一个字符串传进去，所以不可能会被sql注入
        $号-并不会有占位符，所以会被直接拼接，明显被注入
