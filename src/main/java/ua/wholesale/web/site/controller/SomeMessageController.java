package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;

@Controller
public class SomeMessageController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/usernotice/{id}")
    public String greetidng(@AuthenticationPrincipal User user,
                            @PathVariable("id") long id,
                            Model model) {

        Iterable<Goods> messages = goodsService.findById(id);
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);

        return "usernotice";
    }
}
