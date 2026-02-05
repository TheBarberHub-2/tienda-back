package com.fpmislata.daw.tienda.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.model.Carrito;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;

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

    public Carrito fromDtoToModel(CarritoDto carritoDto) {
        if (carritoDto == null) {
            return null;
        }
        List<Producto> productos = new ArrayList<>();
        if (carritoDto.productos() != null && !carritoDto.productos().isEmpty()) {
            productos = carritoDto.productos().stream()
                    .map(ProductoMapper.getInstance()::fromDtoToModel)
                    .toList();
        }
        return new Carrito(
                carritoDto.peluqueriaId(),
                productos,
                carritoDto.duracionTotal(),
                carritoDto.precioTotal());
    }

    public CarritoDto fromModelToDto(Carrito carrito) {
        if (carrito == null) {
            return null;
        }
        List<ProductoDto> productos = new ArrayList<>();
        if (carrito.getProductos() != null && !carrito.getProductos().isEmpty()) {
            productos = carrito.getProductos().stream()
                    .map(ProductoMapper.getInstance()::fromModelToDto)
                    .toList();
        }
        return new CarritoDto(
                carrito.getPeluqueriaId(),
                productos,
                carrito.getDuracionTotal(),
                carrito.getPrecioTotal());
    }
}
