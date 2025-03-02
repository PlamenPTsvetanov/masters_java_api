package me.thesis.master.common.models;


import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseOutView {
    private UUID id;
}
