package me.thesis.master.models.orm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.thesis.master.common.models.BaseOrmBean;

import java.util.UUID;

@Entity
@Table(name = "\"VIDEO\"")
@Getter
@Setter
@ToString
public class VideoOrmBean extends BaseOrmBean {
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    @Column(name = "LOCATION", length = 200, nullable = false)
    private String location;
    @Column(name = "IS_COPYRIGHTED", nullable = false)
    private Boolean isCopyrighted;
    @Column(name = "FREE_TO_USE", nullable = false)
    private Boolean freeToUse;
    @Column(name = "USER_ID", nullable = false)
    private UUID userId;
    @Column(name = "STATUS", length = 20, nullable = false)
    private String status;
    @Column(name = "STATUS_DESCRIPTION", length = 100)
    private String statusDescription;
    @Column(name = "DEEPFAKE_STATUS", length = 20, nullable = false)
    private String deepfakeStatus;
    @Column(name = "DEEPFAKE_STATUS_DESCRIPTION", length = 100)
    private String deepfakeStatusDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", updatable = false, insertable = false, nullable = false)
    private UserOrmBean user;
}
