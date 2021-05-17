package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.wholesale.web.site.service.EmailService;
import ua.wholesale.web.site.service.TwillioService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GreetingController {

    @Autowired
    private TwillioService twillioService;

    @Value("${TRIAL_NUMBER}")
    private String from;

    @Autowired
    private EmailService emailService;


    @GetMapping
    public String getFirstPage() { return "greeting"; }

    @PostMapping
    public String postCookies(
            @RequestParam(value = "save_cookies", required = false) String save_cookies,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value =  "count", required = false) long count,
            @RequestParam(value =  "title", required = false) String title,
            @RequestParam(value = "email", required = false) String description,

            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value =  "bodyPhone", required = false) String bodyPhone,
            @RequestParam(value = "countPhone", required = false) long countPhone,
            HttpServletResponse httpServletResponse
    ) {

        for (int i=0; i<count; i++) emailService.sendSimpleMessage(email, title, description);

        for (int i=0; i<countPhone; i++)   twillioService.sendSms(phoneNumber, from, bodyPhone);

        if (save_cookies!=null){
            String scount = String.valueOf(count);
            Cookie cookie_title = new Cookie("email", email);
            Cookie cookie_heading = new Cookie("count", scount);
            Cookie cookie_pricemin = new Cookie("title", title);
            Cookie cookie_pricemax = new Cookie("description", description);

            cookie_title.setMaxAge(-1);
            cookie_heading.setMaxAge(-1);
            cookie_pricemin.setMaxAge(-1);
            cookie_pricemax.setMaxAge(-1);

            httpServletResponse.addCookie(cookie_title);
            httpServletResponse.addCookie(cookie_heading);
            httpServletResponse.addCookie(cookie_pricemin);
            httpServletResponse.addCookie(cookie_pricemax);
        }


        return "greeting";
    }

    @GetMapping("/cookies")
    public String getCookies(HttpServletRequest httpServletRequest, Model model) {

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie1 : cookies) {
                model.addAttribute(cookie1.getName(), cookie1.getValue());
            }
        }

        return "greeting";
    }
}