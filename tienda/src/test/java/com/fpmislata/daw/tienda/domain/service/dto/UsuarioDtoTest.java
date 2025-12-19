package com.fpmislata.daw.tienda.domain.service.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UsuarioDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Set<ConstraintViolation<UsuarioDto>> validar(UsuarioDto dto) {
        return validator.validate(dto);
    }

    // -------------------------------
    // TESTS @NotNull
    // -------------------------------

    @Test
    @DisplayName("Debe fallar si el email es nulo")
    void emailNoPuedeSerNulo() {
        UsuarioDto dto = new UsuarioDto(
                1L,
                null,
                "Juan",
                "pass",
                Rol.Admin);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El email no puede ser nulo")));
    }

    @Test
    @DisplayName("Debe fallar si el nombre es nulo")
    void nombreNoPuedeSerNulo() {
        UsuarioDto dto = new UsuarioDto(
                1L,
                "usuario@example.com",
                null,
                "pass",
                Rol.Admin);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre no puede ser nulo")));
    }

    @Test
    @DisplayName("Debe fallar si la contraseña es nula")
    void contrasenyaNoPuedeSerNula() {
        UsuarioDto dto = new UsuarioDto(
                1L,
                "usuario@example.com",
                "Juan",
                null,
                Rol.Admin);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La contraseña no puede ser nula")));
    }

    @Test
    @DisplayName("Debe fallar si el rol es nulo")
    void rolNoPuedeSerNulo() {
        UsuarioDto dto = new UsuarioDto(
                1L,
                "usuario@example.com",
                "Juan",
                "pass",
                null);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El rol no puede ser nulo")));
    }

    // -------------------------------
    // TEST formato del email
    // -------------------------------

    @Test
    @DisplayName("Debe fallar si el email tiene un formato inválido")
    void emailFormatoInvalido() {
        UsuarioDto dto = new UsuarioDto(
                1L,
                "email-invalido",
                "Juan",
                "pass",
                Rol.Cliente);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El email debe tener un formato válido")));
    }

    // -------------------------------
    // TEST tamaño máximo del nombre
    // -------------------------------

    @Test
    @DisplayName("Debe fallar si el nombre supera los 25 caracteres")
    void nombreDemasiadoLargo() {
        String nombreLargo = "NombreExcesivamenteLargo12345";

        UsuarioDto dto = new UsuarioDto(
                1L,
                "usuario@example.com",
                nombreLargo,
                "pass",
                Rol.Cliente);

        Set<ConstraintViolation<UsuarioDto>> violations = validar(dto);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre no puede superar los 25 carácteres")));
    }

    // -------------------------------
    // TEST rol válido (JsonCreator)
    // -------------------------------

    @Test
    @DisplayName("Debe lanzar BusinessException si el rol no es válido")
    void rolInvalidoDebeLanzarExcepcion() {
        assertThrows(BusinessException.class, () -> {
            Rol.fromString("Inexistente");
        });
    }
}