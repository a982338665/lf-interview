

模拟迁移：-- 会出现锁表，大批量失败问题
    
    insert into order_record select * from order_today where pay_success_time < '2020-03-08 00:00:00';
    
出现原因：
    
    在默认的事务隔离级别下：insert into order_record select * from order_today 加锁规则是：order_record表锁，order_today逐步锁（扫描一个锁一个）。
    分析：查看执行计划可知，order_today扫描的是全表，mysql会从上到下扫描order_today内的记录并且加锁，这样一来不就和直接锁表是一样了。
    
解决方案：-加索引，防止全表扫描
    
    INSERT INTO order_record SELECT * FROM order_today FORCE INDEX (idx_pay_suc_time) WHERE pay_success_time <= '2020-03-08 00:00:00';
    
    
