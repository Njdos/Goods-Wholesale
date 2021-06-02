package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.LikeMe;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.service.LikeMeService;

import java.util.List;
import java.util.Set;

@Controller
public class LikeGoodsController {

    @Autowired
    private LikeMeService likeMeService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/lovedMess")
    public String main(
            @AuthenticationPrincipal User user,
            Model model) {


        List<Goods> messages  = goodsService.findAll();

        Set<LikeMe> likeMe = likeMeService.findByUserId(user.getId());

        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        model.addAttribute("loveMess", likeMe);

        return "lovedMess";
    }

    @GetMapping("/likedmess/add/{id}")
    public String addLIke(@AuthenticationPrincipal User user, @PathVariable long id) {
        if(likeMeService.findByMessIdAndUserId(id,user.getId())) {
            likeMeService.save(id, user.getId());
            return "redirect:/main";
        }
        return "redirect:/main";
    }

    @GetMapping("/likedmess/delete/{id}")
    public String deleteLIke(@AuthenticationPrincipal User user, @PathVariable long id) {
        likeMeService.delete(id , user.getId());
        return "redirect:/main"; }
}