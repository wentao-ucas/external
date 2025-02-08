package cn.cncc.caos.common.kafka;

import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: Kafka消费者
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrapServers}")
    private String producerBootstrapServers; //生产者连接Server地址

    @Value("${kafka.consumer.group.id}")
    private String groupId; //生产者连接Server地址

    @Value("${kafka.listener.concurrency}")
    private Integer concurrency; //生产者连接Server地址

    @Value("${kafka.consumer.client.id}")
    private String clientId; //生产者连接Server地址

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // 并发创建的消费者数量
        factory.setConcurrency(concurrency);
        // 开启批处理
//        factory.setBatchListener(true);

        factory.getContainerProperties().setPollTimeout(1500);

        //配置手动提交offset
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);


        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }


    private Map<String, Object> consumerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", producerBootstrapServers);
        //properties.put("group.id", getIPAddress()); //获取服务器Ip作为groupId
        properties.put("group.id", groupId); //获取服务器Ip作为groupId
        properties.put("client.id", clientId); //获取服务器Ip作为groupId
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put("auto.commit.interval.ms", "1000");
//        properties.put("auto.offset.reset", "earliest");
        properties.put("auto.offset.reset", "latest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        properties.put("fetch.message.max.bytes")

//        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        return properties;
    }

    public String getIPAddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            if (address != null && StringUtils.isNotBlank(address.getHostAddress())) {
                return address.getHostAddress();
            }
        }catch (UnknownHostException e) {
            return UUID.randomUUID().toString().replace("-","");
        }
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 自定义监听
     */
//    @Bean
//    public DataFileKafkaListener listener() {
//        return new DataFileKafkaListener();
//    }
}