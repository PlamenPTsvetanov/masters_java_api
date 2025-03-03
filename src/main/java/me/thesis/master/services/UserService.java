package me.thesis.master.services;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.common.service.BaseService;
import me.thesis.master.models.views.UserInView;
import me.thesis.master.models.orm.UserOrmBean;
import me.thesis.master.models.views.UserOutView;
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
}
