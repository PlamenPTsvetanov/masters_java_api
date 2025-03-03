package me.thesis.master.models.views.apiKey;

import lombok.Getter;
import lombok.Setter;
import me.thesis.master.common.models.BaseOutView;

import java.time.Instant;

@Getter
@Setter
public class ApiKeyOutView extends BaseOutView {
    private String value;
    private Instant validUntil;
    private Boolean isActive;
}
