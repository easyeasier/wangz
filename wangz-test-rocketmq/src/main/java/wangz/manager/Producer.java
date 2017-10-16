package wangz.manager;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangz on 17-3-3.
 */
public class Producer {
    public static void main(String[] args) throws MQClientException,
            InterruptedException, UnsupportedEncodingException, RemotingException, MQBrokerException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("FS-BPM-WORKFLOW-TRANSFER");
//        producer.setNamesrvAddr("10.113.41.2:9876");
        producer.setNamesrvAddr("localhost:9876");

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        /**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
        String[] tags = new String[]{"TagA", "TagB"};
        String[] topics = new String[]{"WZTopicA", "TopicB"};
        for (int i = 0; i < 10; i++) {
            int orderId = i % 10;
            //Create a message instance, specifying topic, tag and message body.
//            Message msg = new Message(topics[i % topics.length], tags[i % tags.length], "KEY" + i,
//            Message msg = new Message("TopicB ", tags[i % tags.length], "KEY" + i,
//                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //单tag，多consumerGroup测试
//            Message msg = new Message("TopicA", "TagA", ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //多tag，单队列，阻塞测试
            Message msg = new Message("WZTopicA", tags[i % 2], ("Hello RocketMQ " + i).getBytes(RemotingHelper
                    .DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % 3;
//                    int index = 0;
                    System.out.println("id = " + id + " ,mqs.size = " + mqs.size() + " ,tag = " + msg.getTags() + " ," +
                            "topic=" + msg.getTopic());
                    return mqs.get(index);
                }
            }, orderId);

            System.out.printf("%s%n", sendResult);
        }

        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }
}