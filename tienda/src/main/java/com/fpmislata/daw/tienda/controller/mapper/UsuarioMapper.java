package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.UsuarioInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.UsuarioUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.UsuarioDetailResponse;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;

public class UsuarioMapper {
    private static UsuarioMapper INSTANCE;

    private UsuarioMapper() {
    }

    public static UsuarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsuarioMapper();
        }
        return INSTANCE;
    }

    public UsuarioDetailResponse fromDtoToDetail(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        return new UsuarioDetailResponse(
                usuarioDto.email(),
                usuarioDto.nombre(),
                usuarioDto.rol());
    }

    public UsuarioDto fromInsertToDto(UsuarioInsertRequest usuarioInsertRequest) {
        if (usuarioInsertRequest == null) {
            return null;
        }
        return new UsuarioDto(
                null,
                usuarioInsertRequest.email(),
                usuarioInsertRequest.nombre(),
                usuarioInsertRequest.rol());

    }

    public UsuarioDto fromUpdateToDto(UsuarioUpdateRequest usuarioUpdateRequest) {
        if (usuarioUpdateRequest == null) {
            return null;
        }
        return new UsuarioDto(
                null,
                usuarioUpdateRequest.email(),
                usuarioUpdateRequest.nombre(),
                null);
    }
}
