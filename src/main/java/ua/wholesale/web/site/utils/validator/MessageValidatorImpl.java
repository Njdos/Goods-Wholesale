package ua.wholesale.web.site.utils.validator;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.wholesale.web.site.model.Goods;

public class MessageValidatorImpl implements Validator, GoodsValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Goods.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Goods message = (Goods) o;
        if (message.getTitle().length()<1 || message.getTitle().length()>255){
            errors.rejectValue("title","Size.messageForm.title", "Title not validates length(must have < 1 and > 255)");
        }
        if (message.getDescription().length()<1 || message.getDescription().length()>255){
            errors.rejectValue("description","Size.messageForm.description", "Description not validates length(must have < 1 and > 255)");
        }
        if(message.getPrice()<0 || message.getPrice()>9999999){
            errors.rejectValue("price", "Size.messageForm.price", "Price not validates length(must have < 0  and > 9.999.999)");
        }
        if (message.getPlace().length()<1 || message.getPlace().length()>255){
            errors.rejectValue("place","Size.messageForm.place", "Place not validates length(must have < 1  and > 255)");
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
