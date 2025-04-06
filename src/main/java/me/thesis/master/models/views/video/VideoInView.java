package me.thesis.master.models.views.video;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;

@Getter
@Setter
@ToString
public class VideoInView extends BaseInView {
    @NotNull(message = "Name cannot be null!")
    @Size(min = 3, max = 50, message = "Name cannot be less than 3 and more than 50 symbols!")
    private String name;
    @Size(min = 5, max = 1000, message = "Description cannot be less than 5 and more than 1000 symbols!")
    private String description;
    private Boolean isCopyrighted;
    private Boolean freeToUse;
}
