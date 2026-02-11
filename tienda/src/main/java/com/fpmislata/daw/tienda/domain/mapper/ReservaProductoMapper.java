package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Reserva;
import com.fpmislata.daw.tienda.domain.model.ReservaProducto;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaProductoDto;
import com.fpmislata.daw.tienda.enums.DiaSemana;

public class ReservaProductoMapper {

    private static ReservaProductoMapper INSTANCE;

    private ReservaProductoMapper() {
    }

    public static ReservaProductoMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservaProductoMapper();
        }
        return INSTANCE;
    }

    // ---------------------------------------------------------
    // MODEL → ENTITY
    // ---------------------------------------------------------
    public ReservaProductoEntity fromModelToEntity(ReservaProducto reservaProducto) {
        if (reservaProducto == null) {
            return null;
        }

        Reserva reserva = reservaProducto.getReserva();
        ReservaEntity reservaEntity = null;
        if (reserva != null) {
            reservaEntity = new ReservaEntity(
                    reserva.getId(),
                    UsuarioMapper.getInstance().fromModelToEntity(reserva.getCliente()),
                    PeluqueriaMapper.getInstance().fromModelToEntity(reserva.getPeluqueria()),
                    reserva.getDiaSemana().getValue(),
                    reserva.getFechaReserva(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFinal(),
                    reserva.getPrecioTotal(),
                    reserva.getEstado(),
                    reserva.getCreatedAt(),
                    reserva.getUpdatedAt(),
                    reserva.getIban(),
                    null);
        }

        Producto producto = reservaProducto.getProducto();
        ProductoEntity productoEntity = null;
        if (producto != null) {
            productoEntity = new ProductoEntity(
                    producto.getId(),
                    CategoriaMapper.getInstance().fromModelToEntity(producto.getCategoria()),
                    PeluqueriaMapper.getInstance().fromModelToEntity(producto.getPeluqueria()),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getDuracion());
        }

        return new ReservaProductoEntity(
                reservaProducto.getId(),
                reservaEntity,
                productoEntity);
    }

    // ---------------------------------------------------------
    // ENTITY → MODEL
    // ---------------------------------------------------------
    public ReservaProducto fromEntityToModel(ReservaProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        ReservaEntity reservaEntity = entity.reserva();
        Reserva reserva = null;
        if (reservaEntity != null) {
            reserva = new Reserva(
                    reservaEntity.id(),
                    UsuarioMapper.getInstance().fromEntityToModel(reservaEntity.cliente()),
                    PeluqueriaMapper.getInstance().fromEntityToModel(reservaEntity.peluqueria()),
                    DiaSemana.fromValue(reservaEntity.diaSemana()),
                    reservaEntity.fechaReserva(),
                    reservaEntity.horaInicio(),
                    reservaEntity.horaFinal(),
                    reservaEntity.precioTotal(),
                    reservaEntity.estado(),
                    reservaEntity.createdAt(),
                    reservaEntity.updatedAt(),
                    reservaEntity.iban(),
                    null);
        }

        ProductoEntity productoEntity = entity.producto();
        Producto producto = null;
        if (productoEntity != null) {
            producto = new Producto(
                    productoEntity.id(),
                    CategoriaMapper.getInstance().fromEntityToModel(productoEntity.categoria()),
                    PeluqueriaMapper.getInstance().fromEntityToModel(productoEntity.peluqueria()),
                    productoEntity.nombre(),
                    productoEntity.precio(),
                    productoEntity.duracion());
        }

        return new ReservaProducto(
                entity.id(),
                reserva,
                producto);
    }

    // ---------------------------------------------------------
    // DTO → MODEL
    // ---------------------------------------------------------
    public ReservaProducto fromDtoToModel(ReservaProductoDto dto) {
        if (dto == null) {
            return null;
        }

        Reserva reserva = dto.reserva() == null ? null
                : ReservaMapper.getInstance().fromDtoToModel(dto.reserva());

        Producto producto = dto.producto() == null ? null
                : ProductoMapper.getInstance().fromDtoToModel(dto.producto());

        return new ReservaProducto(
                dto.id(),
                reserva,
                producto);
    }

    // ---------------------------------------------------------
    // MODEL → DTO
    // ---------------------------------------------------------
    public ReservaProductoDto fromModelToDto(ReservaProducto reservaProducto) {
        if (reservaProducto == null) {
            return null;
        }

        return new ReservaProductoDto(
                reservaProducto.getId(),
                ReservaMapper.getInstance().fromModelToDto(reservaProducto.getReserva()),
                ProductoMapper.getInstance().fromModelToDto(reservaProducto.getProducto()));
    }
}