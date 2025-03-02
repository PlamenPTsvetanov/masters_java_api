package me.thesis.master.common.models;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseOrmBean implements Serializable {
    @Id
    private UUID id;

    @Version
    private Long version;
}
