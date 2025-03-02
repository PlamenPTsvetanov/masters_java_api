package me.thesis.master.user.services;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.user.models.UserInView;
import me.thesis.master.user.models.UserOrmBean;
import me.thesis.master.user.models.UserOutView;
import me.thesis.master.user.repositories.UserRepository;
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
}
