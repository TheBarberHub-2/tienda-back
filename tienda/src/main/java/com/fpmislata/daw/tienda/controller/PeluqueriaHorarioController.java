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
import org.springframework.web.bind.annotation.RestController;

import com.fpmislata.daw.tienda.controller.mapper.PeluqueriaHorarioMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaHorarioRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaHorarioResponse;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaHorarioService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.domain.validation.RequireSamePeluqueria;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/peluqueria")
public class PeluqueriaHorarioController {

        private final PeluqueriaHorarioService peluqueriaHorarioService;

        private final PeluqueriaService peluqueriaService;

        public PeluqueriaHorarioController(PeluqueriaHorarioService peluqueriaHorarioService,
                        PeluqueriaService peluqueriaService) {
                this.peluqueriaHorarioService = peluqueriaHorarioService;
                this.peluqueriaService = peluqueriaService;
        }

        @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
        @GetMapping("/{peluqueriaId}/horarios")
        public ResponseEntity<List<PeluqueriaHorarioResponse>> findByPeluqueria(@PathVariable Long peluqueriaId) {
                List<PeluqueriaHorarioDto> dtos = peluqueriaHorarioService.findByPeluqueria(peluqueriaId);

                List<PeluqueriaHorarioResponse> response = dtos.stream()
                                .map(PeluqueriaHorarioMapper.getInstance()::fromDtoToResponse)
                                .toList();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @RequireSamePeluqueria(paramName = "peluqueriaId")
        @PostMapping("/{peluqueriaId}/horarios")
        public ResponseEntity<PeluqueriaHorarioResponse> create(@PathVariable Long peluqueriaId,
                        @RequestBody PeluqueriaHorarioRequest request) {
                PeluqueriaDto peluqueriaDto = peluqueriaService.getById(peluqueriaId);

                PeluqueriaHorarioDto horarioDto = PeluqueriaHorarioMapper.getInstance().fromRequestToDto(request);

                horarioDto = new PeluqueriaHorarioDto(null, peluqueriaDto, horarioDto.diaSemana(),
                                horarioDto.horaApertura(),
                                horarioDto.horaCierre());

                DtoValidator.validate(horarioDto);

                PeluqueriaHorarioResponse response = PeluqueriaHorarioMapper.getInstance()
                                .fromDtoToResponse(peluqueriaHorarioService.create(horarioDto));

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @RequireSamePeluqueria(paramName = "peluqueriaId")
        @PutMapping("/{peluqueriaId}/horarios/{id}")
        public ResponseEntity<PeluqueriaHorarioResponse> update(@PathVariable Long peluqueriaId,
                        @PathVariable Long id,
                        @RequestBody PeluqueriaHorarioRequest request) {
                PeluqueriaHorarioDto baseDto = peluqueriaHorarioService.findById(id)
                                .orElseThrow(() -> new BusinessException("Horario no encontrado"));

                if (!baseDto.peluqueria().id().equals(peluqueriaId)) {
                        throw new ResourceNotFoundException("Acceso denegado");
                }

                PeluqueriaHorarioDto horarioDto = PeluqueriaHorarioMapper.getInstance().fromRequestToDto(request);

                horarioDto = new PeluqueriaHorarioDto(baseDto.id(), baseDto.peluqueria(), horarioDto.diaSemana(),
                                horarioDto.horaApertura(),
                                horarioDto.horaCierre());

                DtoValidator.validate(horarioDto);

                PeluqueriaHorarioResponse response = PeluqueriaHorarioMapper.getInstance()
                                .fromDtoToResponse(peluqueriaHorarioService.update(horarioDto));

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @RequireSamePeluqueria(paramName = "peluqueriaId")
        @DeleteMapping("/{peluqueriaId}/horarios/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long peluqueriaId, @PathVariable Long id) {

                PeluqueriaHorarioDto horarioDto = peluqueriaHorarioService.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado"));

                if (!horarioDto.peluqueria().id().equals(peluqueriaId)) {
                        throw new BusinessException("Acceso denegado");
                }

                peluqueriaHorarioService.deleteById(id);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
