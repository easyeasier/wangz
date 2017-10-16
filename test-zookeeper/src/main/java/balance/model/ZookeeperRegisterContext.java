package balance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by wangz on 17-4-3.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZookeeperRegisterContext {
    String path;
    ServerData serverData;
    ZkClient zkClient;
}
