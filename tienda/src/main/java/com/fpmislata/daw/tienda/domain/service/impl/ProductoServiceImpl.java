package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.ReservaService;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ReservaService reservaService;

    public ProductoServiceImpl(ProductoRepository productoRepository, ReservaService reservaService) {
        this.productoRepository = productoRepository;
        this.reservaService = reservaService;
    }

    @Override
    public Page<ProductoDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<ProductoEntity> productoPage = productoRepository.findAll(page, size);

        List<ProductoDto> productoDtos = productoPage.data().stream()
                .map(ProductoMapper.getInstance()::fromEntityToModel)
                .map(ProductoMapper.getInstance()::fromModelToDto)
                .toList();

        return new Page<>(productoDtos, productoPage.pageNumber(), productoPage.pageSize(),
                productoPage.totalElements());
    }

    @Override
    public ProductoDto getById(long id) {
        return productoRepository.findById(id).map(ProductoMapper.getInstance()::fromEntityToModel)
                .map(ProductoMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto with id " + id + " not found"));
    }

    @Override
    public Optional<ProductoDto> findById(long id) {
        return productoRepository.findById(id).map(ProductoMapper.getInstance()::fromEntityToModel)
                .map(ProductoMapper.getInstance()::fromModelToDto);
    }

    @Override
    public ProductoDto create(ProductoDto productoDto) {
        ProductoEntity productoEntity = ProductoMapper.getInstance().fromModelToEntity(
                ProductoMapper.getInstance().fromDtoToModel(productoDto));

        return ProductoMapper.getInstance().fromModelToDto(
                ProductoMapper.getInstance().fromEntityToModel(productoRepository.save(productoEntity)));
    }

    @Override
    public ProductoDto update(ProductoDto productoDto) {
        productoRepository.findById(productoDto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto with id " + productoDto.id() + " not found"));

        ProductoEntity productoEntity = ProductoMapper.getInstance().fromModelToEntity(
                ProductoMapper.getInstance().fromDtoToModel(productoDto));

        return ProductoMapper.getInstance().fromModelToDto(
                ProductoMapper.getInstance().fromEntityToModel(productoRepository.save(productoEntity)));
    }

    @Override
    public void delete(long id) {
        ProductoDto producto = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto with id " + id + " not found"));

        List<ReservaDto> reservas = reservaService.listarReservasPeluqueria(producto.peluqueria().id())
                .stream()
                .filter(r -> r.productos().stream()
                        .anyMatch(rp -> rp.producto().id() == producto.id()))
                .toList();

        for (ReservaDto reservaDto : reservas) {
            reservaService.cancelarReservaPorPeluqueria(reservaDto.id(), producto.peluqueria().id());
        }

        productoRepository.deleteById(id);
    }

    @Override
    public List<ProductoDto> findByIds(List<Long> ids) {
        List<ProductoDto> dtos = productoRepository.findByIds(ids).stream()
                .map(ProductoMapper.getInstance()::fromEntityToModel)
                .map(ProductoMapper.getInstance()::fromModelToDto)
                .toList();

        return dtos;
    }
}
