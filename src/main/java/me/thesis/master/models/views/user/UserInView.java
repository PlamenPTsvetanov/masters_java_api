package me.thesis.master.models.views.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseInView;

@Getter
@Setter
@ToString
public class UserInView extends BaseInView {
    @NotNull(message = "First name cannot be null!")
    @Size(min = 3, max = 50, message = "First name cannot be less than 3 and more than 50 symbols!")
    private String firstName;
    @NotNull(message = "Last name cannot be null!")
    @Size(min = 3, max = 50, message = "Last name cannot be less than 3 and more than 50 symbols!")
    private String lastName;
    @NotNull(message = "Email cannot be null!")
    @Size(min = 5, max = 100, message = "Email cannot be less than 5 and more than 100 symbols!")
    private String email;

}
