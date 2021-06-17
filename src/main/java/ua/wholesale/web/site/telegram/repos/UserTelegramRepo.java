package ua.wholesale.web.site.telegram.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.telegram.model.UserTelegram;

import java.util.Optional;

@Component
public interface UserTelegramRepo extends JpaRepository<UserTelegram, Long> {

    UserTelegram findByUserid(Long userid);

    Optional<UserTelegram> findById(Long id);

}
