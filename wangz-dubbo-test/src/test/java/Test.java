import dubbo.dubbo.Serveice.Provider;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wangz on 17-4-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class Test {
    @Autowired
    Provider provider;

    @org.junit.Test
    public void dubboTest() throws IOException {
        provider.fun1();
        provider.fun2();

        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
