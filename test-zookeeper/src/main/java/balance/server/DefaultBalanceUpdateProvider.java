package balance.server;


import balance.model.ServerData;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * Created by wangz on 17-4-3.
 */
public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {
    private String serverPath;
    private ZkClient zkClient;

    public DefaultBalanceUpdateProvider(String serverPath, ZkClient zkClient) {
        this.serverPath = serverPath;
        this.zkClient = zkClient;
    }

    @Override
    public boolean addBalance(Integer step) {

        Stat stat = new Stat();
        ServerData data;
        while(true){
            data = zkClient.readData(serverPath, stat);

            data.setBalance(data.getBalance() + step);
            try {
                zkClient.writeData(serverPath, data, stat.getAversion());
                return true;
            } catch (ZkBadVersionException e) {
                //当更新时,有其他客户端也做了更改,
                //ignore
            }
            catch (Exception e){
                System.out.println(e);
                return false;
            }
        }
    }

    @Override
    public boolean reduceBalance(Integer step) {
        Stat stat = new Stat();
        ServerData data;
        while(true){
            data = zkClient.readData(serverPath, stat);

            data.setBalance((data.getBalance() - step) < 0 ? 0 : data.getBalance() - step);
            try {
                zkClient.writeData(serverPath, data, stat.getAversion());
                return true;
            } catch (ZkBadVersionException e) {
                //ignore
            }catch (Exception e){
                System.out.println(e);
                return false;
            }
        }
    }
}
