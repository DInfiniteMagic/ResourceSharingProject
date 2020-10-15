import com.dzk.common.utils.FileUtils;
import com.dzk.common.utils.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;


/**
 * @author dzk
 * @date 2020/7/8 19:47
 * @description
 */
public class CommonTest {
    @Test
    public void Demo(){
        System.out.println("qwert".equalsIgnoreCase("QWERT"));
    }
    @Test
    public void Demo1(){
        Calendar cal=Calendar.getInstance();
        System.out.println(StringUtils.dataChange(cal.getTime(),"yyyy/MM/dd-HH:mm:ss"));
        System.out.println(StringUtils.dataToString(cal));
    }

    @Test
    public void Demo2(){
        System.out.println(new Date(new java.util.Date().getTime()));
    }
    @Test
    public void Demo3(){
        System.out.println(Math.ceil(5/2));
    }
     @Test
    public void Demo4() throws IOException {
        File file =new File("E:\\MyFirstProjectFile\\uploadFile\\adminManager\\2020720073");
         FileUtils.deleteDirectory(file);
         System.out.println(file.exists());
    }
    @Test
    public void Demo5() throws IOException {
       System.out.println("----------------------------------------------------------------------------");
       ApplicationContext context=new ClassPathXmlApplicationContext("SpringConfig.xml");
        for (String s: context.getBeanDefinitionNames()
             ) {
            System.out.println(s);
        }
        System.out.println("----------------------------------------------------------------------------");
    }
}
