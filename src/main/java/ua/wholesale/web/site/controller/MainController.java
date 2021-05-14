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
import ua.wholesale.web.site.service.GoodsService;

import java.util.List;

@Controller
@Api(value = "All goods")
public class MainController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/main")
    @ApiOperation(value = "Search good and Display all goods", response = String.class)
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "") String heading,
            @RequestParam(required = false, defaultValue = "0") String pricemin,
            @RequestParam(required = false, defaultValue = "1000000000") String pricemax,
            Model model) {

        List<Goods> goods;

//Тільки заголовок є
        if ((filter != null || !filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            goods = goodsService.filterTitleAndPrice1AndPrice2(filter, pricemin1, pricemax1);
        }
//Тільки рубрика все
        else if ((filter == null || filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            goods = goodsService.filterPrice1AndPrice2(pricemin1, pricemax1);
        }
//Є рубрика
        else if ((filter == null || filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            goods = goodsService.filterHeadingAndPrice1AndPrice2(heading, pricemin1, pricemax1);
        }
//Є заголовок і рубрика
        else if ((filter != null || !filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            goods = goodsService.filterTitleAndHeadingAndPrice1AndPrice2(filter, heading, pricemin1, pricemax1);
        } else {
            goods = goodsService.findAll();
        }

        model.addAttribute("messages",goods);
        model.addAttribute("filter", filter);
        model.addAttribute("heading", heading);
        model.addAttribute("pricemin", pricemin);
        model.addAttribute("pricemax", pricemax);
        model.addAttribute("user", user);

        return "main";
    }

    @GetMapping
    @ApiOperation(value = "First page", response = String.class)
    public String rather() {
        return "greeting";
        }

}
