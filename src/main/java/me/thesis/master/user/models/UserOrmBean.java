package me.thesis.master.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseOrmBean;

@Entity
@Table(name = "\"USER\"")
@Getter
@Setter
@ToString
public class UserOrmBean extends BaseOrmBean {
    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;
}
