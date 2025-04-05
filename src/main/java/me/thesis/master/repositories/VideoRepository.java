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

    @Query("select n from VideoOrmBean n where " +
            "n.userId = :userId AND" +
            " (:name = NULL OR n.name LIKE CONCAT('%', :name, '%')) AND " +
            " (:isCopyrighted = NULL OR n.isCopyrighted = :isCopyrighted) AND " +
            " (:freeToUse = NULL OR n.freeToUse = :freeToUse) AND " +
            " (:status = NULL OR n.status = :status) AND " +
            " (:statusDescription = NULL OR n.statusDescription LIKE CONCAT('%', :statusDescription, '%')) AND " +
            " (:deepfakeStatus = NULL OR n.deepfakeStatus = :deepfakeStatus) AND " +
            " (:deepFakeStatusDescription = NULL OR n.deepfakeStatusDescription LIKE CONCAT('%', :deepFakeStatusDescription, '%'))")
    List<VideoOrmBean> findAllByFilter(@Param("userId") UUID userId,
                                       @Param("name") String name,
                                       @Param("isCopyrighted") Boolean isCopyrighted,
                                       @Param("freeToUse") Boolean freeToUse,
                                       @Param("status") String status,
                                       @Param("statusDescription") String statusDescription,
                                       @Param("deepfakeStatus") String deepfakeStatus,
                                       @Param("deepFakeStatusDescription") String deepFakeStatusDescription,
                                       Pageable pageable);
}
