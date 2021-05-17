package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.User;

import java.io.IOException;

@Component
public interface RegistrationControllerService {

    void RolesChose(String role);

    void isFilet(User user, MultipartFile fileq) throws IOException;

}
