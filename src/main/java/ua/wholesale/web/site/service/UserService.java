package ua.wholesale.web.site.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.User;

import java.util.List;
import java.util.Set;

@Component
public interface UserService{

    UserDetails loadUserByUsername(String username);

    boolean searchUserName(User user);

    boolean searchUserNameWithoutUser(String username, Long id);

    boolean searchPhoneWithoutUser(String phone, Long id);

    boolean searchEmailWithoutUser(String email, Long id);

    boolean searchEmail(User user);

    boolean searchPhone(User user);

    void save(User user);

    List<User> findAll();

    Set<User> findById(long id);

    void update(User user);
}
