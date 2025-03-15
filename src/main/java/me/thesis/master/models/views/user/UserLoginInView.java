package me.thesis.master.models.views.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UserLoginInView extends BaseInView {
    private UUID userId;
    private String apiKeyValue;

}
