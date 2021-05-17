package ua.wholesale.web.site.service;

import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.Goods;

import java.util.List;

@Component
public interface MainControllerService {

    List<Goods> searchByFilter(String filter, String heading, String pricemin, String pricemax);

}
