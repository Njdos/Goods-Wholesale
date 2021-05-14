package ua.wholesale.web.site.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;

import java.util.List;
import java.util.Set;

@Component
public interface GoodsRepo extends JpaRepository<Goods, Long> {
    List<Goods> findByTitleContainingAndHeadingContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (String title, String heading, long price1, long price2);

    List<Goods> findByTitleContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (String title, long price1, long price2);

    List<Goods> findByHeadingContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (String heading, long price1, long price2);

    List<Goods> findByPriceGreaterThanEqualAndPriceLessThanEqual (long price1, long price2);

    List<Goods> findAll();

    Set<Goods> findById(long id);

    Goods findByTitle(String title);
}
