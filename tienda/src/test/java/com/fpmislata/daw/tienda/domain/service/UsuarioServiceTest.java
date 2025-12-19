package com.fpmislata.daw.tienda.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.UsuarioRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.service.impl.UsuarioServiceImpl;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    private UsuarioDto usuarioDtoValido() {
        return new UsuarioDto(
                1L,
                "test@example.com",
                "Usuario Test",
                "password",
                Rol.Cliente);
    }

    private UsuarioEntity usuarioEntityValido() {
        return new UsuarioEntity(
                1L,
                "test@example.com",
                "Usuario Test",
                "password",
                Rol.Cliente);
    }

    // ---------------------------------------------------------
    // IllegalArgumentException → findAll
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe lanzar IllegalArgumentException si page o size son < 1")
    void findAllParametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> usuarioService.findAll(0, 10));
        assertThrows(IllegalArgumentException.class, () -> usuarioService.findAll(1, 0));
        assertThrows(IllegalArgumentException.class, () -> usuarioService.findAll(-1, 5));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getById
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe lanzar ResourceNotFoundException si el usuario no existe")
    void getByIdNoEncontrado() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.getById(1L));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → update
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe lanzar ResourceNotFoundException si el usuario no existe")
    void updateUsuarioNoExiste() {
        UsuarioDto dto = usuarioDtoValido();

        when(usuarioRepository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.update(dto));
    }

    // ---------------------------------------------------------
    // BusinessException → create (email duplicado)
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe lanzar BusinessException si el email ya existe")
    void createEmailDuplicado() {
        UsuarioDto dto = usuarioDtoValido();

        when(usuarioRepository.findByEmail(dto.email()))
                .thenReturn(Optional.of(usuarioEntityValido()));

        assertThrows(BusinessException.class, () -> usuarioService.create(dto));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → delete
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe lanzar ResourceNotFoundException si el usuario no existe")
    void deleteUsuarioNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.delete(1L));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getByEmail
    // ---------------------------------------------------------

    @Test
    @DisplayName("getByEmail debe lanzar ResourceNotFoundException si no se encuentra el usuario")
    void getByEmailNoEncontrado() {
        when(usuarioRepository.findByEmail("noexiste@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> usuarioService.getByEmail("noexiste@example.com"));
    }

    // ---------------------------------------------------------
    // findAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findAll debe devolver una página válida")
    void findAllCorrecto() {
        Page<UsuarioEntity> page = new Page<>(
                List.of(usuarioEntityValido()),
                1,
                10,
                1);

        when(usuarioRepository.findAll(1, 10)).thenReturn(page);

        Page<UsuarioDto> result = usuarioService.findAll(1, 10);

        assertEquals(1, result.data().size());
        assertEquals("test@example.com", result.data().get(0).email());
        assertEquals(1, result.pageNumber());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
    }

    // ---------------------------------------------------------
    // getAll OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getAll debe devolver una lista de usuarios")
    void getAllCorrecto() {
        Page<UsuarioEntity> page = new Page<>(
                List.of(usuarioEntityValido()),
                1,
                10,
                1);

        when(usuarioRepository.findAll(1, 10)).thenReturn(page);

        List<UsuarioDto> result = usuarioService.getAll();

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).email());
    }

    // ---------------------------------------------------------
    // getById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getById debe devolver el usuario si existe")
    void getByIdCorrecto() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntityValido()));

        UsuarioDto result = usuarioService.getById(1L);

        assertEquals("test@example.com", result.email());
        assertEquals("Usuario Test", result.nombre());
    }

    // ---------------------------------------------------------
    // findById OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("findById debe devolver Optional con el usuario si existe")
    void findByIdCorrecto() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntityValido()));

        Optional<UsuarioDto> result = usuarioService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().email());
    }

    // ---------------------------------------------------------
    // create OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe guardar y devolver el usuario creado")
    void createCorrecto() {
        UsuarioDto dto = usuarioDtoValido();
        UsuarioEntity entity = usuarioEntityValido();

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(entity);

        UsuarioDto result = usuarioService.create(dto);

        assertEquals(dto.email(), result.email());
        verify(usuarioRepository).save(any());
    }

    // ---------------------------------------------------------
    // update OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("update debe actualizar y devolver el usuario")
    void updateCorrecto() {
        UsuarioDto dto = usuarioDtoValido();
        UsuarioEntity entity = usuarioEntityValido();

        when(usuarioRepository.findById(dto.id())).thenReturn(Optional.of(entity));
        when(usuarioRepository.save(any())).thenReturn(entity);

        UsuarioDto result = usuarioService.update(dto);

        assertEquals(dto.email(), result.email());
        verify(usuarioRepository).save(any());
    }

    // ---------------------------------------------------------
    // delete OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("delete debe eliminar el usuario si existe")
    void deleteCorrecto() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntityValido()));

        usuarioService.delete(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    // ---------------------------------------------------------
    // getByEmail OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getByEmail debe devolver el usuario si existe")
    void getByEmailCorrecto() {
        when(usuarioRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(usuarioEntityValido()));

        UsuarioDto result = usuarioService.getByEmail("test@example.com");

        assertEquals("test@example.com", result.email());
    }

}
