package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;

@Component
public interface EmailService {

    void sendSimpleMessage(String email, String activeCode);

}
