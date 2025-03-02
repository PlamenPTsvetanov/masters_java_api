package me.thesis.master.common.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseInView {
    private UUID id;
}
