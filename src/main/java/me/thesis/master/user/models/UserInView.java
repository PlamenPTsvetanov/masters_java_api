package me.thesis.master.user.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;
import me.thesis.master.common.models.BaseOrmBean;

@Getter
@Setter
@ToString
public class UserInView extends BaseInView {
    private String firstName;
    private String lastName;
    private String email;
}
