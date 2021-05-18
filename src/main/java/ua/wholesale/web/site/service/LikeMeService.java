package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.LikeMe;

import java.util.List;
import java.util.Set;

@Component
public interface LikeMeService {

    void save(Long idMess, Long idUser);

    void delete(Long idMess, Long idUser);

    LikeMe findById(Long id);

    Set<LikeMe> findByUserId(Long id);

    List<Long> getListLikeMeIdByUserId(Long id);

    boolean findByMessIdAndUserId(Long messId, Long userId);

}
