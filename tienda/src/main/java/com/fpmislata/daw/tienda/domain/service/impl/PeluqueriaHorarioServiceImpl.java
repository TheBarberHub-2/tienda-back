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

                peluqueriaHorarioRepository.findById(dto.id())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "PeluqueriaHorario with id " + dto.id() + " not found"));

                PeluqueriaHorarioEntity saved = peluqueriaHorarioRepository.save(PeluqueriaHorarioMapper.getInstance()
                                .fromModelToEntity(PeluqueriaHorarioMapper.getInstance().fromDtoToModel(dto)));

                cancelarReservasFueraDeHorarios(saved.peluqueria().id(), saved.diaSemana());

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

                long peluqueriaId = horario.peluqueria().id();
                int diaSemana = horario.diaSemana();

                peluqueriaHorarioRepository.deleteById(id);

                cancelarReservasFueraDeHorarios(peluqueriaId, diaSemana);
        }

        @Override
        public Optional<PeluqueriaHorarioDto> findById(long id) {
                return peluqueriaHorarioRepository.findById(id)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromEntityToModel)
                                .map(PeluqueriaHorarioMapper.getInstance()::fromModelToDto);
        }

        private void cancelarReservasFueraDeHorarios(long peluqueriaId, int diaSemana) {
                List<PeluqueriaHorarioEntity> horarios = peluqueriaHorarioRepository
                                .findByPeluqueriaAndDia(peluqueriaId, diaSemana);

                List<ReservaEntity> reservas = reservaRepository.findByPeluqueriaAndDia(peluqueriaId, diaSemana);

                for (ReservaEntity reserva : reservas) {
                        boolean dentro = horarios.stream().anyMatch(h -> {
                                boolean starts = !reserva.horaInicio().isBefore(h.horaApertura());
                                boolean ends = reserva.horaFinal() == null
                                                || !reserva.horaFinal().isAfter(h.horaCierre());
                                return starts && ends;
                        });

                        if (!dentro) {
                                reservaService.cancelarReservaPorPeluqueria(reserva.id(), reserva.peluqueria().id());
                        }
                }
        }
}
