package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Peluqueria;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
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

    public ProductoEntity fromModelToEntity(Producto producto) {
        if (producto == null) {
            return null;
        }

        Peluqueria peluqueria = producto.getPeluqueria();
        PeluqueriaEntity peluqueriaEntity = null;
        if (peluqueria != null) {
            peluqueriaEntity = new PeluqueriaEntity(
                    peluqueria.getId(),
                    UsuarioMapper.getInstance().fromModelToEntity(peluqueria.getUsuario()),
                    peluqueria.getMunicipio(),
                    peluqueria.getDireccion(),
                    peluqueria.getTelefono(),
                    null);
        }

        return new ProductoEntity(
                producto.getId(),
                CategoriaMapper.getInstance().fromModelToEntity(producto.getCategoria()),
                peluqueriaEntity,
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDuracion());
    }

    public Producto fromEntityToModel(ProductoEntity productoEntity) {
        if (productoEntity == null) {
            return null;
        }

        PeluqueriaEntity peluqueriaEntity = productoEntity.peluqueria();
        Peluqueria peluqueria = null;
        if (peluqueriaEntity != null) {
            peluqueria = new Peluqueria(
                    peluqueriaEntity.id(),
                    UsuarioMapper.getInstance().fromEntityToModel(peluqueriaEntity.usuario()),
                    peluqueriaEntity.municipio(),
                    peluqueriaEntity.direccion(),
                    peluqueriaEntity.telefono(),
                    null);
        }

        return new Producto(
                productoEntity.id(),
                CategoriaMapper.getInstance().fromEntityToModel(productoEntity.categoria()),
                peluqueria,
                productoEntity.nombre(),
                productoEntity.precio(),
                productoEntity.duracion());
    }

    public Producto fromDtoToModel(ProductoDto productoDto) {
        if (productoDto == null) {
            return null;
        }

        PeluqueriaDto peluqueriaDto = productoDto.peluqueria();
        Peluqueria peluqueria = null;
        if (peluqueriaDto != null) {
            peluqueria = new Peluqueria(
                    peluqueriaDto.id(),
                    UsuarioMapper.getInstance().fromDtoToModel(peluqueriaDto.usuario()),
                    peluqueriaDto.municipio(),
                    peluqueriaDto.direccion(),
                    peluqueriaDto.telefono(),
                    null);
        }

        return new Producto(
                productoDto.id(),
                CategoriaMapper.getInstance().fromDtoToModel(productoDto.categoria()),
                peluqueria,
                productoDto.nombre(),
                productoDto.precio(),
                productoDto.duracion());
    }

    public ProductoDto fromModelToDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        Peluqueria peluqueria = producto.getPeluqueria();
        PeluqueriaDto peluqueriaDto = null;
        if (peluqueria != null) {
            peluqueriaDto = new PeluqueriaDto(
                    peluqueria.getId(),
                    UsuarioMapper.getInstance().fromModelToDto(peluqueria.getUsuario()),
                    peluqueria.getMunicipio(),
                    peluqueria.getDireccion(),
                    peluqueria.getTelefono(),
                    null // lista de productos vacía
            );
        }

        return new ProductoDto(
                producto.getId(),
                CategoriaMapper.getInstance().fromModelToDto(producto.getCategoria()),
                peluqueriaDto,
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDuracion());
    }
}
