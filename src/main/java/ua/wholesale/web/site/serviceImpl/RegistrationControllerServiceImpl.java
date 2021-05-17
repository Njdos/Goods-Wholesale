package ua.wholesale.web.site.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.wholesale.web.site.model.Role;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.RegistrationControllerService;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Service
public class RegistrationControllerServiceImpl implements RegistrationControllerService {

    @Value("${upload.path}q")
    private String uploadPathq;

    @Override
    public void RolesChose(String role) {
        User user =  new User();
        switch (role){
            case "USER" -> user.setRoles(Collections.singleton(Role.USER));
            case "SELLER" -> user.setRoles(Collections.singleton(Role.SELLER));
            default -> user.setRoles(Collections.singleton(Role.ADMIN));
        }
    }

    @Override
    public void isFilet(User user, MultipartFile fileq)throws IOException {
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
