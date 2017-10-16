package balance.client;

/**
 * Created by wangz on 17-4-3.
 */
public interface BalanceProvider<T> {
    T getBalanceItem();
}
