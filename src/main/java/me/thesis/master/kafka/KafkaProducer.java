package me.thesis.master.kafka;

import lombok.extern.slf4j.Slf4j;
import me.thesis.master.common.kafka.KafkaTopicManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicManager kafkaTopicManager;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicManager kafkaTopicManager) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicManager = kafkaTopicManager;
    }

    public void send(String topic, String message) {
        kafkaTopicManager.createTopicIfNotExist(topic, 1, (short) 1);

        log.info("\t\t\t\tSending message: {} to topic: {}", message, topic);
        kafkaTemplate.send(topic, message);
    }
}
