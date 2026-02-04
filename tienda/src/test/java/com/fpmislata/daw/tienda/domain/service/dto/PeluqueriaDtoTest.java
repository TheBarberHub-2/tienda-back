package com.fpmislata.daw.tienda.domain.service.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fpmislata.daw.tienda.enums.Rol;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PeluqueriaDtoTest {

        private static Validator validator;

        @BeforeAll
        static void setupValidator() {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                validator = factory.getValidator();
        }

        private UsuarioDto crearUsuarioPeluqueria() {
                return new UsuarioDto(
                                1L,
                                "pelu@example.com",
                                "Peluquería Centro",
                                "password",
                                Rol.Peluqueria);
        }

        private UsuarioDto crearUsuarioNoPeluqueria() {
                return new UsuarioDto(
                                1L,
                                "cliente@example.com",
                                "Cliente",
                                "password",
                                Rol.Cliente);
        }

        private PeluqueriaDto crearPeluqueriaValida() {
                return new PeluqueriaDto(
                                1L,
                                crearUsuarioPeluqueria(),
                                "Valencia",
                                "Calle Falsa 123",
                                "+34961111222",
                                List.of(),
                                List.of());
        }

        private Set<ConstraintViolation<PeluqueriaDto>> validar(PeluqueriaDto dto) {
                return validator.validate(dto);
        }

        // -------------------------------
        // TESTS @NotNull
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si el usuario es nulo")
        void usuarioNoPuedeSerNulo() {
                PeluqueriaDto dto = new PeluqueriaDto(
                                1L,
                                null,
                                "Valencia",
                                "Calle Falsa 123",
                                "+34961111222",
                                List.of(),
                                List.of());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El usuario no puede ser nulo")));
        }

        @Test
        @DisplayName("Debe fallar si la dirección es nula")
        void direccionNoPuedeSerNula() {
                PeluqueriaDto dto = new PeluqueriaDto(
                                1L,
                                crearUsuarioPeluqueria(),
                                "Valencia",
                                null,
                                "+34961111222",
                                List.of(),
                                List.of());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("La dirección no puede ser nula")));
        }

        @Test
        @DisplayName("Debe fallar si el teléfono es nulo")
        void telefonoNoPuedeSerNulo() {
                PeluqueriaDto dto = new PeluqueriaDto(
                                1L,
                                crearUsuarioPeluqueria(),
                                "Valencia",
                                "Calle Falsa 123",
                                null,
                                List.of(),
                                List.of());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El teléfono no puede ser nulo")));
        }

        // -------------------------------
        // TEST formato del teléfono
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si el teléfono no cumple el patrón (menos de 9 dígitos)")
        void telefonoFormatoInvalidoCorto() {
                PeluqueriaDto dto = crearPeluqueriaValida();
                dto = new PeluqueriaDto(
                                dto.id(),
                                dto.usuario(),
                                dto.municipio(),
                                dto.direccion(),
                                "12345",
                                dto.productos(),
                                dto.horarios());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El teléfono debe tener entre 9 y 15 dígitos")));
        }

        @Test
        @DisplayName("Debe fallar si el teléfono no cumple el patrón (más de 15 dígitos)")
        void telefonoFormatoInvalidoLargo() {
                PeluqueriaDto dto = crearPeluqueriaValida();
                dto = new PeluqueriaDto(
                                dto.id(),
                                dto.usuario(),
                                dto.municipio(),
                                dto.direccion(),
                                "12345678901234567",
                                dto.productos(),
                                dto.horarios());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El teléfono debe tener entre 9 y 15 dígitos")));
        }

        // -------------------------------
        // TEST constraint personalizada: usuario debe tener rol Peluqueria
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si el usuario no tiene rol Peluqueria")
        void usuarioDebeTenerRolPeluqueria() {
                PeluqueriaDto dto = new PeluqueriaDto(
                                1L,
                                crearUsuarioNoPeluqueria(),
                                "Valencia",
                                "Calle Falsa 123",
                                "+34961111222",
                                List.of(),
                                List.of());

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El usuario debe tener rol Peluqueria")));
        }

        @Test
        @DisplayName("Debe validar correctamente cuando el usuario tiene rol Peluqueria")
        void usuarioConRolCorrectoDebeSerValido() {
                PeluqueriaDto dto = crearPeluqueriaValida();

                Set<ConstraintViolation<PeluqueriaDto>> violations = validar(dto);

                assertTrue(violations.isEmpty());
        }

}
