package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.service.impl.AuthServiceImpl;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UsuarioService usuarioService;
    private SesionService sesionService;
    private AuthService authService;

    @BeforeEach
    void setup() {
        usuarioService = mock(UsuarioService.class);
        sesionService = mock(SesionService.class);
        authService = new AuthServiceImpl(usuarioService, sesionService);
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    private UsuarioDto usuarioValido() {
        return new UsuarioDto(
                1L,
                "user@example.com",
                "Usuario",
                "password123",
                Rol.Cliente);
    }

    private SesionDto sesionValida() {
        return new SesionDto(
                1L,
                usuarioValido(),
                "TOKEN123",
                LocalDateTime.now().plusHours(1));
    }

    // ---------------------------------------------------------
    // BusinessException → logIn (contraseña incorrecta)
    // ---------------------------------------------------------

    @Test
    @DisplayName("logIn debe lanzar BusinessException si la contraseña es incorrecta")
    void loginContrasenyaIncorrecta() {
        UsuarioDto usuario = usuarioValido();

        when(usuarioService.getByEmail("user@example.com")).thenReturn(usuario);

        assertThrows(BusinessException.class,
                () -> authService.logIn("user@example.com", "incorrecta"));
    }

    // ---------------------------------------------------------
    // logIn OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("logIn debe devolver un token y crear una sesión")
    void loginCorrecto() {
        UsuarioDto usuario = usuarioValido();

        when(usuarioService.getByEmail("user@example.com")).thenReturn(usuario);

        String token = authService.logIn("user@example.com", "password123");

        assertNotNull(token);
        verify(sesionService).create(any());
    }

    // ---------------------------------------------------------
    // logOut OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("logOut debe eliminar la sesión por token")
    void logoutCorrecto() {
        authService.logOut("TOKEN123");

        verify(sesionService).deleteByToken("TOKEN123");
    }

    // ---------------------------------------------------------
    // getByToken OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getByToken debe devolver el usuario asociado al token")
    void getByTokenCorrecto() {
        when(sesionService.getByToken("TOKEN123")).thenReturn(sesionValida());

        UsuarioDto result = authService.getByToken("TOKEN123");

        assertEquals("user@example.com", result.email());
    }

    // ---------------------------------------------------------
    // getRolByToken OK
    // ---------------------------------------------------------

    @Test
    @DisplayName("getRolByToken debe devolver el rol del usuario asociado al token")
    void getRolByTokenCorrecto() {
        when(sesionService.getByToken("TOKEN123")).thenReturn(sesionValida());

        Rol rol = authService.getRolByToken("TOKEN123");

        assertEquals(Rol.Cliente, rol);
    }
}