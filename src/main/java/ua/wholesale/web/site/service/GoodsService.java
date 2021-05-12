package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.Goods;

import java.util.List;
import java.util.Set;

@Component
public interface GoodsService {

    void save(Goods goods);

    void update(Goods goods);

    void delete(Goods goods);

    void deleteById(Long id);

    Goods getById(Long id);

    List<Goods> filterTitleAndHeadingAndPrice1AndPrice2(String title, String heading, long price1, long price2);

    List<Goods> filterTitleAndPrice1AndPrice2 (String title, long price1, long price2);

    List<Goods> filterHeadingAndPrice1AndPrice2 (String heading,long price1, long price2);

    List<Goods> filterPrice1AndPrice2 (long price1, long price2);

    Set<Goods> findById(long id);

    List<Goods> findAll();
}
