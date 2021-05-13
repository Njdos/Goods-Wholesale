package ua.wholesale.web.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.Role;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.utils.validator.GoodsValidator;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class AddMessageController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.path}s")
    private String uploadPaths;

    @Value("${upload.path}sq")
    private String uploadPathsq;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsValidator goodsValidator;

    @GetMapping("/addnotice")
    public String addget(@AuthenticationPrincipal User user,
                         Model model) {
        model.addAttribute("user", user);
        return "addnotice";
    }

    @PostMapping("/addnotice")
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

        goodsValidator.validate(good, bindingResult);

        goodsValidator.bindingResultErrors(bindingResult, model);

        good.setAuthor(user);


        if (good.getTitle() != null && !good.getTitle().isEmpty() &&
                good.getDescription() != null && !good.getDescription().isEmpty() &&
                good.getPrice() >= 0 && good.getPrice() <= 9999999 &&
                good.getPlace() != null && !good.getPlace().isEmpty() &&
                saveFile1(good, file) &&
                saveFile2(good, files) &&
                saveFile3(good, filesq)
        ) {
            goodsService.save(good);
            model.addAttribute("message", null);
            Iterable<Goods> goods = goodsService.findAll();
            model.addAttribute("messages", goods);
            return "redirect:/main";
        } else {
            model.addAttribute("messages", good);
            return "addnotice";
        }
    }
    private boolean saveFile1(@Valid Goods good, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) { uploadDir.mkdir(); }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            good.setFilename(resultFilename);
            return true;
        } else {
            return false;
        }
    }

    private boolean saveFile2(@Valid Goods good, @RequestParam("files") MultipartFile files) throws IOException {
        if (files != null && !Objects.requireNonNull(files.getOriginalFilename()).isEmpty()) {
            File uploadDirs = new File(uploadPaths);
            if (!uploadDirs.exists()) { uploadDirs.mkdir();  }
            String uuidFiles = UUID.randomUUID().toString();
            String resultFilenames = uuidFiles + "." + files.getOriginalFilename();
            files.transferTo(new File(uploadPaths + "/" + resultFilenames));
            good.setFilenames(resultFilenames);
            return true;
        } else {
            return false;
        }
    }

    private boolean saveFile3(@Valid Goods good, @RequestParam("filesq") MultipartFile filesq) throws IOException {
        if (filesq != null && !Objects.requireNonNull(filesq.getOriginalFilename()).isEmpty()) {
            File uploadDirsq = new File(uploadPathsq);
            if (!uploadDirsq.exists()) {
                uploadDirsq.mkdir();
            }
            String uuidFilesq = UUID.randomUUID().toString();
            String resultFilenamesq = uuidFilesq + "." + filesq.getOriginalFilename();
            filesq.transferTo(new File(uploadPathsq + "/" + resultFilenamesq));
            good.setFilenamesq(resultFilenamesq);
            return true;
        } else {
            return false;
        }
    }
}
