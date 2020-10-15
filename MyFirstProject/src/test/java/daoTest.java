import com.dzk.common.utils.EncryptionUtils;
import com.dzk.web.entity.Encryption;
import com.dzk.web.entity.Register;
import com.dzk.web.service.AccountService;
import com.dzk.web.service.ResourceService;
import com.dzk.web.service.SystemManagementService;
import lombok.val;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dzk
 * @date 2020/7/4 22:05
 * @description
 */
public class daoTest {
    @Test
    public void demo1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println(context.getBean("accountServiceImpl", AccountService.class).getAccountByUsername("董志坤"));
        System.out.println(context.getBean("accountServiceImpl", AccountService.class).getAccountByUsername("董志坤"));
    }
    @Test
    public void demo2(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        Register register = new Register("1436921968@qq.com","17340525807","DInfiniteMagic","独照","123456a","123456a",null,123456);
        context.getBean("accountServiceImpl", AccountService.class).registerAccount(register);
        //context.getBean("accountServiceImpl", AccountService.class).registerAccount(register);
    }
    @Test
    public void demo3(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("accountServiceImpl", AccountService.class).forgetUsername("1436921968@qq.com"));
        System.out.println("----------------------------------------------------------------------------");

    }

    @Test
    public void demo4(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("accountServiceImpl", AccountService.class).getMailById(4));
        System.out.println("----------------------------------------------------------------------------");

    }
    @Test
    public void demo5(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        Encryption encryption= EncryptionUtils.encryptionByMd5("12311111");//密码加密
        context.getBean("accountServiceImpl", AccountService.class).modifyPasswordById(6,encryption);//更改密码
        System.out.println("----------------------------------------------------------------------------");

    }
    @Test
    public void demo6(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("accountServiceImpl", AccountService.class).getSubjectRoles("adminManager"));
        System.out.println("----------------------------------------------------------------------------");

    }

    @Test
    public void demo7(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("resourceServiceImpl", ResourceService.class).getInitDownloadTableData(1,6,"","id","asc","未审核"));
        System.out.println("----------------------------------------------------------------------------");

    }

    @Test
    public void demo8(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("resourceServiceImpl", ResourceService.class).getAllResourcesClass());
        System.out.println("----------------------------------------------------------------------------");
    }


    @Test
    public void demo9(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("resourceServiceImpl", ResourceService.class).getInitUploadTableData(1,6,"","asc","adminManager","未审核"));
        System.out.println("----------------------------------------------------------------------------");
    }

     @Test
    public void demo10(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("resourceServiceImpl", ResourceService.class).findUploadFileUrlById(1));
        System.out.println("----------------------------------------------------------------------------");
    }
     @Test
    public void demo11(){
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("systemManagementServiceImpl", SystemManagementService.class).getHistorySystemMonitor());
        System.out.println("----------------------------------------------------------------------------");
    }
}
