package ua.wholesale.web.site.telegram.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.UserTelegram;

@Component
public interface DataCacheService {

    void setUsersCurrentBotState(UserTelegram userTelegram);

    BotState getUsersCurrentBotState(Long userId);

    UserTelegram getUserProfileData(Long userId);

    void saveUserProfileData(UserTelegram userProfileData);

}