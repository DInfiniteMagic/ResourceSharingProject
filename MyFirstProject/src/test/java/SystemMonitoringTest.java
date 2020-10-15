
import com.dzk.web.service.SystemManagementService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dzk
 * @date 2020/7/26 21:26
 * @description
 */
public class SystemMonitoringTest {
    @Test
    public void demo1() {
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(context.getBean("systemManagementServiceImpl", SystemManagementService.class).getSystemMonitoring());
        System.out.println("----------------------------------------------------------------------------");
    }
      @Test
    public void demo2() {
        ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        System.out.println("----------------------------------------------------------------------------");
        context.getBean("systemManagementServiceImpl", SystemManagementService.class).autoSavaSystemMonitory();

        System.out.println("----------------------------------------------------------------------------");
    }
}
