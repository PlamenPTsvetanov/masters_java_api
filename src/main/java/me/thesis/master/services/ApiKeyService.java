package me.thesis.master.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import me.thesis.master.common.exceptions.KeyNotFoundException;
import me.thesis.master.common.exceptions.TooManyKeysException;
import me.thesis.master.common.exceptions.UserNotFoundException;
import me.thesis.master.common.models.BaseInView;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.models.orm.ApiKeyOrmBean;
import me.thesis.master.models.orm.UserOrmBean;
import me.thesis.master.models.views.apiKey.ApiKeyOutView;
import me.thesis.master.repositories.ApiKeyRepository;
import me.thesis.master.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
public class ApiKeyService extends BaseService<ApiKeyOrmBean, BaseInView, ApiKeyOutView> {
    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository, UserRepository userRepository) {
        super(ApiKeyOrmBean.class, BaseInView.class, ApiKeyOutView.class);
        this.apiKeyRepository = apiKeyRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected ApiKeyRepository getRepository() {
        return apiKeyRepository;
    }

    /**
     * Retrieving all keys (either active or inactive) for user.
     *
     * @param userId user identifier
     * @return List of api keys.
     */
    @Transactional
    public List<ApiKeyOutView> getAllForUser(UUID userId, Boolean filter) {
        // Validating user
        validateUser(userId);

        // Retrieving all desired keys
        List<ApiKeyOrmBean> byUserId = new ArrayList<>();
        if (filter) {
            byUserId = this.apiKeyRepository.getByUserIdAndIsActive(userId, true);
        } else {
            byUserId = this.apiKeyRepository.getByUserId(userId);
        }
        // Mapping to output
        return mapToOutList(byUserId);
    }

    /**
     * Generating api key for provided user.
     *
     * @param userId identifier of user.
     */
    @Transactional
    public ApiKeyOutView generateApiKey(UUID userId) {
        validateUser(userId);

        BigInteger keysForUser = this.apiKeyRepository.countByUserIdAndIsActive(userId, true);
        if (keysForUser.compareTo(BigInteger.valueOf(5)) >= 0) {
            throw new TooManyKeysException("Too many keys for user! Disable old keys first!");
        }

        // Create api key
        ApiKeyOrmBean apiKeyOrmBean = createApiKeyForUser(userId);

        ApiKeyOrmBean save = apiKeyRepository.save(apiKeyOrmBean);

        return mapToOutView(save);
    }

    @Transactional
    public ApiKeyOutView disableApiKey(UUID userId, UUID keyId) {
        validateUser(userId);

        Optional<ApiKeyOrmBean> keyOpt = this.apiKeyRepository.findById(keyId);
        if (keyOpt.isEmpty()) {
            throw new KeyNotFoundException("Key not found! Could not disable api key!");
        }

        ApiKeyOrmBean key = keyOpt.get();
        key.setIsActive(false);
        key.setValidUntil(Instant.now());
        ApiKeyOrmBean save = apiKeyRepository.save(key);
        return mapToOutView(save);
    }

    private void validateUser(UUID userId) {
        // Verify user
        Optional<UserOrmBean> userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
    }


    /**
     * Creating api key orm for user.
     *
     * @param userId user identifier.
     * @return api key database object.
     */
    private ApiKeyOrmBean createApiKeyForUser(UUID userId) {
        ApiKeyOrmBean apiKeyOrmBean = new ApiKeyOrmBean();
        apiKeyOrmBean.setId(UUID.randomUUID());
        String apiKey = generateApiKey();
        apiKeyOrmBean.setValue(apiKey);
        apiKeyOrmBean.setIsActive(true);
        apiKeyOrmBean.setValidFrom(Instant.now());
        apiKeyOrmBean.setValidUntil(Instant.now().plus(30, ChronoUnit.DAYS));
        apiKeyOrmBean.setUserId(userId);
        return apiKeyOrmBean;
    }

    /**
     * Using secure way to generate an api key.
     *
     * @return api key as a string.
     */
    private String generateApiKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
