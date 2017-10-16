package balance.server;

/**
 * Created by wangz on 17-4-3.
 */
public interface RegisterProvider {
    void register(Object context);
    void unRegister(Object context);
}
