package ua.wholesale.web.site.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Role;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.UserService;
import ua.wholesale.web.site.utils.validator.UserValidator;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Controller
@Api(value = "Register user")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Value("${upload.path}q")
    private String uploadPathq;

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
    public String addUser(
            @Valid
            @ModelAttribute("userForm") User user,
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
        } else {
            isFilet(user, fileq);
            user.setStatus(true);
            RolesChose(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
            return "redirect:/main";
        }
    }


    @ApiOperation(value = "Choose Role")
    private void RolesChose(String role){
        User user =  new User();
        switch (role){
            case "USER" -> user.setRoles(Collections.singleton(Role.USER));
            case "SELLER" -> user.setRoles(Collections.singleton(Role.SELLER));
            default -> user.setRoles(Collections.singleton(Role.ADMIN));
        }
    }

    @ApiOperation(value = "Save image" , response = Boolean.class)
    private void isFilet(User user, MultipartFile fileq) throws IOException {
        if (fileq != null && !Objects.requireNonNull(fileq.getOriginalFilename()).isEmpty()) {
            File uploadDirq = new File(uploadPathq);
            if (!uploadDirq.exists()) {
                uploadDirq.mkdir();
            }
            String uuidFileq = UUID.randomUUID().toString();
            String resultFilenameq = uuidFileq + "." + fileq.getOriginalFilename();
            fileq.transferTo(new File(uploadPathq + "/" + resultFilenameq));
            user.setFilenameq(resultFilenameq);
        }
    }

}