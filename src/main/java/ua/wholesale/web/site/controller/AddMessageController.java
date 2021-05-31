package ua.wholesale.web.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.AddMessageControllerService;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.utils.validator.GoodsValidator;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@Api(value = "Add ad")
public class AddMessageController {

    @Autowired
    private AddMessageControllerService addMessageControllerService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsValidator goodsValidator;

    @GetMapping("/addnotice")
    @ApiOperation(value = "Display forms for add good", response = String.class)
    public String addget(@AuthenticationPrincipal User user,
                         Model model) {
        model.addAttribute("user", user);

        return "addnotice";
    }


    @PostMapping("/addnotice")
    @ApiOperation(value = "Save good", response = String.class)
    @Transactional
    public String addpost(
            @AuthenticationPrincipal User user,
            @Valid
            @ModelAttribute("messageForm") Goods good,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            @RequestParam("files") MultipartFile files,
            @RequestParam("filesq") MultipartFile filesq
    ) throws IOException {

        good.setAuthor(user);

        goodsValidator.validate(good, bindingResult);

        if(bindingResult.hasErrors())
        {
            goodsValidator.bindingResultErrors(bindingResult, model);
            model.addAttribute("messages", good);

            return "addnotice";
        } else
        {

            addMessageControllerService.saveFile1(good, file);
            addMessageControllerService.saveFile2(good, files);
            addMessageControllerService.saveFile3(good, filesq);
            goodsService.save(good);

            return "redirect:/main";
        }
    }
}