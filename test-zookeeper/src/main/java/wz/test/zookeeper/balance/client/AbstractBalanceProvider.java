package wz.test.zookeeper.balance.client;

import java.util.List;

/**
 * Created by wangz on 17-4-3.
 */
public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {
    protected abstract  T balanceAlgorithm(List<T> items);

    protected abstract  List<T> getBalanceItems();

    public T getBlanceItem(){
        return balanceAlgorithm(getBalanceItems());
    }
}
