package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaProductoJpaEntity;

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
    // ENTITY → JPA
    // ---------------------------------------------------------
    public ReservaProductoJpaEntity fromEntityToJpa(ReservaProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ReservaProductoJpaEntity(
                entity.id(),
                ReservaMapper.getInstance().fromEntityToJpa(entity.reserva()),
                ProductoMapper.getInstance().fromEntityToJpa(entity.producto()));
    }

    // ---------------------------------------------------------
    // JPA → ENTITY
    // ---------------------------------------------------------
    public ReservaProductoEntity fromJpaToEntity(ReservaProductoJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        // ⚠️ Aquí evitamos el ciclo infinito:
        // Creamos una ReservaEntity "ligera" sin productos.
        ReservaJpaEntity reservaJpa = jpa.getReserva();

        ReservaEntity reservaLigera = new ReservaEntity(
                reservaJpa.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(reservaJpa.getCliente()),
                PeluqueriaMapper.getInstance().fromJpaToEntity(reservaJpa.getPeluqueria()),
                reservaJpa.getDiaSemana(),
                reservaJpa.getFechaReserva(),
                reservaJpa.getHoraInicio(),
                reservaJpa.getHoraFinal(),
                reservaJpa.getPrecioTotal(),
                reservaJpa.getEstado(),
                reservaJpa.getCreatedAt(),
                reservaJpa.getUpdatedAt(),
                null // ⚠️ NO mapeamos productos aquí
        );

        return new ReservaProductoEntity(
                jpa.getId(),
                reservaLigera,
                ProductoMapper.getInstance().fromJpaToEntity(jpa.getProducto()));
    }
}