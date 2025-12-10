package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.PeluqueriaJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ProductoJpaEntity;

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

    public ProductoJpaEntity fromEntityToJpa(ProductoEntity productoEntity) {
        if (productoEntity == null) {
            return null;
        }

        PeluqueriaEntity peluqueriaEntity = productoEntity.peluqueria();
        PeluqueriaJpaEntity peluqueriaJpa = null;
        if (peluqueriaEntity != null) {
            peluqueriaJpa = new PeluqueriaJpaEntity(
                    peluqueriaEntity.id(),
                    UsuarioMapper.getInstance().fromEntityToJpa(peluqueriaEntity.usuario()),
                    peluqueriaEntity.municipio(),
                    peluqueriaEntity.direccion(),
                    peluqueriaEntity.telefono(),
                    null);
        }

        return new ProductoJpaEntity(
                productoEntity.id(),
                CategoriaMapper.getInstance().fromEntityToJpa(productoEntity.categoria()),
                peluqueriaJpa,
                productoEntity.nombre(),
                productoEntity.precio(),
                productoEntity.duracion());
    }

    public ProductoEntity fromJpaToEntity(ProductoJpaEntity productoJpaEntity) {
        if (productoJpaEntity == null) {
            return null;
        }

        PeluqueriaJpaEntity peluqueriaJpa = productoJpaEntity.getPeluqueria();
        PeluqueriaEntity peluqueriaEntity = null;
        if (peluqueriaJpa != null) {
            peluqueriaEntity = new PeluqueriaEntity(
                    peluqueriaJpa.getId(),
                    UsuarioMapper.getInstance().fromJpaToEntity(peluqueriaJpa.getUsuario()),
                    peluqueriaJpa.getMunicipio(),
                    peluqueriaJpa.getDireccion(),
                    peluqueriaJpa.getTelefono(),
                    null);
        }

        return new ProductoEntity(
                productoJpaEntity.getId(),
                CategoriaMapper.getInstance().fromJpaToEntity(productoJpaEntity.getCategoria()),
                peluqueriaEntity,
                productoJpaEntity.getNombre(),
                productoJpaEntity.getPrecio(),
                productoJpaEntity.getDuracion());
    }
}
