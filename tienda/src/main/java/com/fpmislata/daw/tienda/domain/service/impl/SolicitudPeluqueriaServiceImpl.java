package com.fpmislata.daw.tienda.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.domain.mapper.SolicitudMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudPeluqueriaMapper;
import com.fpmislata.daw.tienda.domain.repository.SolicitudPeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.SolicitudPeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudPeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class SolicitudPeluqueriaServiceImpl implements SolicitudPeluqueriaService {

        private final SolicitudRepository solicitudRepository;
        private final SolicitudPeluqueriaRepository solicitudPeluqueriaRepository;
        private final AuthService authService;

        public SolicitudPeluqueriaServiceImpl(SolicitudRepository solicitudRepository,
                        SolicitudPeluqueriaRepository solicitudPeluqueriaRepository,
                        AuthService authService) {
                this.solicitudRepository = solicitudRepository;
                this.solicitudPeluqueriaRepository = solicitudPeluqueriaRepository;
                this.authService = authService;
        }

        @Override
        public SolicitudPeluqueriaDto crearSolicitudAltaPeluqueria(String token, SolicitudPeluqueriaDto dto) {

                UsuarioDto usuario = authService.getByToken(token);
                if (!usuario.rol().equals(Rol.Cliente)) {
                        throw new BusinessException(
                                        "Solo los Usuarios con rol Cliente pueden solicitar ser Peluquería.");
                }

                List<SolicitudDto> solicitudesUsuario = solicitudRepository.findByUsuario(usuario.id())
                                .stream()
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .map(SolicitudMapper.getInstance()::fromModelToDto)
                                .toList();

                boolean tieneSolicitudPendiente = solicitudesUsuario.stream()
                                .anyMatch(
                                                s -> s.tipo().equals(TipoSolicitud.Peluqueria)
                                                                && s.estado().equals(EstadoSolicitud.Pendiente));

                if (tieneSolicitudPendiente) {
                        throw new BusinessException(
                                        "El Usuario ya tiene una solicitud de alta de Peluquería pendiente.");
                }

                SolicitudDto solicitudDto = new SolicitudDto(
                                null,
                                usuario,
                                TipoSolicitud.Peluqueria,
                                EstadoSolicitud.Pendiente,
                                LocalDateTime.now());

                SolicitudEntity solicitudEntity = solicitudRepository.save(SolicitudMapper.getInstance()
                                .fromModelToEntity(SolicitudMapper.getInstance().fromDtoToModel(solicitudDto)));

                SolicitudPeluqueriaDto solicitudPeluqueriaDto = new SolicitudPeluqueriaDto(
                                solicitudEntity.id(),
                                SolicitudMapper.getInstance().fromModelToDto(
                                                SolicitudMapper.getInstance().fromEntityToModel(solicitudEntity)),
                                dto.municipio(),
                                dto.direccion(),
                                dto.telefono());

                SolicitudPeluqueriaEntity solicitudPeluqueriaEntity = solicitudPeluqueriaRepository
                                .save(SolicitudPeluqueriaMapper.getInstance()
                                                .fromModelToEntity(SolicitudPeluqueriaMapper.getInstance()
                                                                .fromDtoToModel(solicitudPeluqueriaDto)));

                return SolicitudPeluqueriaMapper.getInstance().fromModelToDto(
                                SolicitudPeluqueriaMapper.getInstance().fromEntityToModel(solicitudPeluqueriaEntity));
        }

}
