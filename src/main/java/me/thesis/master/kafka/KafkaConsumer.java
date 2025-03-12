package me.thesis.master.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.thesis.master.models.orm.VideoOrmBean;
import me.thesis.master.models.views.video.VideoStatusTransferView;
import me.thesis.master.models.views.video.VideoTransferView;
import me.thesis.master.repositories.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final VideoRepository repository;

    public KafkaConsumer(VideoRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "video.status", groupId = "dev")
    public void consume(String message) {
        // TODO move this to the service
        try {
            VideoStatusTransferView in = mapper.readValue(message, VideoStatusTransferView.class);
            if (in != null) {
                VideoOrmBean ref = repository.getReferenceById(in.getId());
                ref.setStatus(in.getStatus());
                ref.setStatusMessage(in.getMessage());
                repository.save(ref);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
