package wz.test.zookeeper.balance.server;

/**
 * Created by wangz on 17-4-3.
 */
public interface BalanceUpdateProvider {
    boolean addBalance(Integer step);
    boolean reduceBalance(Integer step);
}
