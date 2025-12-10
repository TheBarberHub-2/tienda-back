package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.ProductoInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.ProductoUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoSummaryResponse;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;

public class ProductoMapper {

    private static ProductoMapper INSTANCE;

    private ProductoMapper() {
    }

    public static ProductoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductoMapper();
        }
        return INSTANCE;
    }

    public ProductoDetailResponse fromDtoToDetail(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }
        return new ProductoDetailResponse(
                CategoriaMapper.getInstance().fromDtoToDetail(productoDto.categoria()),
                PeluqueriaMapper.getInstance().fromDtoToSummary(productoDto.peluqueria()),
                productoDto.nombre(),
                productoDto.precio(),
                productoDto.duracion());
    }

    public ProductoSummaryResponse fromDtoToSummary(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }
        return new ProductoSummaryResponse(
                productoDto.nombre(),
                productoDto.precio(),
                productoDto.duracion());
    }

    public ProductoDto fromInsertToDto(ProductoInsertRequest productoInsertRequest, CategoriaDto categoriaDto,
            PeluqueriaDto peluqueriaDto) {
        if (productoInsertRequest == null) {
            return null;
        }
        return new ProductoDto(
                null,
                categoriaDto,
                peluqueriaDto,
                productoInsertRequest.nombre(),
                productoInsertRequest.precio(),
                productoInsertRequest.duracion());
    }

    public ProductoDto fromUpdateToDto(ProductoUpdateRequest productoUpdateRequest) {
        if (productoUpdateRequest == null) {
            return null;
        }
        return new ProductoDto(
                null,
                null,
                null,
                productoUpdateRequest.nombre(),
                productoUpdateRequest.precio(),
                productoUpdateRequest.duracion());
    }
}
