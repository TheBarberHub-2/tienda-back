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

import com.fpmislata.daw.tienda.controller.mapper.PeluqueriaMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaSummaryResponse;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;
import com.fpmislata.daw.tienda.enums.Rol;

@RestController
@RequestMapping("/api/peluquerias")
public class PeluqueriaController {

        private final PeluqueriaService peluqueriaService;

        private final UsuarioService usuarioService;

        public PeluqueriaController(PeluqueriaService peluqueriaService, UsuarioService usuarioService) {
                this.peluqueriaService = peluqueriaService;
                this.usuarioService = usuarioService;
        }

        @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
        @GetMapping
        public ResponseEntity<Page<PeluqueriaSummaryResponse>> findAll(
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "100") int size) {
                Page<PeluqueriaDto> peluqueriaPage = peluqueriaService.findAll(page, size);

                List<PeluqueriaSummaryResponse> peluqueriaResponses = peluqueriaPage.data().stream()
                                .map(PeluqueriaMapper.getInstance()::fromDtoToSummary)
                                .toList();

                Page<PeluqueriaSummaryResponse> responsePage = new Page<>(
                                peluqueriaResponses,
                                peluqueriaPage.pageNumber(),
                                peluqueriaPage.pageSize(),
                                peluqueriaPage.totalElements());

                return new ResponseEntity<>(responsePage, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
        @GetMapping("/{id}")
        public ResponseEntity<PeluqueriaDetailResponse> findById(@PathVariable Long id) {
                PeluqueriaDetailResponse peluqueriaResponse = PeluqueriaMapper.getInstance()
                                .fromDtoToDetail(peluqueriaService.getById(id));

                return new ResponseEntity<>(peluqueriaResponse, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin })
        @PostMapping
        public ResponseEntity<PeluqueriaDetailResponse> create(
                        @RequestBody PeluqueriaInsertRequest peluqueriaInsertRequest) {

                UsuarioDto usuarioDto = usuarioService.getById(peluqueriaInsertRequest.usuarioId());

                PeluqueriaDto peluqueriaDto = PeluqueriaMapper.getInstance()
                                .fromInsertToDto(peluqueriaInsertRequest, usuarioDto);

                DtoValidator.validate(peluqueriaDto);

                PeluqueriaDetailResponse peluqueriaResponse = PeluqueriaMapper.getInstance()
                                .fromDtoToDetail(peluqueriaService.create(peluqueriaDto));

                return new ResponseEntity<>(peluqueriaResponse, HttpStatus.CREATED);
        }

        @RequireRole(roles = { Rol.Admin })
        @PutMapping("/{id}")
        public ResponseEntity<PeluqueriaDetailResponse> update(
                        @PathVariable Long id,
                        @RequestBody PeluqueriaUpdateRequest peluqueriaUpdateRequest) {
                PeluqueriaDto baseDto = peluqueriaService.getById(id);

                PeluqueriaDto peluqueriaDto = PeluqueriaMapper.getInstance()
                                .fromUpdateToDto(peluqueriaUpdateRequest);

                peluqueriaDto = new PeluqueriaDto(id, baseDto.usuario(), peluqueriaDto.municipio(),
                                peluqueriaDto.direccion(),
                                peluqueriaDto.telefono(), baseDto.productos());

                DtoValidator.validate(peluqueriaDto);

                PeluqueriaDetailResponse peluqueriaResponse = PeluqueriaMapper.getInstance()
                                .fromDtoToDetail(peluqueriaService.update(peluqueriaDto));

                return new ResponseEntity<>(peluqueriaResponse, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                peluqueriaService.delete(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
