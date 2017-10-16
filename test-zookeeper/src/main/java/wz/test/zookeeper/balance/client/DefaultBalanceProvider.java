package wz.test.zookeeper.balance.client;

import wz.test.zookeeper.balance.model.Constants;
import wz.test.zookeeper.balance.model.ServerData;
import com.google.common.collect.Lists;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.Collections;
import java.util.List;

/**
 * Created by wangz on 17-4-3.
 */
public class DefaultBalanceProvider extends AbstractBalanceProvider<ServerData> {
    private final String zkServer;
    private final String serverPath;
    private final ZkClient zkClient;

    public DefaultBalanceProvider(String zkServer, String serverPath){
        this.zkServer = zkServer;
        this.serverPath = serverPath;

        this.zkClient = new ZkClient(zkServer, Constants.SESSION_TIME_OUT, Constants.CONNECT_TIME_OUT, new
                SerializableSerializer());
    }

    @Override
    public ServerData getBalanceItem() {
        return null;
    }

    @Override
    protected ServerData balanceAlgorithm(List<ServerData> servers) {
        if(servers != null && servers.size() > 0){
            Collections.sort(servers);
            return servers.get(0);
        }else {
            return null;
        }
    }

    @Override
    protected List<ServerData> getBalanceItems() {
        List<ServerData> serverDatas = Lists.newArrayList();
        List<String> children = zkClient.readData(this.serverPath);
        for(String child : children){
            ServerData serverData = zkClient.readData(serverPath + "/" + child);
            serverDatas.add(serverData);
        }

        return serverDatas;
    }

}
