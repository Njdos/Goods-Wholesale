package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;

@Component
public interface TwillioService {


     void sendSms(String to, String from, String body);

}
