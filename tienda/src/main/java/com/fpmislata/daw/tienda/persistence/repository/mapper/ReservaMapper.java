package com.fpmislata.daw.tienda.persistence.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;

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
    // ENTITY → JPA
    // ---------------------------------------------------------
    public ReservaJpaEntity fromEntityToJpa(ReservaEntity entity) {
        if (entity == null) {
            return null;
        }

        List<ReservaProductoJpaEntity> productosJpa = new ArrayList<>();
        if (entity.productos() != null && !entity.productos().isEmpty()) {
            productosJpa = entity.productos().stream()
                    .map(ReservaProductoMapper.getInstance()::fromEntityToJpa)
                    .toList();
        }

        return new ReservaJpaEntity(
                entity.id(),
                UsuarioMapper.getInstance().fromEntityToJpa(entity.cliente()),
                PeluqueriaMapper.getInstance().fromEntityToJpa(entity.peluqueria()),
                entity.diaSemana(),
                entity.fechaReserva(),
                entity.horaInicio(),
                entity.horaFinal(),
                entity.precioTotal(),
                entity.estado(),
                entity.createdAt(),
                entity.updatedAt(),
                productosJpa);
    }

    // ---------------------------------------------------------
    // JPA → ENTITY
    // ---------------------------------------------------------
    public ReservaEntity fromJpaToEntity(ReservaJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        List<ReservaProductoEntity> productos = new ArrayList<>();
        if (jpa.getProductos() != null && !jpa.getProductos().isEmpty()) {
            productos = jpa.getProductos().stream()
                    .map(ReservaProductoMapper.getInstance()::fromJpaToEntity)
                    .toList();
        }

        return new ReservaEntity(
                jpa.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(jpa.getCliente()),
                PeluqueriaMapper.getInstance().fromJpaToEntity(jpa.getPeluqueria()),
                jpa.getDiaSemana(),
                jpa.getFechaReserva(),
                jpa.getHoraInicio(),
                jpa.getHoraFinal(),
                jpa.getPrecioTotal(),
                jpa.getEstado(),
                jpa.getCreatedAt(),
                jpa.getUpdatedAt(),
                productos);
    }
}