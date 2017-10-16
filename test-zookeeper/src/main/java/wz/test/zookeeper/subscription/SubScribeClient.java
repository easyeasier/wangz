package wz.test.zookeeper.subscription;

import com.google.common.collect.Lists;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import wz.test.zookeeper.subscription.model.ServerData;
import wz.test.zookeeper.subscription.server.ManageServer;
import wz.test.zookeeper.subscription.server.WorkServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by wangz on 17-4-2.
 */

/**
 * 注册订阅程序入口
 * 注意点:
 *  1.临时节点不能有子字节点.在临时节点上创建子节点,抛出 "NoChildrenForEphemerals"
 *  2.程序中的ZKdataListener 必须和命令行中的统一,这样datalistener才能有效
 */
public class SubScribeClient {
    private static final String ZOOKEEPER_SERVERS = "localhost:2181,localhost:2182,localhost:2183";
    private static final String CONFIG_PATH = "/config";
    private static final String SERVER_PATH = "/server";
    private static final String MANAGE_PATH = "/manage";
    private static List<ZkClient> zkClients = Lists.newArrayList();

    public static void main(String[] args) throws IOException, InterruptedException {
        ZkClient manageClient = new ZkClient(ZOOKEEPER_SERVERS, 5000, 5000, new BytesPushThroughSerializer());
//        ZkClient manageClient = new ZkClient(ZOOKEEPER_SERVERS, 5000, 5000);
        //默认SerializableSerializer序列化器
        ManageServer manageServer = new ManageServer(manageClient, CONFIG_PATH, SERVER_PATH, MANAGE_PATH);
        zkClients.add(manageClient);
        manageServer.start();
        try {
            for (int i = 1; i <= 5; i++) {
                ZkClient serverClient = new ZkClient(ZOOKEEPER_SERVERS, 5000, 5000, new BytesPushThroughSerializer());
//                ZkClient serverClient = new ZkClient(ZOOKEEPER_SERVERS, 5000, 5000);
                ServerData serverData = new ServerData("localhost_" + i, "server_" + i);
                WorkServer workServer = new WorkServer(serverClient, CONFIG_PATH, SERVER_PATH, serverData);
                zkClients.add(serverClient);
                workServer.start();
            }


            //用程序出发dadalistner
            Thread.sleep(5000);
//            manageClient.writeData(MANAGE_PATH, "update");
            manageClient.writeData(MANAGE_PATH, "update".getBytes());
            Thread.sleep(1000);
//            manageClient.writeData(MANAGE_PATH, "list");
            manageClient.writeData(MANAGE_PATH, "list".getBytes());
            Thread.sleep(1000);
//            manageClient.writeData(MANAGE_PATH, "aaaa");
            manageClient.writeData(MANAGE_PATH, "aaaa".getBytes());

            new BufferedReader(new InputStreamReader(System.in)).readLine();


        } finally {
//            workServers.forEach(workServer -> workServer.stop());
//            manageClient.close();
        }

    }
}
