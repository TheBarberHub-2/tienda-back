package com.fpmislata.daw.tienda.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fpmislata.daw.tienda.domain.mapper.CarritoMapper;
import com.fpmislata.daw.tienda.domain.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.domain.model.Carrito;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.service.CarritoService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class CarritoServiceImpl implements CarritoService {

    private final ProductoRepository productoRepository;
    private final PeluqueriaService peluqueriaService;

    public CarritoServiceImpl(ProductoRepository productoRepository, PeluqueriaService peluqueriaService) {
        this.productoRepository = productoRepository;
        this.peluqueriaService = peluqueriaService;
    }

    @Override
    public CarritoDto calcularCarrito(CarritoInputDto dto) {
        PeluqueriaDto peluqueriaDto = peluqueriaService.getById(dto.peluqueriaId());

        List<Long> productosIds = dto.productoIds();

        List<ProductoEntity> productoEntities = productoRepository.findByIds(productosIds);

        if (productoEntities.size() != productosIds.size()) {
            throw new BusinessException("Algunos productos no existen");
        }

        boolean allSamePeluqueria = productoEntities.stream()
                .allMatch(p -> p.peluqueria().id().equals(peluqueriaDto.id()));

        if (!allSamePeluqueria) {
            throw new BusinessException("Todos los productos deben pertenecer a la misma peluquería");
        }

        validarCategorias(productoEntities);

        List<Producto> productos = productoEntities.stream()
                .map(ProductoMapper.getInstance()::fromEntityToModel)
                .toList();

        int duracionTotal = productos.stream()
                .mapToInt(Producto::getDuracion)
                .sum();

        BigDecimal precioTotal = productos.stream()
                .map(Producto::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Carrito carrito = new Carrito(
                dto.peluqueriaId(),
                productos,
                duracionTotal,
                precioTotal);

        return CarritoMapper.getInstance().fromModelToDto(carrito);
    }

    private void validarCategorias(List<ProductoEntity> productos) {
        Map<Long, Long> categoriaCount = productos.stream()
                .collect(Collectors.groupingBy(p -> p.categoria().id(), Collectors.counting()));

        if (categoriaCount.getOrDefault(1L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de pelo'");
        }

        if (categoriaCount.getOrDefault(2L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de barba'");
        }

        if (categoriaCount.getOrDefault(4L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Afeitado clásico'");
        }
    }
}
