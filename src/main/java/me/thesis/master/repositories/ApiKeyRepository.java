package me.thesis.master.repositories;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.models.orm.ApiKeyOrmBean;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface ApiKeyRepository extends BaseRepository<ApiKeyOrmBean> {
    BigInteger countByUserIdAndIsActive(UUID userId, Boolean isActive);

    List<ApiKeyOrmBean> getByUserIdAndIsActive(UUID userId, Boolean isActive);
    List<ApiKeyOrmBean> getByUserId(UUID userId);

    ApiKeyOrmBean findByUserIdAndId(UUID userId, UUID id);

    ApiKeyOrmBean findByUserIdAndValue(UUID userId, String value);
}
