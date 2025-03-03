package me.thesis.master.services;

import me.thesis.master.common.exceptions.UserAlreadyExistsException;
import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.models.orm.UserOrmBean;
import me.thesis.master.models.views.user.UserInView;
import me.thesis.master.models.views.user.UserOutView;
import me.thesis.master.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
