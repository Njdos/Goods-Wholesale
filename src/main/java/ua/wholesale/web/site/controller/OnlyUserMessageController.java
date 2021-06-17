package ua.wholesale.web.site.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.service.OnlyUserMessageService;
import ua.wholesale.web.site.utils.validator.GoodsValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

@Controller
@ApiOperation(value = "Display all good by user")
public class OnlyUserMessageController {

    @Autowired
    private OnlyUserMessageService onlyUserMessageControllerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsValidator goodsValidator;


    @PostMapping("/user-messages")
    @ApiOperation(value = "Display all by user goods ")
    @Transactional
    public String updateMessage(
            @AuthenticationPrincipal User user,
            @Valid
            @ModelAttribute("messageForm") Goods good,
            BindingResult bindingResult,
            Model model,
            @RequestParam("messAutId") Long messAutId,
            @RequestParam("id") Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("files") MultipartFile files,
            @RequestParam("filesq") MultipartFile filesq
    ) throws IOException {

        good.setAuthor(user);
        goodsValidator.validate(good, bindingResult);
        if (bindingResult.hasErrors()) {
            goodsValidator.bindingResultErrors(bindingResult, model);
            model.addAttribute("user", user);
            model.addAttribute("messages", good);
            return "redirect:/user-messages/" + messAutId + "?message=" + id;
        }

        onlyUserMessageControllerService.saveFile1(good, file);
        onlyUserMessageControllerService.saveFile2(good, files);
        onlyUserMessageControllerService.saveFile3(good, filesq);
        goodsService.update(good);
        Iterable<Goods> goods = goodsService.findAll();
        model.addAttribute("message", null);
        model.addAttribute("messages", goods);
        return "redirect:/user-messages/" + user.getId();

    }

    @GetMapping("/user-messages/{user}")
    @ApiOperation(value = "If this good is him. Operation Edit" , response = String.class)
    public String userMessges(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Goods good
    ) {
        Set<Goods> messages = user.getGoods();
        model.addAttribute("messages", messages);
        model.addAttribute("message", good);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("user", currentUser);

        return "userMessages";
    }

    @GetMapping("/message-delete/{messageId}")
    @ApiOperation(value = "If this good is him. Operation Delete" , response = String.class)
    public String deleteMessage(@PathVariable Long messageId) throws IOException {
        goodsService.deleteById(messageId);
        return "redirect:/main";
    }
}