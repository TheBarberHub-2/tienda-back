package com.fpmislata.daw.tienda.domain.service;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;

public interface PeluqueriaHorarioService {
    PeluqueriaHorarioDto create(PeluqueriaHorarioDto peluqueriaHorarioDto);

    PeluqueriaHorarioDto update(PeluqueriaHorarioDto peluqueriaHorarioDto);

    List<PeluqueriaHorarioDto> findByPeluqueria(long peluqueriaId);

    void validarHorarios(PeluqueriaHorarioDto peluqueriaHorarioDto);

    void deleteById(long id);

    Optional<PeluqueriaHorarioDto> findById(long id);
}
