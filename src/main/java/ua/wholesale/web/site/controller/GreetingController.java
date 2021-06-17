package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.wholesale.web.site.service.EmailService;

@Controller
public class    GreetingController {

    @Autowired
    private EmailService emailService;

    @GetMapping()
    public String getFirstPage() { return "greeting"; }

}