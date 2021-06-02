package ua.wholesale.web.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.LikeMeService;
import ua.wholesale.web.site.service.MainService;

import java.util.List;

@Controller
@Api(value = "All goods")
public class MainController {

    @Autowired
    private MainService mainControllerService;

    @Autowired
    private LikeMeService likeMeService;

    @GetMapping("/main")
    @ApiOperation(value = "Search good and Display all goods", response = String.class)
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "") String heading,
            @RequestParam(required = false, defaultValue = "0") String pricemin,
            @RequestParam(required = false, defaultValue = "1000000000") String pricemax,
            Model model) {

        List<Goods> goods = mainControllerService.searchByFilter(filter,heading,pricemin,pricemax);
        List<Long> likeMeList = likeMeService.getListLikeMeIdByUserId(user.getId());

        model.addAttribute("messages",goods);
        model.addAttribute("filter", filter);
        model.addAttribute("heading", heading);
        model.addAttribute("pricemin", pricemin);
        model.addAttribute("pricemax", pricemax);
        model.addAttribute("likeMeList", likeMeList);
        model.addAttribute("user", user);

        return "main";
    }
}