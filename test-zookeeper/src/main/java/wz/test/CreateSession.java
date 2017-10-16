package wz.test;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by wangz on 17-3-27.
 */
public class CreateSession implements Watcher {
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        //1.建立链接, url,timeout,watcher
        zooKeeper = new ZooKeeper("localhost:2182", 5000, new CreateSession());
        //2.异步链接，所以开始会输出 “CONNECTING”
        System.out.println(zooKeeper.getState());
        Thread.sleep(5000);
        //3.连接建立后状态“CONNECTED”
        System.out.println(zooKeeper.getState());
    }

    //4.监听事件，链接建立后，输出“收到事件：WatchedEvent state:SyncConnected type:None path:null”
//    @Override
//    public void process(WatchedEvent event) {
//        System.out.println("收到事件："+event);
//    }

    @Override
    public void process(WatchedEvent event) {
        //5.当确定连接建立后，doSomething
        if (event.getState() == Event.KeeperState.SyncConnected) {
            doSomething();
        }
    }

//
//    private void doSomething() {
//        try {
//            //6.同步创建节点：节点路径，value,acl,持久类型（-s -e)
//            String path = zooKeeper.create("/node_2", "234".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode
//                    .PERSISTENT);
//            System.out.println("retrun path:" + path);
//        } catch (KeeperException e) { //当节点存在时，抛出异常
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void doSomething() {

        //7.异步创建节点：节点路径，value,acl,持久类型（-s -e),异步回调，上下文（Object类型，在回调时回传回来）
        zooKeeper.create("/node_4", "14".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,
                new IStringCallBack(), "创建");

    }

    //异步创建需要实现的callback接口
    static class IStringCallBack implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            StringBuilder sb = new StringBuilder();
            sb.append("rc=" + rc).append("\n");
            sb.append("path=" + path).append("\n");
            sb.append("ctx=" + ctx.toString()).append("\n");
            sb.append("name=" + name).append("\n");
            System.out.println(sb.toString());
        }
    }
}

