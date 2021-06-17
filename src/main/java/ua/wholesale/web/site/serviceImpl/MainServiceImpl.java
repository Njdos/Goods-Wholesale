package ua.wholesale.web.site.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.service.GoodsService;
import ua.wholesale.web.site.service.MainService;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public List<Goods> searchByFilter(String filter, String heading, String pricemin, String pricemax) {

//Тільки заголовок є
        if ((filter != null || !filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            return goodsService.filterTitleAndPrice1AndPrice2(filter, pricemin1, pricemax1);
        }
//Тільки рубрика все
        else if ((filter == null || filter.isEmpty()) && (heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            return  goodsService.filterPrice1AndPrice2(pricemin1, pricemax1);
        }
//Є рубрика
        else if ((filter == null || filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            return goodsService.filterHeadingAndPrice1AndPrice2(heading, pricemin1, pricemax1);
        }
//Є заголовок і рубрика
        else if ((filter != null || !filter.isEmpty()) && (heading != null || !heading.isEmpty()) && (!heading.equals("All"))) {
            long pricemin1 = Long.parseLong(pricemin);
            long pricemax1 = Long.parseLong(pricemax);
            return goodsService.filterTitleAndHeadingAndPrice1AndPrice2(filter, heading, pricemin1, pricemax1);
        } else {
            return goodsService.findAll();
        }

    }
}
