package ua.wholesale.web.site.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.service.GoodsService;

@Component
public class GoodsValidatorImpl implements Validator, GoodsValidator {

    @Autowired
    private GoodsService goodsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Goods.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Goods goods = (Goods) o;
        if (goods.getTitle().length()<1 || goods.getTitle().length()>255){
            errors.rejectValue("title","Size.messageForm.title", "Title not validates length(must have < 1 and > 255)");
        }

        if (goods.getDescription().length()<1 || goods.getDescription().length()>255){
            errors.rejectValue("description","Size.messageForm.description", "Description not validates length(must have < 1 and > 255)");
        }
        if(goods.getPrice()<0 || goods.getPrice()>9999999){
            errors.rejectValue("price", "Size.messageForm.price", "Price not validates length(must have < 0  and > 9.999.999)");
        }
        if (goods.getPlace().length()<1 || goods.getPlace().length()>255){
            errors.rejectValue("place","Size.messageForm.place", "Place not validates length(must have < 1  and > 255)");
        }
        if (!goodsService.findByTitle(goods)){
            errors.rejectValue("title","Size.messageForm.title", "Title of good are used");
        }
    }

    @Override
    public void bindingResultErrors(BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("title")) {
                model.addAttribute("titleError", bindingResult.getFieldError("title").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("description")) {
                model.addAttribute("descriptionError", bindingResult.getFieldError("description").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("price")) {
                model.addAttribute("priceError", bindingResult.getFieldError("price").getDefaultMessage());
            }

            if (bindingResult.hasFieldErrors("place")) {
                model.addAttribute("placeError", bindingResult.getFieldError("place").getDefaultMessage());
            }
        }
    }
}
