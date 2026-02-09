package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.PeluqueriaHorarioMapper;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaHorarioService;
import com.fpmislata.daw.tienda.domain.service.ReservaService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class PeluqueriaHorarioServiceImpl implements PeluqueriaHorarioService {

        private final PeluqueriaHorarioRepository peluqueriaHorarioRepository;
        private final ReservaRepository reservaRepository;
        private final ReservaService reservaService;

        public PeluqueriaHorarioServiceImpl(PeluqueriaHorarioRepository peluqueriaHorarioRepository,
                        ReservaRepository reservaRepository, ReservaService reservaService) {
                this.peluqueriaHorarioRepository = peluqueriaHorarioRepository;
                this.reservaRepository = reservaRepository;
                this.reservaService = reservaService;
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
        public PeluqueriaHorarioDto update(PeluqueriaHorarioDto dto) {

                validarHorarios(dto);

                PeluqueriaHorarioEntity original = peluqueriaHorarioRepository.findById(dto.id())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "PeluqueriaHorario with id " + dto.id() + " not found"));

                PeluqueriaHorarioEntity saved = peluqueriaHorarioRepository.save(original);

                cancelarReservasAfectadas(saved);

                return PeluqueriaHorarioMapper.getInstance()
                                .fromModelToDto(PeluqueriaHorarioMapper.getInstance().fromEntityToModel(saved));
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

                PeluqueriaHorarioEntity horario = peluqueriaHorarioRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Horario with id: " + id + " not found"));

                cancelarReservasAfectadas(horario);

                peluqueriaHorarioRepository.deleteById(id);
        }

        @Override
        public Optional<PeluqueriaHorarioDto> findById(long id) {
                return peluqueriaHorarioRepository.findById(id)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromEntityToModel)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromModelToDto);
        }

        private void cancelarReservasAfectadas(PeluqueriaHorarioEntity horario) {
                List<ReservaEntity> reservas = reservaRepository.findByPeluqueriaAndDia(horario.peluqueria().id(),
                                horario.diaSemana());

                for (ReservaEntity reservaEntity : reservas) {
                        boolean fueraHorario = reservaEntity.horaInicio().isBefore(horario.horaApertura()) ||
                                        (reservaEntity.horaFinal() != null
                                                        && reservaEntity.horaFinal().isAfter(horario.horaCierre()));

                        if (fueraHorario) {
                                reservaService.cancelarReservaPorPeluqueria(reservaEntity.id(),
                                                reservaEntity.peluqueria().id());
                        }
                }
        }
}
