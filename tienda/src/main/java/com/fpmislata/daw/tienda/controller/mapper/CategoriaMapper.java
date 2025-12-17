package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.CategoriaRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.CategoriaDetailResponse;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;

public class CategoriaMapper {

    private static CategoriaMapper INSTANCE;

    private CategoriaMapper() {
    }

    public static CategoriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoriaMapper();
        }
        return INSTANCE;
    }

    public CategoriaDetailResponse fromDtoToDetail(CategoriaDto categoriaDto) {
        if (categoriaDto == null) {
            return null;
        }
        return new CategoriaDetailResponse(
                categoriaDto.id(),
                categoriaDto.nombre(),
                categoriaDto.descripcion());
    }

    public CategoriaDto fromRequestToDto(CategoriaRequest categoriaRequest) {
        if (categoriaRequest == null) {
            return null;
        }
        return new CategoriaDto(
                null,
                categoriaRequest.nombre(),
                categoriaRequest.descripcion());
    }
}
