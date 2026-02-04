package com.fpmislata.daw.tienda.domain.service.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProductoDtoTest {

        private static Validator validator;

        @BeforeAll
        static void setupValidator() {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                validator = factory.getValidator();
        }

        // -------------------------------
        // Helpers para objetos válidos
        // -------------------------------

        private CategoriaDto categoriaValida() {
                return new CategoriaDto(1L, "Corte", "Servicios de corte");
        }

        private UsuarioDto usuarioPeluqueria() {
                return new UsuarioDto(
                                1L,
                                "pelu@example.com",
                                "Pelu Centro",
                                "pass",
                                com.fpmislata.daw.tienda.enums.Rol.Peluqueria);
        }

        private PeluqueriaDto peluqueriaValida() {
                return new PeluqueriaDto(
                                1L,
                                usuarioPeluqueria(),
                                "Valencia",
                                "Calle Falsa 123",
                                "+34961111222",
                                List.of(),
                                null);
        }

        private ProductoDto productoValido() {
                return new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte Básico",
                                new BigDecimal("10.00"),
                                30);
        }

        private Set<ConstraintViolation<ProductoDto>> validar(ProductoDto dto) {
                return validator.validate(dto);
        }

        // -------------------------------
        // TESTS @NotNull
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si la categoría es nula")
        void categoriaNoPuedeSerNula() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                null,
                                peluqueriaValida(),
                                "Corte",
                                new BigDecimal("10.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("La categoría no puede ser nula")));
        }

        @Test
        @DisplayName("Debe fallar si la peluquería es nula")
        void peluqueriaNoPuedeSerNula() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                null,
                                "Corte",
                                new BigDecimal("10.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("La peluquería no puede ser nula")));
        }

        @Test
        @DisplayName("Debe fallar si el nombre es nulo")
        void nombreNoPuedeSerNulo() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                null,
                                new BigDecimal("10.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El nombre no puede ser nulo")));
        }

        @Test
        @DisplayName("Debe fallar si el precio es nulo")
        void precioNoPuedeSerNulo() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte",
                                null,
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El precio no puede ser nulo")));
        }

        // -------------------------------
        // TEST nombre > 20 caracteres
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si el nombre supera los 20 caracteres")
        void nombreDemasiadoLargo() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "NombreDeProductoMuyLargo123",
                                new BigDecimal("10.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El nombre debe tener menos de 20 caracteres")));
        }

        // -------------------------------
        // TEST precio = 0 y negativo
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si el precio es 0")
        void precioCeroDebeFallar() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte",
                                new BigDecimal("0.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El precio debe ser mayor que 0")));
        }

        @Test
        @DisplayName("Debe fallar si el precio es negativo")
        void precioNegativoDebeFallar() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte",
                                new BigDecimal("-5.00"),
                                30);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("El precio debe ser mayor que 0")));
        }

        // -------------------------------
        // TEST duración = 0 y negativa
        // -------------------------------

        @Test
        @DisplayName("Debe fallar si la duración es 0")
        void duracionCeroDebeFallar() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte",
                                new BigDecimal("10.00"),
                                0);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("La duración debe ser mayor que 0")));
        }

        @Test
        @DisplayName("Debe fallar si la duración es negativa")
        void duracionNegativaDebeFallar() {
                ProductoDto dto = new ProductoDto(
                                1L,
                                categoriaValida(),
                                peluqueriaValida(),
                                "Corte",
                                new BigDecimal("10.00"),
                                -10);

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.stream()
                                .anyMatch(v -> v.getMessage().equals("La duración debe ser mayor que 0")));
        }

        // -------------------------------
        // TEST caso válido
        // -------------------------------

        @Test
        @DisplayName("Debe ser válido cuando todos los campos son correctos")
        void productoValidoDebePasarValidacion() {
                ProductoDto dto = productoValido();

                Set<ConstraintViolation<ProductoDto>> violations = validar(dto);

                assertTrue(violations.isEmpty());
        }

}
