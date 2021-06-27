package ua.wholesale.web.site.telegram.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;
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
@AllArgsConstructor
public class FillingServiceImpl implements FillingService {

    private DataCacheService dataCache;

    private GoodsService goodsService;

    private BotBuyGoods botBuyGoods;

    private ProfileUsersService profileUsersService;

    @Override
    public SendMessage getHandler(UserTelegram userTelegram, Message message) {

        String answer = message.getText();
        BotState botState = BotState.valueOf(userTelegram.getState());

        UserTelegram userTelegram1 = dataCache.getUserProfileData(userTelegram.getUserid());
        SendMessage replys = new SendMessage();

        String chatID = String.valueOf(message.getChatId());
        Long telegramID = userTelegram.getId();
        Long telegramUserID = userTelegram.getUserid();


        if (botState.equals(BotState.FAILED_FILLING)){

            if (userTelegram1.getId() != null){
                dataCache.deleteMakeNullUserid(userTelegram1);
                userTelegram1 = new UserTelegram();
                defaultValues("reply.askDesire" , replys);
                userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
                userTelegram1.setDesire(answer);
            }

            defaultValues("reply.askDesire" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_DESIRE)){
            if (userTelegram1.getId() != null){
                dataCache.deleteMakeNullUserid(userTelegram1);
                userTelegram1 = new UserTelegram();
                defaultValues("reply.askDesire" , replys);
                userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
                userTelegram1.setDesire(answer);
            }
            defaultValues("reply.askDesire" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_KOD) && answer.equals("yes")){
            defaultValues("reply.askKod" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_GOOD));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_KOD) && answer.equals("no")){
            defaultValues("reply.askDesire" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_DESIRE));
            userTelegram1.setDesire(answer);
        }
        if (botState.equals(BotState.ASK_GOOD)){
            try{

                Goods goods = goodsService.getById(Long.valueOf(answer.trim()));
                if (goods == null) new  Exception();
                defaultValues("reply.askGood" , replys);
                replys.setText(goods + "\n" + "\n" + replys.getText());
                userTelegram1.setState(String.valueOf(BotState.ASK_NUMBERS));
                userTelegram1.setKod(answer);
                botBuyGoods.sendPhoto(message,goods);

            }catch (Exception e){
                userTelegram1.setState(String.valueOf(BotState.ASK_KOD));
                replys.setText("Sorry your is not right :" + answer + "\n"  + "Write 'yes'' if you wanna to try again");
            }
        }
        if (botState.equals(BotState.ASK_NUMBERS)){
            defaultValues("reply.askNumbers" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_BIO));
            userTelegram1.setGood(answer);
        }
        if (botState.equals(BotState.ASK_BIO)){
            defaultValues("reply.askBio" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_PHONE));
            userTelegram1.setNumbers(answer);
        }
        if (botState.equals(BotState.ASK_PHONE)){
            defaultValues("reply.askPhone" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_EMAIL));
            userTelegram1.setBio(answer);
        }
        if (botState.equals(BotState.ASK_EMAIL)){
            defaultValues("reply.askEmail" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_ADDRESS));
            userTelegram1.setPhone(answer);
        }
        if (botState.equals(BotState.ASK_ADDRESS)){
            defaultValues("reply.askAddress" , replys);
            userTelegram1.setState(String.valueOf(BotState.ASK_TOTAL_SUM));
            userTelegram1.setEmail(answer);
        }
        if (botState.equals(BotState.ASK_TOTAL_SUM)){

            UserTelegram telegram = dataCache.getUserProfileData(message.getFrom().getId());
            Set<Goods> good = goodsService.findById(Long.valueOf(telegram.getKod().trim()));

            answerNumIs( telegram.getNumbers(), good.stream().findFirst().get().getCount(), good.stream().findFirst().get().getPrice(), replys , userTelegram1 , message, answer);

        }
        if (botState.equals(BotState.FINISH_PROFILE) || message.getText().equals("/finish")){

            ArrayList<ProfileUsers> arrayList = profileUsersService.getByUserid(message.getFrom().getId());



            if (arrayList!=null){
                botBuyGoods.sendProfile(arrayList,replys,userTelegram1,telegramID,telegramUserID,chatID);
                replys.setText("That All");
                replys.setChatId( chatID );
                return replys;
            }

            replys.setText("Sorry you don`t make any orders");

        }

        userTelegram1.setId( telegramID );
        userTelegram1.setUserid( telegramUserID );

        dataCache.save( userTelegram1 );

        replys.setChatId( chatID );

        return replys;
    }
    private void answerNumIs(String num, Long goodNum, Long goodPrice, SendMessage replys, UserTelegram userTelegram1, Message message, String answer){

        defaultValues("reply.askTotal_sum", replys);
        String question = replys.getText();
        Long answerNum;

        try {
            answerNum= Long.parseLong( num );
        } catch(Exception e){
            answerNum = Long.valueOf(1);
        }
        if ( answerNum > goodNum ){
            String result = String.valueOf( goodNum * goodPrice );
            replys.setText( question + "\n\n" + "Total sum = " + result + " count = " + goodNum);
            userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
            userTelegram1.setAddress(answer);
            profileUsersService.save(userTelegram1, message.getFrom().getId());
        }
        if (  answerNum < 1 ){
            String result = String.valueOf(goodPrice);
            defaultValues("reply.askTotal_sum", replys);
            replys.setText( question + "\n\n" + "Total sum = " + result + " count = 1");
            userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
            userTelegram1.setAddress(answer);
            profileUsersService.save(userTelegram1, message.getFrom().getId());
        }

        String result = String.valueOf(answerNum  * goodPrice);
        replys.setText( question + "\n\n" + "Total sum = " + result + " count = " + answerNum + "\n\n" + "Write /finish");
        userTelegram1.setState(String.valueOf(BotState.FINISH_PROFILE));
        userTelegram1.setAddress(answer);
        userTelegram1.setTotal_sum(result);

        profileUsersService.save(userTelegram1, message.getFrom().getId());
    }

    private void defaultValues(String values, SendMessage replys){
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("C:/Users/Admin/IdeaProjects/Goods-Wholesale/src/main/resources/messages_ua_UA.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        replys.setText( String.valueOf(appProps.get(values)) );
    }
}