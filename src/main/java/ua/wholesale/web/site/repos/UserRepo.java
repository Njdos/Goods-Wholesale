package ua.wholesale.web.site.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.User;

import java.util.Set;

@Component
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndIdNot(String username, Long id);

    User findByPhoneAndIdNot(String phone, Long id);

    User findByEmailAndIdNot(String email, Long id);

    User findByPhone(String photo);

    User findByEmail(String email);

    Set<User> findById(long id);

}
