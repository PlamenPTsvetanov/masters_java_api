package me.thesis.master.common.repositories;


import me.thesis.master.common.models.BaseOrmBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BaseRepository<Orm extends BaseOrmBean>
        extends JpaRepository<Orm, UUID> {
}
