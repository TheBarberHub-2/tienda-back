package com.fpmislata.daw.tienda.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.SolicitudMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudProductoMapper;
import com.fpmislata.daw.tienda.domain.repository.SolicitudProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.SolicitudProductoService;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class SolicitudProductoServiceImpl implements SolicitudProductoService {

        private final SolicitudProductoRepository solicitudProductoRepository;
        private final SolicitudRepository solicitudRepository;
        private final AuthService authService;
        private final CategoriaService categoriaService;

        public SolicitudProductoServiceImpl(SolicitudProductoRepository solicitudProductoRepository,
                        SolicitudRepository solicitudRepository, AuthService authService,
                        CategoriaService categoriaService) {
                this.solicitudProductoRepository = solicitudProductoRepository;
                this.solicitudRepository = solicitudRepository;
                this.authService = authService;
                this.categoriaService = categoriaService;
        }

        @Override
        public SolicitudProductoDto crearSolicitudProducto(String token, SolicitudProductoDto dto) {

                UsuarioDto usuario = authService.getByToken(token);
                if (!usuario.rol().equals(Rol.Peluqueria)) {
                        throw new BusinessException("Solo las peluquerías pueden solicitar la creación de productos.");
                }

                categoriaService.findById(dto.categoria().id())
                                .orElseThrow(() -> new BusinessException("La categoría indicada no existe."));

                List<SolicitudDto> solicitudesUsuario = solicitudRepository.findById(usuario.id())
                                .stream()
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .map(SolicitudMapper.getInstance()::fromModelToDto)
                                .toList();

                boolean existeSolicitudMismoProducto = solicitudesUsuario.stream()
                                .filter(s -> s.tipo().equals(TipoSolicitud.Producto))
                                .map(s -> solicitudProductoRepository.findBySolicitud(s.id()))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .anyMatch(sp -> sp.nombre().equalsIgnoreCase(dto.nombre()));

                if (existeSolicitudMismoProducto) {
                        throw new BusinessException(
                                        "Ya existe una solicitud pendiente para un producto con el mismo nombre.");
                }

                SolicitudDto solicitudDto = new SolicitudDto(
                                null,
                                usuario,
                                TipoSolicitud.Producto,
                                EstadoSolicitud.Pendiente,
                                LocalDateTime.now());

                SolicitudEntity solicitudEntity = solicitudRepository.save(SolicitudMapper.getInstance()
                                .fromModelToEntity(SolicitudMapper.getInstance().fromDtoToModel(solicitudDto)));

                SolicitudProductoDto solicitudProductoDto = new SolicitudProductoDto(
                                solicitudEntity.id(),
                                SolicitudMapper.getInstance()
                                                .fromModelToDto(SolicitudMapper.getInstance()
                                                                .fromEntityToModel(solicitudEntity)),
                                dto.categoria(),
                                dto.nombre(),
                                dto.precio(),
                                dto.duracion());

                SolicitudProductoEntity solicitudProductoEntity = solicitudProductoRepository
                                .save(SolicitudProductoMapper.getInstance()
                                                .fromModelToEntity(SolicitudProductoMapper.getInstance()
                                                                .fromDtoToModel(solicitudProductoDto)));

                return SolicitudProductoMapper.getInstance()
                                .fromModelToDto(SolicitudProductoMapper.getInstance()
                                                .fromEntityToModel(solicitudProductoEntity));
        }

        @Override
        public SolicitudProductoDto getById(long id) {
                SolicitudProductoEntity entity = solicitudProductoRepository.findById(id)
                                .orElseThrow(() -> new BusinessException("No se encontró la solicitud de producto."));

                return SolicitudProductoMapper.getInstance().fromModelToDto(
                                SolicitudProductoMapper.getInstance().fromEntityToModel(entity));
        }

}
