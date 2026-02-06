package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesDto;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesInputDto;

public interface CarritoService {

    CarritoDto calcularCarrito(CarritoInputDto dto);

    SlotsDisponiblesDto obtenerSlotsDisponibles(SlotsDisponiblesInputDto dto);
}
