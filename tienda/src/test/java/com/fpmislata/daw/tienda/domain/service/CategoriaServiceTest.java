package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.CategoriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
import com.fpmislata.daw.tienda.domain.service.impl.CategoriaServiceImpl;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    private CategoriaRepository categoriaRepository;
    private CategoriaService categoriaService;

    @BeforeEach
    void setup() {
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaService = new CategoriaServiceImpl(categoriaRepository);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private CategoriaEntity entity() {
        return new CategoriaEntity(
                1L,
                "Corte",
                "Servicios de corte");
    }

    private CategoriaDto dto() {
        return new CategoriaDto(
                1L,
                "Corte",
                "Servicios de corte");
    }

    // ---------------------------------------------------------
    // IllegalArgumentException → findAll
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe lanzar IllegalArgumentException si page o size son < 1")
    void findAllParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> categoriaService.findAll(0, 10));
        assertThrows(IllegalArgumentException.class, () -> categoriaService.findAll(1, 0));
        assertThrows(IllegalArgumentException.class, () -> categoriaService.findAll(-1, 5));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getById
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe lanzar ResourceNotFoundException si no existe")
    void getByIdNoEncontrado() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.getById(1L));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → update
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe lanzar ResourceNotFoundException si no existe")
    void updateNoEncontrado() {
        CategoriaDto dto = dto();

        when(categoriaRepository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.update(dto));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → delete
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe lanzar ResourceNotFoundException si no existe")
    void deleteNoEncontrado() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.delete(1L));
    }

    // ---------------------------------------------------------
    // findAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe devolver una página válida")
    void findAllCorrecto() {
        Page<CategoriaEntity> page = new Page<>(
                List.of(entity()),
                1,
                10,
                1);

        when(categoriaRepository.findAll(1, 10)).thenReturn(page);

        Page<CategoriaDto> result = categoriaService.findAll(1, 10);

        assertEquals(1, result.data().size());
        assertEquals("Corte", result.data().get(0).nombre());
    }

    // ---------------------------------------------------------
    // getById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe devolver la categoría si existe")
    void getByIdCorrecto() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(entity()));

        CategoriaDto result = categoriaService.getById(1L);

        assertEquals("Corte", result.nombre());
    }

    // ---------------------------------------------------------
    // findById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findById debe devolver Optional con la categoría si existe")
    void findByIdCorrecto() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(entity()));

        Optional<CategoriaDto> result = categoriaService.findById(1L);

        assertTrue(result.isPresent());
    }

    // ---------------------------------------------------------
    // create OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe guardar y devolver la categoría creada")
    void createCorrecto() {
        CategoriaDto dto = dto();
        CategoriaEntity entity = entity();

        when(categoriaRepository.save(any())).thenReturn(entity);

        CategoriaDto result = categoriaService.create(dto);

        assertEquals(dto.nombre(), result.nombre());
        verify(categoriaRepository).save(any());
    }

    // ---------------------------------------------------------
    // update OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe actualizar y devolver la categoría")
    void updateCorrecto() {
        CategoriaDto dto = dto();
        CategoriaEntity entity = entity();

        when(categoriaRepository.findById(dto.id())).thenReturn(Optional.of(entity));
        when(categoriaRepository.save(any())).thenReturn(entity);

        CategoriaDto result = categoriaService.update(dto);

        assertEquals(dto.nombre(), result.nombre());
        verify(categoriaRepository).save(any());
    }

    // ---------------------------------------------------------
    // delete OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe eliminar la categoría si existe")
    void deleteCorrecto() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(entity()));

        categoriaService.delete(1L);

        verify(categoriaRepository).deleteById(1L);
    }
}