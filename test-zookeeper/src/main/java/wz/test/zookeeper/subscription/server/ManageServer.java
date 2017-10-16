package wz.test.zookeeper.subscription.server;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import wz.test.zookeeper.subscription.model.ServerConfig;

import java.util.List;

/**
 * Created by wangz on 17-4-2.
 */
@Getter
@Setter
public class ManageServer {
    private String configPath;
    private String serverPath;
    private ZkClient zkClient;
    private IZkChildListener serverListener;
    private IZkDataListener manageListener;
    private List<String> servers = Lists.newArrayList();
    private String managePath;

    public ManageServer(ZkClient zkClient, String configPath, String serverPath, String managePath) {
        this.configPath = configPath;
        this.serverPath = serverPath;
        this.zkClient = zkClient;
        this.managePath = managePath;

        this.serverListener = new IZkChildListener() {
            @Override
            public void handleChildChange(String path, List<String> list) throws Exception {
                System.out.println("servers 发生变化....");
                servers = list;
                list();
            }
        };

        this.manageListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                String order = new String((byte[]) o);
//                String order =(String) o;
                System.out.println("接收到指令:" + order);
                execute(order);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        };

    }

    private void execute(String order) {
        switch (order) {
            case "list":
                list();
                break;
            case "create":
                create();
                break;
            case "update":
                update();
                break;
            default:
                System.out.println("命令错误......");
        }
    }

    public void start() {
        zkClient.createEphemeral(managePath, "list".getBytes());
//        zkClient.createEphemeral(managePath, "list");

        subscribeManageListener();
        subscribeServerListener();
        create();
    }


    private void subscribeManageListener() {
        zkClient.subscribeDataChanges(managePath, manageListener);
    }

    private void subscribeServerListener() {
        zkClient.subscribeChildChanges(serverPath, serverListener);
    }

    public void createConfig(ServerConfig config) {
        System.out.println("创建config...." + config.toString());
            zkClient.createEphemeral(configPath, JSON.toJSONString(config).getBytes());
//        zkClient.createEphemeral(configPath, JSON.toJSONString(config));

    }

    public void updateConfig(ServerConfig config) {
        System.out.println("更新config...." + config.toString());
        zkClient.writeData(configPath, JSON.toJSONString(config).getBytes());
//        zkClient.writeData(configPath, JSON.toJSONString(config));
    }

    public void list() {
        System.out.println("execute list order : ");
        servers.forEach(System.out::println);
    }

    public void create() {
        System.out.println("execute create order :");
        ServerConfig config = new ServerConfig("database_host_1", "db_pwd");
        createConfig(config);
    }

    public void update() {
        System.out.println("execute update order ....");
        ServerConfig config = new ServerConfig("database_host_1_new", "db_pwd");
        updateConfig(config);
    }


}
