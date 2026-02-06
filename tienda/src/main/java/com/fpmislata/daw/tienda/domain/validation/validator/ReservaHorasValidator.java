package com.fpmislata.daw.tienda.domain.validation.validator;

import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.validation.ValidReservaHoras;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservaHorasValidator implements ConstraintValidator<ValidReservaHoras, ReservaDto> {

    @Override
    public boolean isValid(ReservaDto dto, ConstraintValidatorContext context) {
        if (dto == null)
            return true;
        if (dto.horaInicio() == null || dto.horaFinal() == null)
            return true;

        return dto.horaFinal().isAfter(dto.horaInicio());
    }
}