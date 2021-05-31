package ua.wholesale.web.site.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.service.EmailService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void sendSimpleMessage(String email, HttpServletResponse httpServletResponse) {

        Random passGen = new Random();
        String password = String.valueOf(passGen.nextLong());
        String password2 = passwordEncoder.encode(password);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirm Email");
        message.setText("Confirm password " + password2);

        Cookie email_confirm = new Cookie("email_confirm", password2);
        email_confirm.setMaxAge(60 * 60 * 60);
        httpServletResponse.addCookie(email_confirm);
        emailSender.send(message);
    }
}
