package ua.wholesale.web.site.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Goods;

import java.io.IOException;

@Controller
public interface AddMessageControllerService {

    void saveFile1(Goods good, MultipartFile file) throws IOException;

    void saveFile2(Goods good, MultipartFile files) throws IOException;

    void saveFile3(Goods good, MultipartFile filesq) throws IOException;

}
