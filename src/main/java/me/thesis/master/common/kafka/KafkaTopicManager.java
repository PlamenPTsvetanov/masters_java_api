package me.thesis.master.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaTopicManager {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public void createTopicIfNotExist(final String topicName, final int partitions, final short replicationFactor) {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        try (Admin admin = Admin.create(properties)) {
            if (!admin.listTopics().names().get().contains(topicName)) {
                NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

                CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

                KafkaFuture<Void> future = result.values().get(topicName);
                future.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
