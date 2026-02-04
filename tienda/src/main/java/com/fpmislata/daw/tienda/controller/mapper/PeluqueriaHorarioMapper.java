package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaHorarioRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaHorarioResponse;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;

public class PeluqueriaHorarioMapper {

    private static PeluqueriaHorarioMapper INSTANCE;

    private PeluqueriaHorarioMapper() {
    }

    public static PeluqueriaHorarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaHorarioMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaHorarioResponse fromDtoToResponse(PeluqueriaHorarioDto peluqueriaHorarioDto) {
        if (peluqueriaHorarioDto == null) {
            return null;
        }

        return new PeluqueriaHorarioResponse(
                peluqueriaHorarioDto.id(),
                PeluqueriaMapper.getInstance().fromDtoToSummary(peluqueriaHorarioDto.peluqueria()),
                peluqueriaHorarioDto.diaSemana(),
                peluqueriaHorarioDto.horaApertura(),
                peluqueriaHorarioDto.horaCierre());
    }

    public PeluqueriaHorarioDto fromRequestToDto(PeluqueriaHorarioRequest peluqueriaHorarioRequest) {
        if (peluqueriaHorarioRequest == null) {
            return null;
        }

        return new PeluqueriaHorarioDto(
                null,
                null,
                peluqueriaHorarioRequest.diaSemana(),
                peluqueriaHorarioRequest.horaApertura(),
                peluqueriaHorarioRequest.horaCierre());
    }
}
