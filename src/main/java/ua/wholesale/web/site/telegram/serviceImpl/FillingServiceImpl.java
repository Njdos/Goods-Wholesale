package ua.wholesale.web.site.telegram.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.telegram.controller.BotBuyGoods;
import ua.wholesale.web.site.telegram.model.BotState;
import ua.wholesale.web.site.telegram.model.ProfileUsers;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.service.DataCacheService;
import ua.wholesale.web.site.telegram.service.FillingService;
import ua.wholesale.web.site.telegram.service.ProfileUsersService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

@Service
public class FillingServiceImpl implements FillingService {

    @Autowired
    private DataCacheService dataCache;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private BotBuyGoods botBuyGoods;

    @Autowired
    private ProfileUsersService profileUsersService;

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
            if (userTelegram1 != null){
                dataCache.delete(userTelegram1);
                userTelegram1 = new UserTelegram();
                replys.setText((String) appProps.get("reply.askDesire"));
                userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
                userTelegram1.setDesire(answer);
            }
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
            userTelegram1.setKod(answer);
            try{
                Goods goods = goodsService.getById(Long.valueOf(answer));
                if (goods == null) new  Exception();
                replys.setText(goods + "\n" + appProps.get("reply.askGood"));
                userTelegram1.setState(String.valueOf(BotState.ASK_NUMBERS));
                userTelegram1.setKod(answer);
                botBuyGoods.sendPhoto(message,goods);
            }catch (Exception e){
                userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
                replys.setText("Sorry your is not right :" + answer + "\n"  + "Write 'yes'' if you wanna to continue");
            }
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
            UserTelegram telegram = dataCache.getUserProfileData(message.getFrom().getId());
            Set<Goods> good = goodsService.findById(Long.valueOf(telegram.getKod()));
            if ( (Long.valueOf(telegram.getNumbers() ) > good.stream().findFirst().get().getPrice() )){
                String result = String.valueOf( good.stream().findFirst().get().getCount() * good.stream().findFirst().get().getPrice() );
                replys.setText( appProps.get("reply.askTotal_sum") + "=  " + result + "count = " + good.stream().findFirst().get().getCount());
                userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
                userTelegram1.setAddress(answer);
            }
            if (  ( Long.valueOf(telegram.getNumbers() ) == 0 )){
                String result = String.valueOf( 1 * good.stream().findFirst().get().getPrice() );
                replys.setText( appProps.get("reply.askTotal_sum") + "=  " + result + "count = 1");
                userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
                userTelegram1.setAddress(answer);
            }
            String result = String.valueOf( Long.valueOf(telegram.getNumbers())  * good.stream().findFirst().get().getPrice() );
            replys.setText( appProps.get("reply.askTotal_sum") + " Total sum =  " + result + " count = " + telegram.getNumbers());
            userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
            userTelegram1.setAddress(answer);
        }
        if (botState.equals(BotState.FINISH_PROFILE) && !message.getText().equals("/finish")) {

            profileUsersService.save(userTelegram1, message.getFrom().getId());
            dataCache.delete(userTelegram1);

            replys.setText("Success");
            if (userTelegram1.getTotal_sum() == null) {
                userTelegram1.setTotal_sum(answer);
                replys.setText("Success");
            }
            replys.setText("?");
        }
        if (botState.equals(BotState.FINISH_PROFILE) && message.getText().equals("/finish")){

            ArrayList<ProfileUsers> hashSet = profileUsersService.getByUserid(message.getFrom().getId());
            if (hashSet!=null){
                for (ProfileUsers profileUsers : hashSet)
                    replys.setText(profileUsers.getUserProfile().toString());
            }

            replys.setText("Sorry you don`t make any orders");

        }

        userTelegram1.setId(userTelegram.getId());
        userTelegram1.setUserid(userTelegram.getUserid());

        dataCache.save(userTelegram1);

        replys.setChatId(String.valueOf(message.getChatId()));

        return replys;
    }
}