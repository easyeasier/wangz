package zkclient;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import zkclient.model.User;

import java.util.List;

/**
 * Created by wangz on 17-3-31.
 */
public class BaseClient extends Client<User> {

    public BaseClient(){
        super();
    }

    //1.创建节点(当重复创建时,会抛出NOdeExistException异常)
    @Override
    public String createData(String path, User user) {
        if(!exist(path)){
            //path(zk的绝对路径),数据(如果是字符串,是str.getBytes()),节点类型(持久类型)
            path = zkClient.create(path, user, CreateMode.PERSISTENT);
            System.out.println("========创建节点 , path : " + path + "======");
        }else{
            zkClient.writeData(path, user);
            System.out.println("========更新节点 , path : " + path + "======");
        }

        return path;
    }



    //2.获取节点数据
    @Override
    public User getData(String path) {
        //读取数据,不带状态
        User user = zkClient.readData(path);
        System.out.println("========读取数据,不带状态=======");
        System.out.println("user : " + user.toString());

        Stat stat = new Stat();
        user = zkClient.readData(path, stat);
        System.out.println("========读取数据,=======");
        System.out.println("user : " + user.toString());
        System.out.println("stat : " + stat);
        return user;
    }

    //3.读取子节点(当节点没有字节点时,返回空数组,而不是null)
    @Override
    public List<String> getChildren(String path) {
        List<String> childrenPaths = zkClient.getChildren(path);
        System.out.println("=======获取子节点=======");
        System.out.println("parent path : " + path);
        System.out.println("chidlren paths : ");
        if (childrenPaths != null) {
            childrenPaths.forEach(System.out::println);
        }
        return childrenPaths;
    }

    //4.路径是否存在
    @Override
    public boolean exist(String path) {
        return zkClient.exists(path);
    }

    //5.删除节点
    @Override
    public void delete(String path) {
        List<String> chidren = getChildren(path);
        if (chidren != null && !chidren.isEmpty()) {
            zkClient.deleteRecursive(path);
            System.out.println("=======循环删除 path : " + path + "=======");
        } else {
            zkClient.delete(path);
            System.out.println("=======单点删除 path : " + path + "=======");
        }
    }

}
