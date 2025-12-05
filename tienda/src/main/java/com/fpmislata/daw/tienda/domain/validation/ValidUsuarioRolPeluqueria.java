package com.fpmislata.daw.tienda.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.fpmislata.daw.tienda.domain.validation.validator.UsuarioRolPeluqueriaValidator;

@Documented
@Constraint(validatedBy = UsuarioRolPeluqueriaValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsuarioRolPeluqueria {
    String message() default "El usuario debe tener rol Peluqueria";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}