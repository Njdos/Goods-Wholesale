package ua.wholesale.web.site.telegram.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.telegram.model.ProfileUsers;

import java.util.ArrayList;

@Component
public interface ProfileUsersRepo extends JpaRepository<ProfileUsers, Long> {

    ArrayList<ProfileUsers> findByProfileUserid(Long userid);


}
