package A20200814_bili第二季.code;

/**
 * @author : Mr huangye
 * @URL : CSDN 皇夜_
 * @createTime : 2020/8/24 15:45
 * @Description : 锁中套锁
 */
public class A27_LockDiGui_Synch {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}

//资源类
class Phone{

    /**
     * 发短信
     * @throws Exception
     */
    public synchronized void  sendSMS() throws Exception{
        System.err.println(Thread.currentThread().getId()+"\t send sms!!!");
        sendEmail();
    }

    /**
     * 发邮件
     * @throws Exception
     */
    public synchronized void  sendEmail() throws Exception{
        System.err.println(Thread.currentThread().getId()+"\t send email!!!");
    }
}
