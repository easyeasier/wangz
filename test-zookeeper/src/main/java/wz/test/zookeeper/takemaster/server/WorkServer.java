package wz.test.zookeeper.takemaster.server;

import lombok.Getter;
import lombok.Setter;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import wz.test.zookeeper.takemaster.model.RunningData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangz on 17-4-1.
 */

/**
 * WokeServer 服务
 * 1.记录serverData配置
 * 2.记录masterData配置
 * 3.记录master节点的监听方式
 */
@Getter
@Setter
public class WorkServer {
    private volatile boolean running = false;
    private ZkClient zkClient;
    private RunningData serverData;
    private RunningData masterData;
    private IZkDataListener dataListener;
    private static final String MASTER_PATH = "/master";
    private ScheduledExecutorService delayExecutor = Executors.newScheduledThreadPool(1);

    //进行优化抢占模式时,推迟抢占要 早于 释放的时间,不然前一次master释放时的抢占会在出现在后面master释放抢占时间上.达不到优化目的
    private int delayTakeMasterTime = 2;//秒
    private int releaseTime = 300;

    public WorkServer(RunningData data, ZkClient zkClient) {
        this.zkClient = zkClient;
        this.serverData = data;
        dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //1.普通抢占
//                takeMaster();
                //2.优化抢占
                //当将听到master节点删除时,抢占master
                //优化项,可能发生网络抖动,导致原master节点断开链接,再进行master选举时先判断是不是原master,如果是直接抢占,如果不是,5秒之后再抢占
                //这样做是为了避免过多的配置更改
                if(checkMaster()){
                    takeMaster();
                }else{
                    delayExecutor.schedule(new Runnable() {
                        @Override
                        public void run() {
                            takeMaster();
                        }
                    }, delayTakeMasterTime, TimeUnit.SECONDS);
                }
            }
        };

    }

    public void start() {
        if (running) {
            throw new RuntimeException("服务已经启动");
        }
        running = true;
        zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
        takeMaster();
    }

    public void stop() {
        if (!running) {
            throw new RuntimeException("服务已经终止");
        }
        running = false;
    }

    //抢占master
    public void takeMaster() {
        if (!running) {
            return;
        }
        try {
            createMaster();
            //用于抢占实验,每隔5秒钟释放一次master
            delayExecutor.schedule(new Runnable() {
                @Override
                public void run() {
                    releaseMaster();
                }
            }, releaseTime, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {   //如果已经存在master节点,说明在抢瞬间,已经被别人抢占
            //path, 如果节点不存在是否返回null
            RunningData runningData = zkClient.readData(MASTER_PATH, true);
            //读的瞬间,master节点宕机,再次进行抢占
            if (runningData == null) {
                takeMaster();
            } else {
                masterData = runningData;
            }
        }
    }

    //释放master
    public void releaseMaster() {
        if (checkMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    //检查本节点是否是master节点
    public boolean checkMaster() {
        if (masterData != null && masterData.equals(this.serverData)) {
            return true;
        }

        return false;
    }

    public void createMaster() {
        zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
        masterData = serverData;
        System.out.println("Client " + this.serverData.getName() + " is master");
    }
}
