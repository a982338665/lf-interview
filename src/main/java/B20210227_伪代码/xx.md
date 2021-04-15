
@RestController
@RequestMapping("/bank/account")
@SLf4j
public class account{

   @Resource(name="amountServiceImpl")
   private AmountService amountService;
   
   @Autowired
   private Redisson redisson;
   
   @Autowired
   private RedisService redisService;
   
   public HashMap<String,Map<String,Object>> map = new HashMap();
   
   @PostMapping("/cost.json")
   @Transaction
   public boolean kouAmount(@RequestBody RequestData requestData){
     RLock rdsLock = redisson.getLock(requestData.getUserAccount());
     try{
       rdslock.lock();
       Map<String,Object> obj = map.get("cost.json");
       if(StringUtils.isEmpty(obj)){
            Map<String,Object> map2 = new Hashmap<>()
            map2.put("firstdate",new Date());
            map2.out("limit",1);
            map.put("cost.json",map2);
       }else{
            Date date = obj.get("firstdate");
            Date last = new Date();
            long jiange = last.currentMills()-date.currentMills()
            if(jiange>1000){
                int num = obj.get("limit");
                int currentI = num+1
                if(currentI>300){
                    return "已限流";
                }else{
                    ...
                }
            }else{
                ...
            }
       }
       //幂等性考虑，防止
       String requestKey = requestData.getRequestKey();
       Boolean isVerify = redisService.checkToken(requestKey);
       if(isVerify){
           redisService.del(requestKey);
           //考虑到数据库本身有行锁，原子性就不需要使用分布式锁
           int x = amountService.updateAmount(requestData);
           return x>0?true:false;
       }else{
            log.error("无有效token！")
            return false;
       }
     }catch(Exception e){
        log.error(e)
        return false;
     }finally{
       rdsLock.unlock()  
     }
   }
}


public interface AmountService{
   int updateAmount(RequestData requestData);
}

@Service
public class AmountServiceImpl{

   @Resource
   AmountDao amountDao; 
    
   public int updateAmount(RequestData requestData){
     amountDao.updateAmount(requestData);
   };
}

@Mapper
public class AmountDao{
    @Update("update user_account set amount= amount-#{BigDecimalcost} where userAccount = #{userAccount}")
    public int updateAmount(RequestData requestData);
}

@Data
public class RequestData{   
  //用户账户
  private String userAccount,
  //扣款金额
  private String BigDecimalcost，
  //幂等Key
  private  String requestKey
}
