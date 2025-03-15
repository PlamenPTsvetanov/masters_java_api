package me.thesis.master.services;

import lombok.extern.slf4j.Slf4j;
import me.thesis.master.common.exceptions.UserAlreadyExistsException;
import me.thesis.master.common.exceptions.UserNotFoundException;
import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.models.orm.UserOrmBean;
import me.thesis.master.models.views.user.UserInView;
import me.thesis.master.models.views.user.UserOutView;
import me.thesis.master.repositories.ApiKeyRepository;
import me.thesis.master.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService extends BaseService<UserOrmBean, UserInView, UserOutView> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(UserOrmBean.class, UserInView.class, UserOutView.class);
        this.userRepository = userRepository;
    }

    @Override
    protected BaseRepository<UserOrmBean> getRepository() {
        return userRepository;
    }

    @Override
    protected void validateBeforePost(UserInView orm) {
        if (this.userRepository.findByEmail(orm.getEmail()) != null) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", orm.getEmail()));
        }
    }

    public void validateUser(UUID userId) {
        log.info("Validating user with id {}", userId);
        // Verify user
        Optional<UserOrmBean> userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            log.error("User with id {} is not present in the system.", userId);
            throw new UserNotFoundException("User not found!");
        }
    }
}
