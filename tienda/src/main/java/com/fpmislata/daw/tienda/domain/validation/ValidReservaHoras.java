package com.fpmislata.daw.tienda.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.fpmislata.daw.tienda.domain.validation.validator.ReservaHorasValidator;

@Documented
@Constraint(validatedBy = ReservaHorasValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReservaHoras {
    String message() default "La hora final debe ser posterior a la hora de inicio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}