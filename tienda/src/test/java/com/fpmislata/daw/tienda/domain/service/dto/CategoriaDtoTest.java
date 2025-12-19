package com.fpmislata.daw.tienda.domain.service.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class CategoriaDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Crear categoría con nombre nulo debe producir una violación de validación")
    void categoriaNombreNuloDebeFallar() {
        // Arrange
        CategoriaDto categoria = new CategoriaDto(
                1L,
                null, // nombre nulo
                "Descripción de prueba");

        // Act
        Set<ConstraintViolation<CategoriaDto>> violations = validator.validate(categoria);

        // Assert
        assertFalse(violations.isEmpty(), "Debe haber violaciones de validación");
        assertTrue(
                violations.stream().anyMatch(v -> v.getMessage().equals("El nombre no puede ser nulo")),
                "Debe contener el mensaje de error esperado");
    }

}
