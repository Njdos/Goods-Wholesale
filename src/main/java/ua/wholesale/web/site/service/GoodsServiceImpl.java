package ua.wholesale.web.site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.repos.GoodsRepo;

import java.util.List;
import java.util.Set;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsRepo goodsRepo;

    @Override
    public void save(Goods Goods) {
        goodsRepo.save(Goods);
    }

    @Override
    public void update(Goods Goods) {
        goodsRepo.save(Goods);
    }

    @Override
    public void delete(Goods Goods) {
        goodsRepo.delete(Goods);
    }

    @Override
    public void deleteById(Long id) {
        if (goodsRepo.existsById(id)) {
            goodsRepo.deleteById(id);
        }
    }

    @Override
    public boolean findByTitle(Goods goods) {
        Goods userFromDb = goodsRepo.findByTitle(goods.getTitle());
        if (userFromDb != null)  return false;
         return true;
    }

    @Override
    public Goods getById(Long id) {
        return goodsRepo.findById(id).get();
    }

    @Override
    public List<Goods> filterTitleAndHeadingAndPrice1AndPrice2(String title, String heading, long price1, long price2) {
        return goodsRepo.findByTitleContainingAndHeadingContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (title, heading, price1, price2);
    }

    @Override
    public List<Goods> filterTitleAndPrice1AndPrice2(String title, long price1, long price2) {
        return goodsRepo.findByTitleContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (title, price1, price2);
    }

    @Override
    public List<Goods> filterHeadingAndPrice1AndPrice2(String heading, long price1, long price2) {
        return goodsRepo.findByHeadingContainingAndPriceGreaterThanEqualAndPriceLessThanEqual (heading, price1, price2);
    }

    @Override
    public List<Goods> filterPrice1AndPrice2(long price1, long price2) {
        return goodsRepo.findByPriceGreaterThanEqualAndPriceLessThanEqual (price1, price2);
    }

    @Override
    public Set<Goods> findById(long id) {
        return goodsRepo.findById(id);
    }

    @Override
    public List<Goods> findAll() {
        return goodsRepo.findAll();
    }
}
