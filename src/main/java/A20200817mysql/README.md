
## 1.行转列
    
    -- 建表
    CREATE TABLE `test_score` (
      `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
      `subject` varchar(255) COLLATE utf8_bin DEFAULT NULL,
      `score` varchar(255) COLLATE utf8_bin DEFAULT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
    
    -- init数据
    INSERT INTO `zaojiao`.`test_score` (`user_name`, `subject`, `score`) VALUES ('user1', '数学', '80');
    INSERT INTO `zaojiao`.`test_score` (`user_name`, `subject`, `score`) VALUES ('user1', '语文', '83');
    INSERT INTO `zaojiao`.`test_score` (`user_name`, `subject`, `score`) VALUES ('user2', '数学', '45');
    INSERT INTO `zaojiao`.`test_score` (`user_name`, `subject`, `score`) VALUES ('user2', '语文', '50');
    
    -- 展示前
    -- user1	数学	80
    -- user1	语文	83
    -- user2	数学	45
    -- user2	语文	50
    -- 转换为以下格式的数据 行转列
    -- user_name 数学 语文
    -- user1			80	 83
    -- user2			45	 50
    
    SELECT user_name,
          MAX(CASE Subject WHEN '语文' THEN Score ELSE 0 END) AS '语文',
          MAX(CASE Subject WHEN '数学' THEN Score ELSE 0 END) AS '数学',
          MAX(CASE Subject WHEN '英语' THEN Score ELSE 0 END) AS '英语',
          MAX(CASE Subject WHEN '生物' THEN Score ELSE 0 END) AS '生物',
                SUM(score) AS TOTAL 
    FROM test_score
    GROUP BY user_name
    UNION
    SELECT 'total',
          sum(CASE Subject WHEN '语文' THEN Score ELSE 0 END) AS '语文',
          sum(CASE Subject WHEN '数学' THEN Score ELSE 0 END) AS '数学',
          sum(CASE Subject WHEN '英语' THEN Score ELSE 0 END) AS '英语',
          sum(CASE Subject WHEN '生物' THEN Score ELSE 0 END) AS '生物',
                SUM(score) AS TOTAL 
    FROM test_score
    ;
    SELECT user_name,
          MAX(if(subject='语文',Score,0)) AS '语文',
          MAX(if(subject='数学',Score,0)) AS '数学',
          MAX(if(subject='英语',Score,0)) AS '英语',
          MAX(if(subject='生物',Score,0)) AS '生物',
                SUM(score) AS TOTAL 
    FROM test_score
    GROUP BY user_name;


