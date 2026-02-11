package com.fpmislata.daw.tienda.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.model.Reserva;
import com.fpmislata.daw.tienda.domain.model.ReservaProducto;
import com.fpmislata.daw.tienda.domain.model.Usuario;
import com.fpmislata.daw.tienda.domain.model.Peluqueria;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaProductoDto;
import com.fpmislata.daw.tienda.enums.DiaSemana;

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
    // MODEL → ENTITY
    // ---------------------------------------------------------
    public ReservaEntity fromModelToEntity(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        Usuario usuario = reserva.getCliente();
        Peluqueria peluqueria = reserva.getPeluqueria();

        return new ReservaEntity(
                reserva.getId(),
                usuario == null ? null : UsuarioMapper.getInstance().fromModelToEntity(usuario),
                peluqueria == null ? null : PeluqueriaMapper.getInstance().fromModelToEntity(peluqueria),
                reserva.getDiaSemana().getValue(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFinal(),
                reserva.getPrecioTotal(),
                reserva.getEstado(),
                reserva.getCreatedAt(),
                reserva.getUpdatedAt(),
                reserva.getIban(),
                mapModelProductos(reserva.getProductos()));
    }

    private List<ReservaProductoEntity> mapModelProductos(List<ReservaProducto> productos) {
        if (productos == null || productos.isEmpty()) {
            return List.of();
        }
        return productos.stream()
                .map(ReservaProductoMapper.getInstance()::fromModelToEntity)
                .toList();
    }

    // ---------------------------------------------------------
    // ENTITY → MODEL
    // ---------------------------------------------------------
    public Reserva fromEntityToModel(ReservaEntity entity) {
        if (entity == null) {
            return null;
        }

        Usuario cliente = entity.cliente() == null ? null
                : UsuarioMapper.getInstance().fromEntityToModel(entity.cliente());

        Peluqueria peluqueria = entity.peluqueria() == null ? null
                : PeluqueriaMapper.getInstance().fromEntityToModel(entity.peluqueria());

        return new Reserva(
                entity.id(),
                cliente,
                peluqueria,
                DiaSemana.fromValue(entity.diaSemana()),
                entity.fechaReserva(),
                entity.horaInicio(),
                entity.horaFinal(),
                entity.precioTotal(),
                entity.estado(),
                entity.createdAt(),
                entity.updatedAt(),
                entity.iban(),
                mapEntityProductos(entity.productos()));
    }

    private List<ReservaProducto> mapEntityProductos(List<ReservaProductoEntity> productos) {
        if (productos == null || productos.isEmpty()) {
            return new ArrayList<>();
        }
        return productos.stream()
                .map(ReservaProductoMapper.getInstance()::fromEntityToModel)
                .toList();
    }

    // ---------------------------------------------------------
    // DTO → MODEL
    // ---------------------------------------------------------
    public Reserva fromDtoToModel(ReservaDto dto) {
        if (dto == null) {
            return null;
        }

        Usuario cliente = dto.cliente() == null ? null
                : UsuarioMapper.getInstance().fromDtoToModel(dto.cliente());

        Peluqueria peluqueria = dto.peluqueria() == null ? null
                : PeluqueriaMapper.getInstance().fromDtoToModel(dto.peluqueria());

        return new Reserva(
                dto.id(),
                cliente,
                peluqueria,
                dto.diaSemana(),
                dto.fechaReserva(),
                dto.horaInicio(),
                dto.horaFinal(),
                dto.precioTotal(),
                dto.estado(),
                dto.createdAt(),
                dto.updatedAt(),
                dto.iban(),
                mapDtoProductos(dto.productos()));
    }

    private List<ReservaProducto> mapDtoProductos(List<ReservaProductoDto> productos) {
        if (productos == null || productos.isEmpty()) {
            return new ArrayList<>();
        }
        return productos.stream()
                .map(ReservaProductoMapper.getInstance()::fromDtoToModel)
                .toList();
    }

    // ---------------------------------------------------------
    // MODEL → DTO
    // ---------------------------------------------------------
    public ReservaDto fromModelToDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        return new ReservaDto(
                reserva.getId(),
                UsuarioMapper.getInstance().fromModelToDto(reserva.getCliente()),
                PeluqueriaMapper.getInstance().fromModelToDto(reserva.getPeluqueria()),
                reserva.getDiaSemana(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFinal(),
                reserva.getPrecioTotal(),
                reserva.getEstado(),
                reserva.getCreatedAt(),
                reserva.getUpdatedAt(),
                reserva.getIban(),
                mapModelToDtoProductos(reserva.getProductos()));
    }

    private List<ReservaProductoDto> mapModelToDtoProductos(List<ReservaProducto> productos) {
        if (productos == null || productos.isEmpty()) {
            return List.of();
        }
        return productos.stream()
                .map(ReservaProductoMapper.getInstance()::fromModelToDto)
                .toList();
    }
}