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

import com.fpmislata.daw.tienda.controller.mapper.CategoriaMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.CategoriaRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.CategoriaDetailResponse;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

        private final CategoriaService categoriaService;

        public CategoriaController(CategoriaService categoriaService) {
                this.categoriaService = categoriaService;
        }

        @GetMapping
        public ResponseEntity<Page<CategoriaDetailResponse>> findAll(
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
                Page<CategoriaDto> categoriaPage = categoriaService.findAll(page, size);

                List<CategoriaDetailResponse> categoriaResponses = categoriaPage.data().stream()
                                .map(CategoriaMapper.getInstance()::fromDtoToDetail)
                                .toList();

                Page<CategoriaDetailResponse> responsePage = new Page<>(
                                categoriaResponses,
                                categoriaPage.pageNumber(),
                                categoriaPage.pageSize(),
                                categoriaPage.totalElements());

                return new ResponseEntity<>(responsePage, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<CategoriaDetailResponse> findById(@PathVariable Long id) {
                CategoriaDetailResponse categoriaResponse = CategoriaMapper.getInstance()
                                .fromDtoToDetail(categoriaService.getById(id));

                return new ResponseEntity<>(categoriaResponse, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<CategoriaDetailResponse> create(@RequestBody CategoriaRequest categoriaRequest) {
                CategoriaDto categoriaDto = CategoriaMapper.getInstance()
                                .fromRequestToDto(categoriaRequest);

                DtoValidator.validate(categoriaDto);

                CategoriaDetailResponse categoriaResponse = CategoriaMapper.getInstance()
                                .fromDtoToDetail(categoriaService.create(categoriaDto));

                return new ResponseEntity<>(categoriaResponse, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<CategoriaDetailResponse> update(
                        @PathVariable Long id,
                        @RequestBody CategoriaRequest categoriaRequest) {
                CategoriaDto categoriaDto = CategoriaMapper.getInstance()
                                .fromRequestToDto(categoriaRequest);

                categoriaDto = new CategoriaDto(id, categoriaDto.nombre(), categoriaDto.descripcion());

                DtoValidator.validate(categoriaDto);

                CategoriaDetailResponse categoriaResponse = CategoriaMapper.getInstance()
                                .fromDtoToDetail(categoriaService.update(categoriaDto));

                return new ResponseEntity<>(categoriaResponse, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                categoriaService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
