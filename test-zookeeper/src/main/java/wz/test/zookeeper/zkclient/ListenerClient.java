package wz.test.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import wz.test.zookeeper.zkclient.model.User;

import java.util.List;

/**
 * Created by wangz on 17-3-31.
 */
public class ListenerClient extends BaseClient {

    public ListenerClient() {
        super();
    }

    public void signChildrenListener(String path, IZkChildListener listener) {
        zkClient.subscribeChildChanges(path, listener);
    }

    public void signDataListener(String path, IZkDataListener listener) {
        zkClient.subscribeDataChanges(path, listener);
    }

    public static class ZkChildListener implements IZkChildListener {

        //childrenPaths 为当前节点的所还有子节点(不会监听子节点的子节点)
        @Override
        public void handleChildChange(String parentPath, List<String> childrenPaths) throws Exception {
            System.out.println("====监听到节点发生变化:");
            System.out.println("parent path : " + parentPath);
            System.out.println("children paths : ");
            childrenPaths.forEach(System.out::println);
            System.out.println("====");
        }
    }

    public static class ZkDataListener implements IZkDataListener{

        @Override
        public void handleDataChange(String path, Object obj) throws Exception {
            System.out.println("======= 监听到节点数据发生变化 : ");
            System.out.println("path :" + path);
            System.out.println("obj :" + obj.toString());
            System.out.println("=======");
        }

        //节点被删除时会被调用
        @Override
        public void handleDataDeleted(String path) throws Exception {
            System.out.println("======= 监听到节点被删除 : ");
            System.out.println("path :" + path);
            System.out.println("=======");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ListenerClient client = new ListenerClient();
        //可以监听不存在的节点
        client.signChildrenListener("/temp", new ZkChildListener());
        client.signDataListener("/node_1", new ZkDataListener());
        User user = new User("1", "node_1");
        client.createData("/node_1", user);
        Thread.sleep(5000);
        client.createData("/node_1", user);
        while(true){

        }
    }
}
