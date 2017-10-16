package takemaster;

import com.google.common.collect.Lists;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import takemaster.model.RunningData;
import takemaster.server.WorkServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by wangz on 17-4-1.
 */
public class LeaderSelectorZkClient {
    private static final int CLIENT_QTY = 10;
    private static final String ZOOKEEPER_SERVERS = "localhost:2181,localhost:2182,localhost:2183";
    private static List<ZkClient> clients = Lists.newArrayList();
    private static List<WorkServer> workServers = Lists.newArrayList();

    public static void main(String[] args) throws IOException {
        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                ZkClient zkClient = new ZkClient(ZOOKEEPER_SERVERS, 5000, 5000, new SerializableSerializer());
                RunningData serverData = new RunningData(i + "", "#" + i);
                WorkServer workServer = new WorkServer(serverData, zkClient);
                clients.add(zkClient);
                workServers.add(workServer);
                workServer.start();
            }

            System.out.println("敲回车退出...");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("Shut downing.....");
            workServers.forEach(workServer -> workServer.stop());
            clients.forEach(ZkClient::close);
        }

    }
}
