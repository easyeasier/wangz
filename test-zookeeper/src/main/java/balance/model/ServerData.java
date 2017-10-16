package balance.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wangz on 17-4-3.
 */
@Getter
@Setter
public class ServerData implements Serializable, Comparable <ServerData>{
    private Integer balance;
    private String host;
    private Integer port;


    @Override
    public int compareTo(ServerData o) {
        return  this.balance.compareTo(o.getBalance());
    }
}
