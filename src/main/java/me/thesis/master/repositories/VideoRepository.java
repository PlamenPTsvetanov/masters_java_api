package me.thesis.master.repositories;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.models.orm.VideoOrmBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends BaseRepository<VideoOrmBean> {

    @Query("select n from VideoOrmBean n  "
            + " WHERE n.userId = :userId"
            + " AND (:name is null or n.name LIKE :name) "
            + " AND (:status is null or n.status LIKE :status) "
            + " AND (:isCopyrighted is null or n.isCopyrighted = :isCopyrighted) "
            + " AND (:freeToUse is null or n.freeToUse = :freeToUse) "
            + " AND (:statusDescription is null or n.statusDescription LIKE :statusDescription) "
            + " AND (:deepfakeStatus is null or n.deepfakeStatus = :deepfakeStatus) "
            + " AND (:deepFakeStatusDescription is null or n.deepfakeStatusDescription LIKE :deepFakeStatusDescription)"
    )
    List<VideoOrmBean> findAllByFilter(@Param("userId") UUID userId,
                                       @Param("name") String name,
                                       @Param("status") String status,
                                       @Param("isCopyrighted") Boolean isCopyrighted,
                                       @Param("freeToUse") Boolean freeToUse,
                                       @Param("statusDescription") String statusDescription,
                                       @Param("deepfakeStatus") String deepfakeStatus,
                                       @Param("deepFakeStatusDescription") String deepFakeStatusDescription,
                                       Pageable pageable);
}
