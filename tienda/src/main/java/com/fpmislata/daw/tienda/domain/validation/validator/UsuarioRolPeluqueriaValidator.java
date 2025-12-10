package com.fpmislata.daw.tienda.domain.validation.validator;

import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.validation.ValidUsuarioRolPeluqueria;
import com.fpmislata.daw.tienda.enums.Rol;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsuarioRolPeluqueriaValidator implements ConstraintValidator<ValidUsuarioRolPeluqueria, PeluqueriaDto> {

    @Override
    public boolean isValid(PeluqueriaDto peluqueriaDto, ConstraintValidatorContext context) {
        if (peluqueriaDto == null || peluqueriaDto.usuario() == null) {
            return true; // ya lo valida @NotNull
        }
        return Rol.Peluqueria.equals(peluqueriaDto.usuario().rol());
    }
}