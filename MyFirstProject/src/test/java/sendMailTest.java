import com.dzk.web.entity.Mail;
import com.dzk.web.entity.Register;
import com.dzk.web.service.mail.SendMail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dzk
 * @date 2020/7/8 20:14
 * @description
 */
public class sendMailTest {
    @Test
    public void demo(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        SendMail sendMail=context.getBean("sendMail",SendMail.class);

        Register register=new Register("1436921968@qq.com","17340525807","mail1111","sdasd","123456789a","123456789a","ASDF",112235);

        sendMail.setRegister(new Mail("账号激活邮件",register.getMailbox(),"sdsd"));
        sendMail.run();


       /* sendMail.setRegister(register);
        ExecutorService pool= Executors.newCachedThreadPool();//创建一个缓存线程池
        pool.execute(sendMail);//将线程放入线程池中进行执行   发送邮件
*/
    }
}
