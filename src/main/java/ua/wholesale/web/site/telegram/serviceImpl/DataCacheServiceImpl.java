package ua.wholesale.web.site.telegram.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.repos.UserTelegramRepo;
import ua.wholesale.web.site.telegram.service.DataCacheService;


@Service
public class DataCacheServiceImpl implements DataCacheService {

    @Autowired
    private UserTelegramRepo userTelegramRepo;

    @Override
    public void setUsersCurrentBotState( UserTelegram userTelegram) {
        UserTelegram telegram = userTelegramRepo.findByUserid(userTelegram.getUserid());
        if (telegram == null) {
            userTelegram.setState(String.valueOf(userTelegram.getState()));
            userTelegram.setUserid(userTelegram.getUserid());
            userTelegramRepo.save(userTelegram);
        }
        telegram.setState(String.valueOf(userTelegram.getState()));
        telegram.setId(userTelegram.getId());
        userTelegramRepo.save(telegram);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        UserTelegram telegram = userTelegramRepo.findByUserid(userId);
        if (telegram == null) {
            return BotState.ASK_DESIRE;
        }
        return BotState.valueOf(telegram.getState());
    }

    @Override
    public UserTelegram getUserProfileData(Long userId) {
        UserTelegram telegram = userTelegramRepo.findByUserid(userId);
        if (telegram == null) {
            telegram = new UserTelegram();
            return telegram;
        }
        return telegram;
    }

    @Override
    public void saveUserProfileData(UserTelegram userProfileData) {
        userTelegramRepo.save(userProfileData);
    }

    @Override
    public void delete(UserTelegram userTelegram) {
        userTelegramRepo.delete(userTelegram);
    }

    @Override
    public void save(UserTelegram userTelegram) {
        userTelegramRepo.save(userTelegram);
    }
}
