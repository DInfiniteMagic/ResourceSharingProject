import com.dzk.common.config.Global;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dzk
 * @date 2020/7/4 0:30
 * @description
 */
public class readPropertiesTest {
    @Test
    public void demo1(){
        System.out.println(Global.getInstance().getCongig("productName"));

    }
}
