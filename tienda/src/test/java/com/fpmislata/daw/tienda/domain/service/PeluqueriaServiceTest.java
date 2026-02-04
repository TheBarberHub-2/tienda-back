package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.service.impl.PeluqueriaServiceImpl;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PeluqueriaServiceTest {

    private PeluqueriaRepository peluqueriaRepository;
    private UsuarioService usuarioService;
    private PeluqueriaService peluqueriaService;

    @BeforeEach
    void setup() {
        peluqueriaRepository = mock(PeluqueriaRepository.class);
        usuarioService = mock(UsuarioService.class);
        peluqueriaService = new PeluqueriaServiceImpl(peluqueriaRepository, usuarioService);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private UsuarioDto usuarioPeluqueriaDto() {
        return new UsuarioDto(
                1L,
                "pelu@example.com",
                "Pelu Centro",
                "pass",
                Rol.Peluqueria);
    }

    private UsuarioEntity usuarioPeluqueriaEntity() {
        return new UsuarioEntity(
                1L,
                "pelu@example.com",
                "Pelu Centro",
                "pass",
                Rol.Peluqueria);
    }

    private PeluqueriaEntity entity() {
        return new PeluqueriaEntity(
                1L,
                usuarioPeluqueriaEntity(),
                "Valencia",
                "Calle Falsa 123",
                "+34961111222",
                null,
                null);
    }

    private PeluqueriaDto dto() {
        return new PeluqueriaDto(
                1L,
                usuarioPeluqueriaDto(),
                "Valencia",
                "Calle Falsa 123",
                "+34961111222",
                null,
                null);
    }

    // ---------------------------------------------------------
    // IllegalArgumentException → findAll
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe lanzar IllegalArgumentException si page o size son < 1")
    void findAllParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> peluqueriaService.findAll(0, 10));
        assertThrows(IllegalArgumentException.class, () -> peluqueriaService.findAll(1, 0));
        assertThrows(IllegalArgumentException.class, () -> peluqueriaService.findAll(-1, 5));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getById
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe lanzar ResourceNotFoundException si no existe")
    void getByIdNoEncontrado() {
        when(peluqueriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> peluqueriaService.getById(1L));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → update
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe lanzar ResourceNotFoundException si no existe")
    void updateNoEncontrado() {
        PeluqueriaDto dto = dto();

        when(peluqueriaRepository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> peluqueriaService.update(dto));
    }

    // ---------------------------------------------------------
    // BusinessException → create (usuario ya tiene peluquería)
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe lanzar BusinessException si el usuario ya tiene peluquería")
    void createUsuarioDuplicado() {
        PeluqueriaDto dto = dto();

        when(peluqueriaRepository.findByUsuario(dto.usuario().id()))
                .thenReturn(Optional.of(entity()));

        assertThrows(BusinessException.class, () -> peluqueriaService.create(dto));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → delete
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe lanzar ResourceNotFoundException si no existe")
    void deleteNoEncontrado() {
        when(peluqueriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> peluqueriaService.delete(1L));
    }

    // ---------------------------------------------------------
    // findAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe devolver una página válida")
    void findAllCorrecto() {
        Page<PeluqueriaEntity> page = new Page<>(
                List.of(entity()),
                1,
                10,
                1);

        when(peluqueriaRepository.findAll(1, 10)).thenReturn(page);

        Page<PeluqueriaDto> result = peluqueriaService.findAll(1, 10);

        assertEquals(1, result.data().size());
        assertEquals("Valencia", result.data().get(0).municipio());
    }

    // ---------------------------------------------------------
    // getAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getAll debe devolver lista de peluquerías")
    void getAllCorrecto() {
        Page<PeluqueriaEntity> page = new Page<>(
                List.of(entity()),
                1,
                10,
                1);

        when(peluqueriaRepository.findAll(1, 10)).thenReturn(page);

        List<PeluqueriaDto> result = peluqueriaService.getAll();

        assertEquals(1, result.size());
        assertEquals("Valencia", result.get(0).municipio());
    }

    // ---------------------------------------------------------
    // getById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe devolver la peluquería si existe")
    void getByIdCorrecto() {
        when(peluqueriaRepository.findById(1L)).thenReturn(Optional.of(entity()));

        PeluqueriaDto result = peluqueriaService.getById(1L);

        assertEquals("Valencia", result.municipio());
    }

    // ---------------------------------------------------------
    // findById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findById debe devolver Optional con la peluquería si existe")
    void findByIdCorrecto() {
        when(peluqueriaRepository.findById(1L)).thenReturn(Optional.of(entity()));

        Optional<PeluqueriaDto> result = peluqueriaService.findById(1L);

        assertTrue(result.isPresent());
    }

    // ---------------------------------------------------------
    // create OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe guardar y devolver la peluquería creada")
    void createCorrecto() {
        PeluqueriaDto dto = dto();
        PeluqueriaEntity entity = entity();

        when(peluqueriaRepository.findByUsuario(dto.usuario().id())).thenReturn(Optional.empty());
        when(peluqueriaRepository.save(any())).thenReturn(entity);

        PeluqueriaDto result = peluqueriaService.create(dto);

        assertEquals(dto.usuario().id(), result.usuario().id());
        verify(peluqueriaRepository).save(any());
    }

    // ---------------------------------------------------------
    // update OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe actualizar y devolver la peluquería")
    void updateCorrecto() {
        PeluqueriaDto dto = dto();
        PeluqueriaEntity entity = entity();

        when(peluqueriaRepository.findById(dto.id())).thenReturn(Optional.of(entity));
        when(peluqueriaRepository.save(any())).thenReturn(entity);

        PeluqueriaDto result = peluqueriaService.update(dto);

        assertEquals(dto.usuario().id(), result.usuario().id());
        verify(peluqueriaRepository).save(any());
    }

    // ---------------------------------------------------------
    // delete OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe eliminar la peluquería y su usuario asociado")
    void deleteCorrecto() {
        PeluqueriaDto dto = dto();
        PeluqueriaEntity entity = entity();

        // 1. El servicio llama internamente a findById(), que depende del repo
        when(peluqueriaRepository.findById(1L)).thenReturn(Optional.of(entity));

        // 2. El mapper convertirá entity → model → dto automáticamente
        // No mockeamos peluqueriaService.findById()

        peluqueriaService.delete(1L);

        // 3. Verificamos que se elimina la peluquería
        verify(peluqueriaRepository).deleteById(1L);

        // 4. Y que se elimina el usuario asociado
        verify(usuarioService).delete(dto.usuario().id());
    }

    // ---------------------------------------------------------
    // findAvailablePeluquerias OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAvailablePeluquerias debe devolver peluquerías disponibles")
    void findAvailableCorrecto() {
        UsuarioDto u1 = usuarioPeluqueriaDto();
        UsuarioDto u2 = new UsuarioDto(2L, "otra@example.com", "Otra", "pass", Rol.Peluqueria);

        when(usuarioService.getAll()).thenReturn(List.of(u1, u2));
        when(peluqueriaRepository.findAll(1, 10))
                .thenReturn(new Page<>(List.of(entity()), 1, 10, 1));

        Optional<List<UsuarioDto>> result = peluqueriaService.findAvailablePeluquerias();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(2L, result.get().get(0).id());
    }

    @Test
    @DisplayName("findAvailablePeluquerias debe devolver Optional.empty si no hay disponibles")
    void findAvailableVacio() {
        when(usuarioService.getAll()).thenReturn(List.of(usuarioPeluqueriaDto()));
        when(peluqueriaRepository.findAll(1, 10))
                .thenReturn(new Page<>(List.of(entity()), 1, 10, 1));

        Optional<List<UsuarioDto>> result = peluqueriaService.findAvailablePeluquerias();

        assertTrue(result.isEmpty());
    }
}