package com.fpmislata.daw.tienda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpmislata.daw.tienda.controller.mapper.CarritoMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.CarritoRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.CarritoResponse;
import com.fpmislata.daw.tienda.domain.service.CarritoService;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;
import com.fpmislata.daw.tienda.enums.Rol;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @RequireRole(roles = { Rol.Admin, Rol.Cliente })
    @PostMapping("/calcular")
    public ResponseEntity<CarritoResponse> calcularCarrito(@RequestBody CarritoRequest carritoRequest) {
        CarritoInputDto inputDto = CarritoMapper.getInstance().fromRequestToInputDto(carritoRequest);

        DtoValidator.validate(inputDto);

        CarritoDto carritoCalculado = carritoService.calcularCarrito(inputDto);

        CarritoResponse carritoResponse = CarritoMapper.getInstance().fromDtoToResponse(carritoCalculado);

        return new ResponseEntity<>(carritoResponse, HttpStatus.OK);
    }
}
