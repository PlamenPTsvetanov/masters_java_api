package me.thesis.master.models.views.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;

@Getter
@Setter
@ToString
public class UserInView extends BaseInView {
    private String firstName;
    private String lastName;
    private String email;
}
