package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;

public interface SesionService {
    SesionDto getByToken(String token);

    SesionDto create(SesionDto sesionDto);

    void deleteByToken(String token);
}
