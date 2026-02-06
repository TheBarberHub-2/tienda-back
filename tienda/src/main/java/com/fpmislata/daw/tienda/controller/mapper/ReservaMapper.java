package com.fpmislata.daw.tienda.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.controller.webModel.request.ReservaInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.ReservaProductoResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.ReservaResponse;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.DiaSemana;
import com.fpmislata.daw.tienda.enums.EstadoReserva;

public class ReservaMapper {

    private static ReservaMapper INSTANCE;

    private ReservaMapper() {
    }

    public static ReservaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservaMapper();
        }
        return INSTANCE;
    }

    // ---------------------------------------------------------
    // REQUEST → DTO
    // ---------------------------------------------------------
    public ReservaDto fromRequestToDto(ReservaInsertRequest request, UsuarioDto clienteDto,
            PeluqueriaDto peluqueriaDto, List<ReservaProductoDto> productoDtos) {
        if (request == null) {
            return null;
        }

        // Construimos un DTO mínimo.
        // El servicio ya se encargará de cargar UsuarioDto, PeluqueriaDto y ProductoDto
        // completos.
        return new ReservaDto(
                null, // id
                clienteDto, // cliente (lo carga el servicio)
                peluqueriaDto, // peluquería (lo carga el servicio)
                DiaSemana.fromValue((byte) request.fechaReserva().getDayOfWeek().getValue()),
                request.fechaReserva(),
                request.horaInicio(),
                null, // horaFinal (la calcula el servicio)
                0.0, // precioTotal (lo calcula el servicio)
                EstadoReserva.Pendiente, // estado inicial
                null, // createdAt
                null, // updatedAt
                productoDtos);
    }

    // ---------------------------------------------------------
    // DTO → RESPONSE
    // ---------------------------------------------------------
    public ReservaResponse fromDtoToResponse(ReservaDto dto) {
        if (dto == null) {
            return null;
        }

        List<ReservaProductoResponse> productosResponse = new ArrayList<>();
        if (dto.productos() != null && !dto.productos().isEmpty()) {
            productosResponse = dto.productos().stream()
                    .map(this::fromProductoDtoToResponse)
                    .toList();
        }

        return new ReservaResponse(
                dto.id(),
                dto.cliente() != null ? dto.cliente().id() : null,
                dto.peluqueria() != null ? dto.peluqueria().id() : null,
                dto.peluqueria() != null ? dto.peluqueria().usuario().nombre() : null,
                dto.fechaReserva(),
                dto.horaInicio(),
                dto.horaFinal(),
                dto.precioTotal(),
                dto.estado().name(),
                productosResponse);
    }

    private ReservaProductoResponse fromProductoDtoToResponse(ReservaProductoDto dto) {
        return new ReservaProductoResponse(
                dto.id(),
                dto.producto() != null ? dto.producto().id() : null,
                dto.producto() != null ? dto.producto().nombre() : null,
                dto.producto() != null ? dto.producto().precio() : null,
                dto.producto() != null ? dto.producto().duracion() : null);
    }
}