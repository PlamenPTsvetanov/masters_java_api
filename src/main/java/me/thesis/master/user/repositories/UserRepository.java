package me.thesis.master.user.repositories;

import me.thesis.master.common.repositories.BaseRepository;
import me.thesis.master.user.models.UserOrmBean;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserOrmBean> {

    /**
     * Retrieves user database object by unique email.
     * @param email personal email of user.
     * @return user database object.
     */
    UserOrmBean findByEmail(String email);

}
