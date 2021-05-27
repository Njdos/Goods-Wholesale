package ua.wholesale.web.site.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.javafxController.JavafxBuyController;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;

import java.util.Set;


@Controller
public class JavaBuyController {

    @Autowired
    private JavafxBuyController javafxBuyController;

    @Autowired
    private GoodsService goodsService;


    @GetMapping("/Buy/{id_user}/{id_goods}")
    @ApiOperation(value = "Display ad by id", response = String.class)
    public String greetidng(@AuthenticationPrincipal User user,
                            @PathVariable("id_goods") long id_goods,
                            @PathVariable("id_goods") long id_user)  {


        Set<Goods> goods = goodsService.findById(id_goods);

        javafxBuyController.buyWindow(goods);

        return "redirect:/usernotice/"+id_goods;
    }
}