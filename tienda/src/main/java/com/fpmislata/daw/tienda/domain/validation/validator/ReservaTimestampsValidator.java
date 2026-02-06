package com.fpmislata.daw.tienda.domain.validation.validator;

import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.validation.ValidReservaTimestamps;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservaTimestampsValidator implements ConstraintValidator<ValidReservaTimestamps, ReservaDto> {

    @Override
    public boolean isValid(ReservaDto dto, ConstraintValidatorContext context) {
        if (dto == null)
            return true;
        if (dto.createdAt() == null || dto.updatedAt() == null)
            return true;

        return dto.updatedAt().isAfter(dto.createdAt());
    }
}