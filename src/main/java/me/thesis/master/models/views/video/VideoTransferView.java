package me.thesis.master.models.views.video;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class VideoTransferView {
    private String location;
    private UUID databaseId;
    private Boolean freeToUse;
    private Boolean isCopyrighted;
    private String description;
    private String userEmail;
}
