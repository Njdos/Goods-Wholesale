package ua.wholesale.web.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Api("Display ad")
public class SomeMessageController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/usernotice/{id}")
    @ApiOperation(value = "Display ad by id" , response = String.class)
    public String greetidng(@AuthenticationPrincipal User user,
                            @PathVariable("id") long id,
                            Model model) {

        Iterable<Goods> messages = goodsService.findById(id);

        model.addAttribute("messages", messages);
        model.addAttribute("user", user);

        return "usernotice";
    }
}
