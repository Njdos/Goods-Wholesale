package ua.wholesale.web.site.telegram.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.service.DataCacheService;
import ua.wholesale.web.site.telegram.service.FillingService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class FillingServiceImpl implements FillingService {

    @Autowired
    public DataCacheService dataCache;


    @Override
    public SendMessage getHandler(UserTelegram userTelegram, Message message) {

        String answer = message.getText();
        BotState botState = BotState.valueOf(userTelegram.getState());

        UserTelegram userTelegram1 = dataCache.getUserProfileData(userTelegram.getUserid());
        SendMessage replys = new SendMessage();
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("C:/Users/Admin/IdeaProjects/Goods-Wholesale/src/main/resources/messages_ua_UA.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (botState.equals(BotState.ASK_DESIRE)){
            replys.setText((String) appProps.get("reply.askDesire"));
            userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_KOD) && answer.equals("yes")){
            replys.setText((String) appProps.get("reply.askKod"));
            userTelegram1.setState(String.valueOf(BotState.ASK_GOOD));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_KOD) && answer.equals("no")){
            replys.setText((String) appProps.get("reply.askDesire"));
            userTelegram1.setState(String.valueOf(BotState.ASK_DESIRE));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_GOOD)){
            replys.setText((String) appProps.get("reply.askGood"));
            userTelegram1.setState(String.valueOf(BotState.ASK_NUMBERS));
            userTelegram1.setKod(answer);
        }
        if (botState.equals(BotState.ASK_NUMBERS)){
            replys.setText((String) appProps.get("reply.askNumbers"));
            userTelegram1.setState(String.valueOf(BotState.ASK_BIO));
            userTelegram1.setGood(answer);
        }
        if (botState.equals(BotState.ASK_BIO)){
            replys.setText((String) appProps.get("reply.askBio"));
            userTelegram1.setState(String.valueOf(BotState.ASK_PHONE));
            userTelegram1.setNumbers(answer);
        }
        if (botState.equals(BotState.ASK_PHONE)){
            replys.setText((String) appProps.get("reply.askPhone"));
            userTelegram1.setState(String.valueOf(BotState.ASK_EMAIL));
            userTelegram1.setBio(answer);
        }
        if (botState.equals(BotState.ASK_EMAIL)){
            replys.setText((String) appProps.get("reply.askEmail"));
            userTelegram1.setState(String.valueOf(BotState.ASK_ADDRESS));
            userTelegram1.setPhone(answer);
        }
        if (botState.equals(BotState.ASK_ADDRESS)){
            replys.setText((String) appProps.get("reply.askAddress"));
            userTelegram1.setState(String.valueOf(BotState.ASK_TOTAL_SUM));
            userTelegram1.setEmail(answer);
        }
        if (botState.equals(BotState.ASK_TOTAL_SUM)){
            replys.setText((String) appProps.get("reply.askTotal_sum"));
            userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
            userTelegram1.setAddress(answer);
        }
        if (botState.equals(BotState.FINISH_PROFILE) && !message.getText().equals("/finish")) {
            replys.setText("Success");
            if (userTelegram1.getTotal_sum() == null) {
                userTelegram1.setTotal_sum(answer);
                replys.setText("Success");
            }
            replys.setText("?");
        }
        if (botState.equals(BotState.FINISH_PROFILE) && message.getText().equals("/finish")){
            replys.setText(userTelegram1.toString());
        }

        userTelegram1.setId(userTelegram.getId());
        userTelegram1.setUserid(userTelegram.getUserid());
        dataCache.saveUserProfileData(userTelegram1);


        replys.setChatId(String.valueOf(message.getChatId()));

        return replys;
    }
}
