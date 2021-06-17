package ua.wholesale.web.site.telegram.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.telegram.model.ProfileUsers;
import ua.wholesale.web.site.telegram.model.UserTelegram;

import java.util.ArrayList;

@Component
public interface ProfileUsersService {

    ArrayList<ProfileUsers> getByUserid(Long userId);

    void save(UserTelegram userTelegram, Long userId);
}
