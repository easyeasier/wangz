package wangz.manager;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by wangz on 17-3-3.
 */
public class ConsumerTagA {

    /**
     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
     */
    public static void main(String[] args) throws InterruptedException,
            MQClientException {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
                "FS-BPM-DATA-TAILER-CONSUMER");
//                "ConsumerGroupName");
        consumer.setNamesrvAddr("localhost:9876");
//        consumer.setNamesrvAddr("10.113.41.2:9876");
        //默认16次消费失败就会放弃这条消息，继续消费下面的
//        consumer.setMaxReconsumeTimes();
        /**
         * 订阅指定topic下tags分别等于TagA或TagC或TagD
         */
//        consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
//        consumer.subscribe("TopicA", "TagA||TagB");
        //消费B
//        consumer.subscribe("WZTopicA", "TagA");
        consumer.subscribe("TRANSFER-TEST", "bpm");
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         */
//        consumer.subscribe("TRANSFER-TEST", "TagN");
//        consumer.subscribe("TopicTest1", "*");
//        consumer.subscribe("TopicTest3", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            /**
             * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
             */
            @Override
            public ConsumeOrderlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeOrderlyContext context) {
                boolean ret = true;

                System.out.println("=====");
                System.out.println(Thread.currentThread().getName()
                        + " Receive New Messages: " + msgs.size());
                for (MessageExt msg : msgs) {
                    System.out.println("topic = " + msg.getTopic() + ", tag = " +
                            msg.getTags() + ", content = " + new String(msg.getBody()));
//                    if(msg.getTags().equals("TagB")){
//                        ret = false;
//                        throw new RuntimeException("2 should be block");
//                    }
                }


                System.out.println("======");


                return ConsumeOrderlyStatus.SUCCESS;

            }
        });

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();

        System.out.println("Consumer A Started...");
    }
}
