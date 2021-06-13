package ua.wholesale.web.site.telegram.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.repos.UserTelegramRepo;
import ua.wholesale.web.site.telegram.service.DataCacheService;


@Service
public class DataCacheServiceImpl implements DataCacheService {

    @Autowired
    private UserTelegramRepo userTelegramRepo;

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
    public void deleteMakeNullUserid(UserTelegram userTelegram) {
        userTelegram.setUserid(null);
        userTelegramRepo.save(userTelegram);
    }

    @Override
    public void save(UserTelegram userTelegram) {
        userTelegramRepo.save(userTelegram);
    }
}
