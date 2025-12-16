package com.fpmislata.daw.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpmislata.daw.tienda.controller.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.ProductoInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.ProductoUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoSummaryResponse;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;
import com.fpmislata.daw.tienda.enums.Rol;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

        private final ProductoService productoService;

        private final CategoriaService categoriaService;

        private final PeluqueriaService peluqueriaService;

        public ProductoController(ProductoService productoService, CategoriaService categoriaService,
                        PeluqueriaService peluqueriaService) {
                this.productoService = productoService;
                this.categoriaService = categoriaService;
                this.peluqueriaService = peluqueriaService;
        }

        @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
        @GetMapping
        public ResponseEntity<Page<ProductoSummaryResponse>> findAll(
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
                Page<ProductoDto> productoPage = productoService.findAll(page, size);

                List<ProductoSummaryResponse> productoResponses = productoPage.data().stream()
                                .map(ProductoMapper.getInstance()::fromDtoToSummary)
                                .toList();

                Page<ProductoSummaryResponse> responsePage = new Page<>(
                                productoResponses,
                                productoPage.pageNumber(),
                                productoPage.pageSize(),
                                productoPage.totalElements());

                return new ResponseEntity<>(responsePage, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
        @GetMapping("/{id}")
        public ResponseEntity<ProductoDetailResponse> findById(@PathVariable Long id) {
                ProductoDetailResponse productoResponse = ProductoMapper.getInstance()
                                .fromDtoToDetail(productoService.getById(id));

                return new ResponseEntity<>(productoResponse, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin })
        @PostMapping
        public ResponseEntity<ProductoDetailResponse> create(@RequestBody ProductoInsertRequest productoInsertRequest) {
                CategoriaDto categoriaDto = categoriaService.getById(productoInsertRequest.categoriaId());

                PeluqueriaDto peluqueriaDto = peluqueriaService.getById(productoInsertRequest.peluqueriaId());

                ProductoDto productoDto = ProductoMapper.getInstance()
                                .fromInsertToDto(productoInsertRequest, categoriaDto, peluqueriaDto);

                DtoValidator.validate(productoDto);

                ProductoDetailResponse productoResponse = ProductoMapper.getInstance()
                                .fromDtoToDetail(productoService.create(productoDto));

                return new ResponseEntity<>(productoResponse, HttpStatus.CREATED);
        }

        @RequireRole(roles = { Rol.Admin })
        @PutMapping("/{id}")
        public ResponseEntity<ProductoDetailResponse> update(
                        @PathVariable Long id,
                        @RequestBody ProductoUpdateRequest productoUpdateRequest) {
                ProductoDto baseDto = productoService.getById(id);

                ProductoDto productoDto = ProductoMapper.getInstance()
                                .fromUpdateToDto(productoUpdateRequest);

                productoDto = new ProductoDto(id, baseDto.categoria(), baseDto.peluqueria(), productoDto.nombre(),
                                productoDto.precio(), productoDto.duracion());

                DtoValidator.validate(productoDto);

                ProductoDetailResponse productoResponse = ProductoMapper.getInstance()
                                .fromDtoToDetail(productoService.update(productoDto));

                return new ResponseEntity<>(productoResponse, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                productoService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}