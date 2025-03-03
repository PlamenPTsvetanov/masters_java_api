package me.thesis.master.models.views.video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;

@Getter
@Setter
@ToString
public class VideoInView extends BaseInView {
    private String name;
    private String description;
    private Boolean isCopyrighted;
    private Boolean freeToUse;
}
