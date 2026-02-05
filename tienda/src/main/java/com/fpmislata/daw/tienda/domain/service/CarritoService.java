package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;

public interface CarritoService {

    CarritoDto calcularCarrito(CarritoInputDto dto);
}
