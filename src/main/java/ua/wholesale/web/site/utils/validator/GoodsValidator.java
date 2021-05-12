package ua.wholesale.web.site.utils.validator;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Component
public interface GoodsValidator {

    void validate(Object o, Errors errors);

    void bindingResultErrors(BindingResult bindingResult, Model model);
}
