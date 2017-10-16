package wz.test.zookeeper.subscription.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wangz on 17-4-2.
 */
@Getter
@Setter
public class ServerConfig implements Serializable{
    private String dbHost;
    private String dbPasswd;

    public ServerConfig(){}
    public ServerConfig(String dbHost, String dbPasswd) {
        this.dbHost = dbHost;
        this.dbPasswd = dbPasswd;
    }

    public String toString(){
        return "dbHost : "+dbHost + ", dbPasswd : "+ dbPasswd;
    }
}
