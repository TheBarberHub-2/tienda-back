package com.fpmislata.daw.tienda.persistence.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;

public class PeluqueriaMapper {

    private static PeluqueriaMapper INSTANCE;

    private PeluqueriaMapper() {
    }

    public static PeluqueriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaJpaEntity fromEntityToJpa(PeluqueriaEntity peluqueriaEntity) {
        if (peluqueriaEntity == null) {
            return null;
        }
        List<ProductoJpaEntity> productos = new ArrayList<>();
        if (peluqueriaEntity.productos() != null && !peluqueriaEntity.productos().isEmpty()) {
            productos = peluqueriaEntity.productos().stream()
                    .map(ProductoMapper.getInstance()::fromEntityToJpa)
                    .toList();
        }
        return new PeluqueriaJpaEntity(
                peluqueriaEntity.id(),
                UsuarioMapper.getInstance().fromEntityToJpa(peluqueriaEntity.usuario()),
                peluqueriaEntity.municipio(),
                peluqueriaEntity.direccion(),
                peluqueriaEntity.telefono(),
                productos);
    }

    public PeluqueriaEntity fromJpaToEntity(PeluqueriaJpaEntity peluqueriaJpaEntity) {
        if (peluqueriaJpaEntity == null) {
            return null;
        }
        List<ProductoEntity> productos = new ArrayList<>();
        if (peluqueriaJpaEntity.getProductos() != null && !peluqueriaJpaEntity.getProductos().isEmpty()) {
            productos = peluqueriaJpaEntity.getProductos().stream()
                    .map(ProductoMapper.getInstance()::fromJpaToEntity)
                    .toList();
        }
        return new PeluqueriaEntity(
                peluqueriaJpaEntity.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(peluqueriaJpaEntity.getUsuario()),
                peluqueriaJpaEntity.getMunicipio(),
                peluqueriaJpaEntity.getDireccion(),
                peluqueriaJpaEntity.getTelefono(),
                productos);
    }
}
