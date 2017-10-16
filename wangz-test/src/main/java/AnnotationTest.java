import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangz on 17-6-25.
 */
public class AnnotationTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        AnnotationTest test = new AnnotationTest();
        Class clz = test.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for(Method m : methods){
            if(m.isAnnotationPresent(Test.class)){
                System.out.print("Test ANNOTATION : ");
                m.invoke(test);
            }

            if(m.isAnnotationPresent(Deprecated.class)){
                System.out.print("Deprecated ANNOTATION : ");
                m.invoke(test);
            }
        }
    }

    @Deprecated
    @Test
    public void fun1() {
        System.out.println("fun1");
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Test {
    }
}