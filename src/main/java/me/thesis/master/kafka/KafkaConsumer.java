package me.thesis.master.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "video.status", groupId = "dev")
    public void consume(String message) {
        System.out.println("message = " + message);
    }
}
