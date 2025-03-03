package me.thesis.master.models.orm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.thesis.master.common.models.BaseOrmBean;

import java.time.Instant;
import java.util.UUID;

@Table(name = "\"API_KEY\"")
@Entity
@Getter
@Setter
public class ApiKeyOrmBean extends BaseOrmBean {
    @Column(name = "VALUE", length = 100, nullable = false)
    private String value;
    @Column(name = "VALID_FROM", nullable = false)
    private Instant validFrom;
    @Column(name = "VALID_UNTIL", nullable = false)
    private Instant validUntil;
    @Column(name = "IS_VALID", nullable = false)
    private Boolean isActive;
    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false, insertable = false)
    private UserOrmBean user;
}
