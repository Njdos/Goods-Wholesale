package ua.wholesale.web.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.RegistrationControllerService;
import ua.wholesale.web.site.service.UserService;
import ua.wholesale.web.site.utils.validator.UserValidator;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@Api(value = "Register user")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationControllerService registrationControllerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;


    @GetMapping("/registration")
    @ApiOperation(value = "Display register forms", response = String.class)
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    @ApiOperation(value = "Add user", response = String.class)
    @Transactional
    public String addUser(
            @Valid @ModelAttribute("userForm") User user,
            BindingResult bindingResult,
            Model model,
            @RequestParam("fileq") MultipartFile fileq,
            @RequestParam("roles") String roles
    ) throws IOException {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            userValidator.bindingResultErrors(bindingResult, model);
            model.addAttribute("user", user);
            return "registration";
        }
        else {
            registrationControllerService.isFilet(user,fileq);
            registrationControllerService.RolesChose(roles);
            user.setStatus(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/main";
        }
    }
}