package me.thesis.master.user.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseOutView;

@Getter
@Setter
@ToString
public class UserOutView extends BaseOutView {
    private String firstName;
    private String lastName;
}
