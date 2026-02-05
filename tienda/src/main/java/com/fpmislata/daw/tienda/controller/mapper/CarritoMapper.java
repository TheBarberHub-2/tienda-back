package com.fpmislata.daw.tienda.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.controller.webModel.request.CarritoRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.CarritoResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoSummaryResponse;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;

public class CarritoMapper {

    private static CarritoMapper INSTANCE;

    private CarritoMapper() {
    }

    public static CarritoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CarritoMapper();
        }
        return INSTANCE;
    }

    // ---------------------------------------------------------
    // REQUEST → DTO
    // ---------------------------------------------------------
    public CarritoDto fromRequestToDto(CarritoRequest request) {
        if (request == null) {
            return null;
        }
        return new CarritoDto(
                request.peluqueriaId(),
                null,
                null,
                null);
    }

    // ---------------------------------------------------------
    // DTO → RESPONSE
    // ---------------------------------------------------------
    public CarritoResponse fromDtoToResponse(CarritoDto dto) {
        if (dto == null) {
            return null;
        }

        List<ProductoSummaryResponse> productosResponse = new ArrayList<>();
        if (dto.productos() != null && !dto.productos().isEmpty()) {
            productosResponse = dto.productos().stream()
                    .map(ProductoMapper.getInstance()::fromDtoToSummary)
                    .toList();
        }

        return new CarritoResponse(
                productosResponse,
                dto.duracionTotal(),
                dto.precioTotal());
    }

    public CarritoInputDto fromRequestToInputDto(CarritoRequest request) {
        if (request == null) {
            return null;
        }
        return new CarritoInputDto(
                request.peluqueriaId(),
                request.productoIds());
    }
}