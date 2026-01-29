package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.PeluqueriaHorarioMapper;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaHorarioService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class PeluqueriaHorarioServiceImpl implements PeluqueriaHorarioService {

        private final PeluqueriaHorarioRepository peluqueriaHorarioRepository;

        public PeluqueriaHorarioServiceImpl(PeluqueriaHorarioRepository peluqueriaHorarioRepository) {
                this.peluqueriaHorarioRepository = peluqueriaHorarioRepository;
        }

        @Override
        public PeluqueriaHorarioDto create(PeluqueriaHorarioDto peluqueriaHorarioDto) {
                validarHorarios(peluqueriaHorarioDto);

                PeluqueriaHorarioEntity entity = PeluqueriaHorarioMapper.getInstance().fromModelToEntity(
                                PeluqueriaHorarioMapper.getInstance().fromDtoToModel(peluqueriaHorarioDto));

                return PeluqueriaHorarioMapper.getInstance().fromModelToDto(
                                PeluqueriaHorarioMapper.getInstance()
                                                .fromEntityToModel(peluqueriaHorarioRepository.save(entity)));
        }

        @Override
        public PeluqueriaHorarioDto update(PeluqueriaHorarioDto peluqueriaHorarioDto) {
                validarHorarios(peluqueriaHorarioDto);
                peluqueriaHorarioRepository.findById(peluqueriaHorarioDto.id())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "PeluqueriaHorario with id " + peluqueriaHorarioDto.id()
                                                                + " not found"));

                PeluqueriaHorarioEntity entity = PeluqueriaHorarioMapper.getInstance().fromModelToEntity(
                                PeluqueriaHorarioMapper.getInstance().fromDtoToModel(peluqueriaHorarioDto));

                return PeluqueriaHorarioMapper.getInstance().fromModelToDto(
                                PeluqueriaHorarioMapper.getInstance()
                                                .fromEntityToModel(peluqueriaHorarioRepository.save(entity)));
        }

        @Override
        public List<PeluqueriaHorarioDto> findByPeluqueria(long peluqueriaId) {
                List<PeluqueriaHorarioEntity> entities = peluqueriaHorarioRepository.findByPeluqueria(peluqueriaId);

                List<PeluqueriaHorarioDto> dtos = entities.stream()
                                .map(PeluqueriaHorarioMapper.getInstance()::fromEntityToModel)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromModelToDto)
                                .toList();

                return dtos;
        }

        @Override
        public void validarHorarios(PeluqueriaHorarioDto peluqueriaHorarioDto) {
                List<PeluqueriaHorarioEntity> existentes = peluqueriaHorarioRepository
                                .findByPeluqueria(peluqueriaHorarioDto.peluqueria().id());

                for (PeluqueriaHorarioEntity p : existentes) {
                        if (p.diaSemana() != peluqueriaHorarioDto.diaSemana().getValue()) {
                                continue;
                        }

                        if (peluqueriaHorarioDto.id() != null && peluqueriaHorarioDto.id().equals(p.id())) {
                                continue;
                        }

                        boolean solapan = peluqueriaHorarioDto.horaApertura().isBefore(p.horaCierre())
                                        && peluqueriaHorarioDto.horaCierre().isAfter(p.horaApertura());

                        if (solapan) {
                                throw new BusinessException("El horario se solapa con otro existente: "
                                                + p.horaApertura() + " - " + p.horaCierre());
                        }
                }
        }

        @Override
        public void deleteById(long id) {
                Optional<PeluqueriaHorarioDto> dto = findById(id);

                if (dto.isEmpty()) {
                        throw new ResourceNotFoundException("Horario with id: " + id + " not found");
                }

                peluqueriaHorarioRepository.deleteById(id);
        }

        @Override
        public Optional<PeluqueriaHorarioDto> findById(long id) {
                return peluqueriaHorarioRepository.findById(id)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromEntityToModel)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromModelToDto);
        }

}
