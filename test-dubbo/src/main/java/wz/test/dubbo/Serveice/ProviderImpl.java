package wz.test.dubbo.Serveice;

import org.springframework.stereotype.Service;

/**
 * Created by wangz on 17-4-5.
 */
@Service
public class ProviderImpl implements Provider{
    @Override
    public void fun1(){
        System.out.println("provider fun1 .....");
    }

    @Override
    public void fun2(){
        System.out.println("provider fun2 .....");
    }
}
