package ua.wholesale.web.site.telegram.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.wholesale.web.site.telegram.model.UserTelegram;

@Component
public interface FillingService {

    SendMessage getHandler(UserTelegram userTelegram, Message message);
}
