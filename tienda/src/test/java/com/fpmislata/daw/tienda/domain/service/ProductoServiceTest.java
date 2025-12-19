package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;
import com.fpmislata.daw.tienda.domain.service.impl.ProductoServiceImpl;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setup() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoServiceImpl(productoRepository);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private ProductoEntity entity() {
        return new ProductoEntity(
                1L,
                null, // categoria
                null, // peluqueria
                "Corte Básico",
                new BigDecimal("10.00"),
                30);
    }

    private ProductoDto dto() {
        return new ProductoDto(
                1L,
                null,
                null,
                "Corte Básico",
                new BigDecimal("10.00"),
                30);
    }

    // ---------------------------------------------------------
    // IllegalArgumentException → findAll
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe lanzar IllegalArgumentException si page o size son < 1")
    void findAllParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> productoService.findAll(0, 10));
        assertThrows(IllegalArgumentException.class, () -> productoService.findAll(1, 0));
        assertThrows(IllegalArgumentException.class, () -> productoService.findAll(-1, 5));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getById
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe lanzar ResourceNotFoundException si no existe")
    void getByIdNoEncontrado() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.getById(1L));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → update
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe lanzar ResourceNotFoundException si no existe")
    void updateNoEncontrado() {
        ProductoDto dto = dto();

        when(productoRepository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.update(dto));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → delete
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe lanzar ResourceNotFoundException si no existe")
    void deleteNoEncontrado() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productoService.delete(1L));
    }

    // ---------------------------------------------------------
    // findAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe devolver una página válida")
    void findAllCorrecto() {
        Page<ProductoEntity> page = new Page<>(
                List.of(entity()),
                1,
                10,
                1);

        when(productoRepository.findAll(1, 10)).thenReturn(page);

        Page<ProductoDto> result = productoService.findAll(1, 10);

        assertEquals(1, result.data().size());
        assertEquals("Corte Básico", result.data().get(0).nombre());
    }

    // ---------------------------------------------------------
    // getById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe devolver el producto si existe")
    void getByIdCorrecto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(entity()));

        ProductoDto result = productoService.getById(1L);

        assertEquals("Corte Básico", result.nombre());
    }

    // ---------------------------------------------------------
    // findById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findById debe devolver Optional con el producto si existe")
    void findByIdCorrecto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(entity()));

        Optional<ProductoDto> result = productoService.findById(1L);

        assertTrue(result.isPresent());
    }

    // ---------------------------------------------------------
    // create OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe guardar y devolver el producto creado")
    void createCorrecto() {
        ProductoDto dto = dto();
        ProductoEntity entity = entity();

        when(productoRepository.save(any())).thenReturn(entity);

        ProductoDto result = productoService.create(dto);

        assertEquals(dto.nombre(), result.nombre());
        verify(productoRepository).save(any());
    }

    // ---------------------------------------------------------
    // update OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe actualizar y devolver el producto")
    void updateCorrecto() {
        ProductoDto dto = dto();
        ProductoEntity entity = entity();

        when(productoRepository.findById(dto.id())).thenReturn(Optional.of(entity));
        when(productoRepository.save(any())).thenReturn(entity);

        ProductoDto result = productoService.update(dto);

        assertEquals(dto.nombre(), result.nombre());
        verify(productoRepository).save(any());
    }

    // ---------------------------------------------------------
    // delete OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe eliminar el producto si existe")
    void deleteCorrecto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(entity()));

        productoService.delete(1L);

        verify(productoRepository).deleteById(1L);
    }
}