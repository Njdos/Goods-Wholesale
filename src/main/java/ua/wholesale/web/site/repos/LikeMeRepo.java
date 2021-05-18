package ua.wholesale.web.site.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.LikeMe;

import java.util.Set;

@Component
public interface LikeMeRepo extends JpaRepository<LikeMe, Long> {


    Set<LikeMe> findByUserId(Long id);

    LikeMe findByMessIdAndUserId(Long messId, Long userId);

}