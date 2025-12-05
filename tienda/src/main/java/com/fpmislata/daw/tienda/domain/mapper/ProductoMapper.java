package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
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

    public ProductoEntity fromModelToEntity(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoEntity(
                producto.getId(),
                CategoriaMapper.getInstance().fromModelToEntity(producto.getCategoria()),
                PeluqueriaMapper.getInstance().fromModelToEntity(producto.getPeluqueria()),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDuracion());
    }

    public Producto fromEntityToModel(ProductoEntity productoEntity) {
        if (productoEntity == null) {
            return null;
        }
        return new Producto(
                productoEntity.id(),
                CategoriaMapper.getInstance().fromEntityToModel(productoEntity.categoria()),
                PeluqueriaMapper.getInstance().fromEntityToModel(productoEntity.peluqueria()),
                productoEntity.nombre(),
                productoEntity.precio(),
                productoEntity.duracion());
    }

    public Producto fromDtoToModel(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }
        return new Producto(
                productoDto.id(),
                CategoriaMapper.getInstance().fromDtoToModel(productoDto.categoria()),
                PeluqueriaMapper.getInstance().fromDtoToModel(productoDto.peluqueria()),
                productoDto.nombre(),
                productoDto.precio(),
                productoDto.duracion());
    }

    public ProductoDto fromModelToDto(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoDto(
                producto.getId(),
                CategoriaMapper.getInstance().fromModelToDto(producto.getCategoria()),
                PeluqueriaMapper.getInstance().fromModelToDto(producto.getPeluqueria()),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDuracion());
    }
}
