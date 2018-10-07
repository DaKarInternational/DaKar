package com.dakar.dakar.validator;

import com.dakar.dakar.models.Journey;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for Journey class
 */
@Component
public class JourneyValidator implements Validator{

    // https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validator

    /**
     * This Validator validates *just* Journey instances
     */
    public boolean supports(Class clazz) {
        return Journey.class.equals(clazz);
    }

    public void validate(Object obj, Errors e) {
        System.out.println("aaa validator ");
        ValidationUtils.rejectIfEmpty(e, "destination", "La destination doit être renseignée");
        Journey j = (Journey) obj;

//        if (j.getPrice() < 0) {
//            e.rejectValue("age", "negativevalue");
//        }
    }
}
