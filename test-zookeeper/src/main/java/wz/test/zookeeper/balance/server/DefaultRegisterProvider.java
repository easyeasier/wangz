package wz.test.zookeeper.balance.server;

import wz.test.zookeeper.balance.model.ZookeeperRegisterContext;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * Created by wangz on 17-4-3.
 */
public class DefaultRegisterProvider implements RegisterProvider {
    @Override
    public void register(Object context) {
        ZookeeperRegisterContext registerContext = (ZookeeperRegisterContext) context;
        String path = registerContext.getPath();
        ZkClient zkClient = registerContext.getZkClient();
        try {
            zkClient.createEphemeral(path, registerContext.getServerData());
        } catch (ZkNoNodeException e) {
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            zkClient.createPersistent(parentPath, true);
            register(registerContext);
        }
    }

    @Override
    public void unRegister(Object context) {

    }
}
