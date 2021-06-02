package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Goods;

import javax.validation.Valid;
import java.io.IOException;

@Component
public interface OnlyUserMessageService {

    void saveFile1(@Valid Goods good, @RequestParam("file") MultipartFile file) throws IOException;

    void saveFile2(@Valid Goods good, @RequestParam("files") MultipartFile files) throws IOException;

    void saveFile3(@Valid Goods good, @RequestParam("filesq") MultipartFile filesq) throws IOException;

}
