package me.thesis.master.models.views.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;

@Getter
@Setter
@ToString
public class UserInitialCreationOutView extends UserOutView {
    private ApiKeyOutView apiKey;
}
