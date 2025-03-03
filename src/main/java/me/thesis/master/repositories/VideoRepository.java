package me.thesis.master.repositories;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.models.orm.VideoOrmBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends BaseRepository<VideoOrmBean> {

    List<VideoOrmBean> findAllByUserId(UUID userId, Pageable pageable);
}
