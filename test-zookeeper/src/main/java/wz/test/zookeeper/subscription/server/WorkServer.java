package wz.test.zookeeper.subscription.server;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import wz.test.zookeeper.subscription.model.ServerConfig;
import wz.test.zookeeper.subscription.model.ServerData;

/**
 * Created by wangz on 17-4-2.
 */
@Getter
@Setter
public class WorkServer {
    private String configPath;
    private String serverPath;
    private ServerData serverData;
    private ServerConfig serverConfig;
    private ZkClient zkClient;
    private IZkDataListener configListener;

    public WorkServer(ZkClient zkClient, String configPath, String serverPath, ServerData serverData) {
        this.configPath = configPath;
        this.serverPath = serverPath;
        this.serverData = serverData;
        this.zkClient = zkClient;

        this.configListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object obj) throws Exception {
                System.out.println("监听到config 发生变化........");
                String configJson = new String((byte[]) obj);
//                String configJson = (String) obj;

                serverConfig = JSON.parseObject(configJson, ServerConfig.class);
                updateConfig(serverConfig);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        };

    }

    public void start() {
        System.out.println("Workserver #" + serverData.getName() + " start....");
        initRunning();
    }

    public void initRunning() {
        registerDataListener();
        createData();
        readConfigData();
    }

    public void createData() {
        String path = serverPath + "/" + serverData.getName();
        try {
            zkClient.createEphemeral(path, JSON.toJSONString(serverData).getBytes());
//            zkClient.createEphemeral(path, JSON.toJSONString(serverData));
            System.out.println("create server data : " + serverData.toString());
        } catch (ZkNoNodeException e) {
            System.out.println("server node is not exist, now create server node firstly");
            zkClient.createPersistent(serverPath, "server");
            createData();
        }
    }

    public void registerDataListener() {
        System.out.println("register data listener...");
        zkClient.subscribeDataChanges(configPath, configListener);
    }

    public void stop() {
        zkClient.unsubscribeAll();
        zkClient.delete(configPath);
        System.out.println("stop work server : #" + serverData.getName() + "....");
    }

    private ServerConfig readConfigData() {
        if (serverConfig == null) {
//            String configJson = zkClient.readData(configPath);
            String configJson = new String ((byte[]) zkClient.readData(configPath));
            serverConfig = JSON.parseObject(configJson, ServerConfig.class);
            updateConfig(serverConfig);
        }

        return serverConfig;
    }

    //当config数据发生变化是,调用的逻辑
    public void updateConfig(ServerConfig serverConfig) {
        System.out.println(serverConfig);
    }

}
