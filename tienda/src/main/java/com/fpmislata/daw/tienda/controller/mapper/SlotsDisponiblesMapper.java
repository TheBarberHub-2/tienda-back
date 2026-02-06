package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.SlotsDisponiblesRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.SlotsDisponiblesResponse;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesDto;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesInputDto;

public class SlotsDisponiblesMapper {

    private static SlotsDisponiblesMapper INSTANCE;

    private SlotsDisponiblesMapper() {
    }

    public static SlotsDisponiblesMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SlotsDisponiblesMapper();
        }
        return INSTANCE;
    }

    public SlotsDisponiblesInputDto fromRequestToInputDto(SlotsDisponiblesRequest request) {
        if (request == null)
            return null;

        return new SlotsDisponiblesInputDto(
                request.peluqueriaId(),
                request.fecha(),
                request.duracionTotal());
    }

    public SlotsDisponiblesResponse fromDtoToResponse(SlotsDisponiblesDto dto) {
        if (dto == null)
            return null;

        return new SlotsDisponiblesResponse(
                dto.peluqueriaId(),
                dto.fecha(),
                dto.horasDisponibles());
    }
}