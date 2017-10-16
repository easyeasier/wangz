package zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

/**
 * Created by wangz on 17-3-30.
 */
public abstract class Client<T> {
    protected static ZkClient zkClient;

    public Client() {
        //address, session超时时间, 连接超时时间, 序列化器(可将对象数据直接序列化为字节数组,与zk直接交互)
        zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183", 10000, 10000, new SerializableSerializer
                ());
        //在命令行客户端调用,更新数据监听时,使用这个序列化器才能将结果打印出来(产生什么就输出什么)
//        zkClient = new ZkClient("localhost:2181,localhost:2182,localhost:2183", 10000, 10000, new
//                BytesPushThroughSerializer());
        System.out.println("========connect zookeeper server======");
    }

    abstract String createData(String path, T t);

    abstract T getData(String path);

    abstract List<String> getChildren(String path);

    abstract boolean exist(String path);

    abstract void delete(String path);
}
