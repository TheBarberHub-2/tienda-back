package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.repository.SesionRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.service.impl.SesionServiceImpl;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SesionServiceTest {

    private SesionRepository sesionRepository;
    private SesionService sesionService;

    @BeforeEach
    void setup() {
        sesionRepository = mock(SesionRepository.class);
        sesionService = new SesionServiceImpl(sesionRepository);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private UsuarioDto usuarioDto() {
        return new UsuarioDto(
                1L,
                "user@example.com",
                "Usuario",
                "pass",
                Rol.Cliente);
    }

    private UsuarioEntity usuarioEntity() {
        return new UsuarioEntity(
                1L,
                "user@example.com",
                "Usuario",
                "pass",
                Rol.Cliente);
    }

    private SesionEntity entity() {
        return new SesionEntity(
                1L,
                usuarioEntity(),
                "TOKEN123",
                LocalDateTime.now().plusHours(1));
    }

    private SesionDto dto() {
        return new SesionDto(
                1L,
                usuarioDto(),
                "TOKEN123",
                LocalDateTime.now().plusHours(1));
    }

    // ---------------------------------------------------------
    // ResourceNotFoundException → getByToken
    // ---------------------------------------------------------

    @Test
    @DisplayName("getByToken debe lanzar ResourceNotFoundException si no existe la sesión")
    void getByTokenNoEncontrado() {
        when(sesionRepository.findByToken("INVALID")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sesionService.getByToken("INVALID"));
    }

    // ---------------------------------------------------------
    // getByToken OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getByToken debe devolver la sesión si existe")
    void getByTokenCorrecto() {
        when(sesionRepository.findByToken("TOKEN123")).thenReturn(Optional.of(entity()));

        SesionDto result = sesionService.getByToken("TOKEN123");

        assertEquals("TOKEN123", result.token());
        assertEquals("user@example.com", result.usuario().email());
    }

    // ---------------------------------------------------------
    // create OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("create debe guardar y devolver la sesión creada")
    void createCorrecto() {
        SesionDto dto = dto();
        SesionEntity entity = entity();

        when(sesionRepository.save(any())).thenReturn(entity);

        SesionDto result = sesionService.create(dto);

        assertEquals(dto.token(), result.token());
        verify(sesionRepository).save(any());
    }

    // ---------------------------------------------------------
    // deleteByToken OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("deleteByToken debe eliminar la sesión por token")
    void deleteCorrecto() {
        sesionService.deleteByToken("TOKEN123");

        verify(sesionRepository).deleteByToken("TOKEN123");
    }
}