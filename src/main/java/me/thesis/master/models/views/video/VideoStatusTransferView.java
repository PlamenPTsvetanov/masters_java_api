package me.thesis.master.models.views.video;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class VideoStatusTransferView {
    private UUID id;
    private String status;
    private String message;
    private String deepfakeStatus;
    private String deepfakeMessage;
}
