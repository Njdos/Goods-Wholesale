package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.repos.UserRepo;
import ua.wholesale.web.site.service.UserService;

@Controller
public class ActiveCodeController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("active/{activeCode}")
    public String activeCode(@PathVariable String activeCode, Model model){
        User user = userRepo.findByActiveCode(activeCode);

        if (user==null){
            model.addAttribute("messageActive","Active code was not found. Sorry ");
        }
        user.setActiveCode(null);
        user.setStatus(true);
        userService.save(user);

        model.addAttribute("messageActive","Hello your account are activity! ");
        return "login";
    }
}
