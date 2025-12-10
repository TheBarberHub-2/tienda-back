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

import com.fpmislata.daw.tienda.controller.mapper.UsuarioMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.UsuarioInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.UsuarioUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.UsuarioDetailResponse;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

        private final UsuarioService usuarioService;

        public UsuarioController(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
        }

        @GetMapping
        public ResponseEntity<Page<UsuarioDetailResponse>> findAll(
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
                Page<UsuarioDto> usuarioPage = usuarioService.findAll(page, size);

                List<UsuarioDetailResponse> usuarioResponses = usuarioPage.data().stream()
                                .map(UsuarioMapper.getInstance()::fromDtoToDetail)
                                .toList();

                Page<UsuarioDetailResponse> responsePage = new Page<>(
                                usuarioResponses,
                                usuarioPage.pageNumber(),
                                usuarioPage.pageSize(),
                                usuarioPage.totalElements());

                return new ResponseEntity<>(responsePage, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UsuarioDetailResponse> findById(@PathVariable Long id) {
                UsuarioDetailResponse usuarioResponse = UsuarioMapper.getInstance()
                                .fromDtoToDetail(usuarioService.getById(id));

                return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
        }

        @PostMapping
        public ResponseEntity<UsuarioDetailResponse> create(
                        @RequestBody UsuarioInsertRequest usuarioInsertRequest) {
                UsuarioDto usuarioDto = UsuarioMapper.getInstance()
                                .fromInsertToDto(usuarioInsertRequest);

                DtoValidator.validate(usuarioDto);

                UsuarioDetailResponse usuarioResponse = UsuarioMapper.getInstance()
                                .fromDtoToDetail(usuarioService.create(usuarioDto));

                return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UsuarioDetailResponse> update(
                        @PathVariable Long id,
                        @RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
                UsuarioDto baseDto = usuarioService.getById(id);

                UsuarioDto usuarioDto = UsuarioMapper.getInstance()
                                .fromUpdateToDto(usuarioUpdateRequest);

                usuarioDto = new UsuarioDto(id, usuarioDto.email(), usuarioDto.nombre(), baseDto.rol());

                DtoValidator.validate(usuarioDto);

                UsuarioDetailResponse usuarioResponse = UsuarioMapper.getInstance()
                                .fromDtoToDetail(usuarioService.update(usuarioDto));

                return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                usuarioService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
