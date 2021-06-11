package ua.wholesale.web.site.telegram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.service.DataCacheService;
import ua.wholesale.web.site.telegram.service.FillingService;

import javax.annotation.PostConstruct;
import java.io.File;

@Controller
public class BotBuyGoods extends TelegramLongPollingBot {

    @Autowired
    private DataCacheService dataCacheService;

    @Autowired
    private FillingService fillingProfileHandler;


    @PostConstruct
    public void registerBot() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "flibusta_for_search_bot";
    }

    @Override
    public String getBotToken() {
        return "1799548863:AAHcVuEPjpFMzMXBWhxjy0imkfk6HQmWsOE";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage() != null && update.getMessage().hasText()){

            Message message = update.getMessage();
            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getFrom().getId();

            BotState botState;
            UserTelegram userTelegram = new UserTelegram();

            switch (messageText){
                case "/start":
                    botState = BotState.ASK_DESIRE;
                    break;
                case "/fail":
                    botState = BotState.FAILED_FILLING;
                    break;
                default:
                    UserTelegram userTelegram1 = dataCacheService.getUserProfileData(userId);
                    if (userTelegram1.getState() != null && userTelegram1.getId() != null){
                        botState = BotState.valueOf(userTelegram1.getState());
                        userTelegram.setId(userTelegram1.getId());
                        break;
                    }
                    botState = BotState.ASK_DESIRE;
                    break;
            }
            userTelegram.setState(String.valueOf(botState));
            userTelegram.setUserid(userId);

             SendMessage sendMessage = fillingProfileHandler.getHandler(userTelegram, message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendPhoto(Message message, Goods goods){
        SendPhoto msg = new SendPhoto();
        msg.setChatId(String.valueOf(message.getChatId()));
        msg.setPhoto(new InputFile(new File("C:/Users/Admin/IdeaProjects/Goods-Wholesale/uploads/" + goods.getFilename())));
        msg.setCaption("Good photos");
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
