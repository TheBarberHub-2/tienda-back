package com.fpmislata.daw.tienda.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.fpmislata.daw.tienda.domain.validation.validator.ReservaTimestampsValidator;

@Documented
@Constraint(validatedBy = ReservaTimestampsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReservaTimestamps {
    String message() default "updatedAt debe ser posterior a createdAt";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}