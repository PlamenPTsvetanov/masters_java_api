package me.thesis.master.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.thesis.master.services.VideoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final VideoService videoService;

    public KafkaConsumer(VideoService videoService) {
        this.videoService = videoService;
    }

    @KafkaListener(topics = "video.status", groupId = "dev")
    public void consume(String message) {
        videoService.updateStatus(message);
    }
}
