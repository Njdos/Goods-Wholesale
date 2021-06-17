package ua.wholesale.web.site.telegram.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.UserTelegram;

@Component
public interface DataCacheService {

    UserTelegram getUserProfileData(Long userId);

    void deleteMakeNullUserid(UserTelegram userTelegram);

    void save(UserTelegram userTelegram);

}