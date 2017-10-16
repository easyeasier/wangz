package wz.test.jdk.sourcecode;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangz on 17-7-19.
 */
public class ArrayListTest {
    public static void main(String[] args) {
        ArrayListTest.toArrayTest();
    }

    /**
     * ArrayList.ensureCapacity测试:
     * 扩展容量,当有大量的数据存储时,先扩展容量
     * 内代码实现是,将底层数组,复制到新的大容量数组中
     * <p>
     * 1000W量次的时候差距开始显现出来了
     */
    public static void ensureCapacityTest() {
        final int N = 10000000;
        Object obj = new Object();

        //1.调用ensureCapacity()方法初始化ArrayList对象
        ArrayList list = new ArrayList();
        long startTime = System.currentTimeMillis();
        list.ensureCapacity(N);//预先设置list的大小
        for (int i = 0; i <= N; i++) {
            list.add(obj);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("调用ensureCapacity()方法所用时间：" + (endTime - startTime) + "ms"); //80ms


        //2.没用调用ensureCapacity()方法初始化ArrayList对象
        list = new ArrayList();
        startTime = System.currentTimeMillis();
        for (int i = 0; i <= N; i++) {
            list.add(obj);
        }
        endTime = System.currentTimeMillis();
        System.out.println("没有调用ensureCapacity()方法所用时间：" + (endTime - startTime) + "ms"); //339ms
    }

    /**
     * ArrayList 的clone是浅拷贝(只复制指针,不重建地址)
     * 另外发散 : Person可以实现clone,用深拷贝实现,在调用clone的时候,生成新的对象
     */
    public static void cloneTest() {
        ArrayList<Person> list = Lists.newArrayList(new Person(1), new Person(2));
        ArrayList<Person> listClone = (ArrayList) list.clone();
        list.get(0).id = 3;
        System.out.println(listClone.get(0).id); //3
    }

    @AllArgsConstructor
    public static class Person{
        public int id;
        @Override
        public String toString(){
            return "id="+id;
        }
    }

    /**
     * ArrayList.subList()的返回类型是内部类SubList类型的(继承自AbstractList,而不是ArrayList)
     *  而且sublist中数据与父list是同一组数据,父list改变,sublist也改变/
     *  (SubList的内有offset标记,用于标记在父list数组中的起始位置,其他数据操作都是在这个offset的基础上计算的)
     */
    public static void subListTest(){
        List<String> list = Lists.newArrayList("1","2","3");
        List<String> subList = list.subList(0, 3);
        try{
            ArrayList<String> subListN = (ArrayList<String>) subList;
        }catch (ClassCastException e){
            System.out.println("subList 不是 ArrayList类型的...");
        }

        list.set(1,"4");
        System.out.println(subList.get(1)); //4
    }

    /**
     * toArray时list内数组的浅拷贝,其对象的改变时,也会改变list内对象
     * String其内属性value是final的,所以改变其值,实质是更新了她的指针,自然在原list中不会改变其值
     */
    public static void toArrayTest(){
        List<String> list = Lists.newArrayList("1", "2","3");
//        List<Person> list = Lists.newArrayList(new Person(1), new Person(2),new Person(3));
        Object[] arr = list.toArray();
//        ((Person)arr[1]).id = 3;
         arr[1] = "3";

        System.out.println(list); //1,3,3
        for(Object o : arr){
            System.out.println(o.toString()); //1,3,3
        }
    }
}
