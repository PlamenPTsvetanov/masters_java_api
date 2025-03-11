package me.thesis.master.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VideoConsumer {
    @KafkaListener(topics = "video.status", groupId = "dev")
    public void listenVideoStatus(String message) {
        System.out.println("Received Message: " + message);
    }
}
