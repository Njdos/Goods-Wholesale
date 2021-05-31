package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public interface EmailService {

    void sendSimpleMessage(String email, HttpServletResponse httpServletResponse);

}
