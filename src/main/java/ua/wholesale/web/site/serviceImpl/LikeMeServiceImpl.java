package ua.wholesale.web.site.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.model.LikeMe;
import ua.wholesale.web.site.repos.LikeMeRepo;
import ua.wholesale.web.site.service.LikeMeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LikeMeServiceImpl implements LikeMeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeMeRepo likeMeRepo;


    @Override
    public void save(Long idMess, Long idUser) {
        LikeMe likeMe = new LikeMe();
        likeMe.setMessId(idMess);
        likeMe.setUserId(idUser);
        likeMeRepo.save(likeMe);
    }

    @Override
    public void delete(Long idMess, Long idUser) {
        LikeMe likeMe = likeMeRepo.findByMessIdAndUserId(idMess,idUser);
        if (likeMe != null){
            likeMeRepo.delete(likeMe);
        }

    }

    @Override
    public LikeMe findById(Long id) {
        return likeMeRepo.getOne(id);
    }

    @Override
    public Set<LikeMe> findByUserId(Long id) {
        return likeMeRepo.findByUserId(id);
    }

    @Override
    public List<Long> getListLikeMeIdByUserId(Long id) {
        String sql = "SELECT mess_id FROM like_me WHERE user_id = " + id;
        List<Long> listLikeMeId = new ArrayList<>();

        try  {
            listLikeMeId = jdbcTemplate.queryForList(sql,Long.TYPE);
        }catch (Exception e){
            System.out.println(e);
        }
        return listLikeMeId;
    }

    @Override
    public boolean findByMessIdAndUserId(Long messId, Long userId) {
        LikeMe likeMe = likeMeRepo.findByMessIdAndUserId(messId,userId);
        if (likeMe==null){
            return true;
        }else {
            return false;
        }
    }
}
