package me.thesis.master.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.thesis.master.common.exceptions.VideoNotFoundException;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.kafka.KafkaProducer;
import me.thesis.master.models.orm.VideoOrmBean;
import me.thesis.master.models.views.video.VideoInView;
import me.thesis.master.models.views.video.VideoOutView;
import me.thesis.master.models.views.video.VideoStatusTransferView;
import me.thesis.master.models.views.video.VideoTransferView;
import me.thesis.master.repositories.UserRepository;
import me.thesis.master.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class VideoService extends BaseService<VideoOrmBean, VideoInView, VideoOutView> {
    @Value("${SAVE_LOCATION:/files}")
    private String saveLocationDir;

    private final ObjectMapper mapper;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    public VideoService(ObjectMapper mapper, VideoRepository videoRepository, UserRepository userRepository, KafkaProducer kafkaProducer) {
        super(VideoOrmBean.class, VideoInView.class, VideoOutView.class);
        this.mapper = mapper;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    protected VideoRepository getRepository() {
        return videoRepository;
    }

    public List<VideoOutView> getAllVideosFor(UUID userId,
                                              int size,
                                              int offset,
                                              String name,
                                              Boolean isCopyrighted,
                                              Boolean freeToUse,
                                              String status,
                                              String statusDescription,
                                              String deepfakeStatus,
                                              String deepFakeStatusDescription) {
        if (size > 100) {
            size = 100;
        }
        if (size < 0) {
            size = 10;
        }
        if (offset < 0) {
            offset = 0;
        }

        Pageable page = PageRequest.of(offset, size);
        if (name != null) name = "%" + name + "%";
        if (status != null) status = "%" + status + "%";
        if (statusDescription != null) statusDescription = "%" + statusDescription + "%";
        if (deepfakeStatus != null) deepfakeStatus = "%" + deepfakeStatus + "%";
        if (deepFakeStatusDescription != null) deepFakeStatusDescription = "%" + deepFakeStatusDescription + "%";
        List<VideoOrmBean> allByUserId = this.videoRepository.findAllByFilter(
                userId,
                name,
                status,
                freeToUse,
                isCopyrighted,
                statusDescription,
                deepfakeStatus,
                deepFakeStatusDescription,
                page);

        return mapToOutList(allByUserId);
    }

    @Transactional
    public VideoOutView saveVideo(UUID userId, VideoInView videoIn, MultipartFile multipartFile) {
        try {
            String filePath = saveVideoToStorage(multipartFile);

            VideoOrmBean ormBean = new VideoOrmBean();
            ormBean.setId(UUID.randomUUID());
            ormBean.setName(videoIn.getName());
            ormBean.setDescription(videoIn.getDescription());
            ormBean.setLocation(filePath);
            ormBean.setFreeToUse(videoIn.getFreeToUse());
            ormBean.setIsCopyrighted(videoIn.getIsCopyrighted());
            ormBean.setUserId(userId);
            ormBean.setUser(userRepository.findById(userId).get());
            ormBean.setStatus("PROCESSING");
            ormBean.setDeepfakeStatus("NO_DEEPFAKE");
            VideoOrmBean save = videoRepository.save(ormBean);

            String json = buildTransferView(ormBean);

            kafkaProducer.send("video.created", json);
            return mapToOutView(save);
        } catch (IOException e) {
            throw new RuntimeException("Exception occurred while saving video", e);
        }
    }

    private String buildTransferView(VideoOrmBean ormBean) throws JsonProcessingException {
        VideoTransferView transfer = new VideoTransferView();
        transfer.setDatabaseId(ormBean.getId());
        transfer.setLocation(ormBean.getLocation());
        transfer.setFreeToUse(ormBean.getFreeToUse());
        transfer.setIsCopyrighted(ormBean.getIsCopyrighted());
        transfer.setDescription(ormBean.getDescription());
        transfer.setUserEmail(ormBean.getUser().getEmail());
        String json = mapper.writeValueAsString(transfer);
        return json;
    }

    @Override
    @Transactional
    public VideoOutView deleteOne(UUID id) {
        try {
            Optional<VideoOrmBean> byId = this.videoRepository.findById(id);
            if (byId.isEmpty()) {
                throw new VideoNotFoundException("Video not found!");
            }

            String location = byId.get().getLocation();
            File file = new File(location);
            file.delete();

            kafkaProducer.send("video.deleted", id.toString());

            videoRepository.deleteById(id);
            return mapToOutView(byId.get());
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while deleting video", e);
        }
    }

    @Transactional
    public void updateStatus(String message) {
        try {
            log.info("Updating video status: {}", message);
            VideoStatusTransferView in = mapper.readValue(message, VideoStatusTransferView.class);
            if (in != null) {
                VideoOrmBean ref = videoRepository.getReferenceById(in.getId());
                if (ref.getStatus().equals("EXCEPTION")) {
                    return;
                }
                ref.setStatus(in.getStatus());
                ref.setStatusDescription(in.getMessage());
                ref.setDeepfakeStatus(in.getDeepfakeStatus());
                ref.setDeepfakeStatusDescription(in.getDeepfakeMessage());
                videoRepository.save(ref);
            }
        } catch (JsonProcessingException e) {
            log.error("Exception occurred while updating video status", e);
        }
    }

    private String saveVideoToStorage(MultipartFile multipartFile) throws IOException {
        File directory = new File(saveLocationDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //Securing file
        String filePath = saveLocationDir + File.separator + UUID.randomUUID() + ".mp4";
        File destinationFile = new File(filePath);

        multipartFile.transferTo(destinationFile);

        return filePath;
    }
}
