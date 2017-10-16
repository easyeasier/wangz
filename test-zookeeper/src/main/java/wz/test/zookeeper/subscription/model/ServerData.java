package wz.test.zookeeper.subscription.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wangz on 17-4-2.
 */
@Getter
@Setter
public class ServerData implements Serializable {
    private String address;
    private String name;

    public ServerData(){}
    public ServerData(String host, String name) {
        this.name = name;
        this.address = host;
    }

    @Override
    public String toString() {
        return "address=" + address + ",name=" + name;
    }
}
