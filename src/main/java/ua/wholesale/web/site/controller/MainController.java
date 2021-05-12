package ua.wholesale.web.site.controller;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;

import java.util.List;

@Controller
public class MainController {


    @Autowired
    private GoodsService goodsService;

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "") String heading,
            @RequestParam(required = false, defaultValue = "0") String pricemin,
            @RequestParam(required = false, defaultValue = "1000000000") String pricemax,
            Model model) {

        List<Goods> messages;

//Тільки заголовок є
        if ((filter != null || !filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            messages = goodsService.filterTitleAndPrice1AndPrice2(filter, pricemin1, pricemax1);
        }
//Тільки рубрика все
        else if ((filter == null || filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            messages = goodsService.filterPrice1AndPrice2(pricemin1, pricemax1);
        }
//Є рубрика
        else if ((filter == null || filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            messages = goodsService.filterHeadingAndPrice1AndPrice2(heading, pricemin1, pricemax1);
        }
//Є заголовок і рубрика
        else if ((filter != null || !filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            messages = goodsService.filterTitleAndHeadingAndPrice1AndPrice2(filter, heading, pricemin1, pricemax1);
        } else {
            messages = goodsService.findAll();
        }

        model.addAttribute("messages",messages);
        model.addAttribute("filter", filter);
        model.addAttribute("heading", heading);
        model.addAttribute("pricemin", pricemin);
        model.addAttribute("pricemax", pricemax);
        model.addAttribute("user", user);

        return "main";
    }

    @GetMapping
    public String rather() {
        return "greeting";
        }

}
