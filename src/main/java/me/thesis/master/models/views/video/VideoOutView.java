package me.thesis.master.models.views.video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseOutView;

@Getter
@Setter
@ToString
public class VideoOutView extends BaseOutView {
    private String name;
    private String description;
    private Boolean isCopyrighted;
    private Boolean freeToUse;
}
