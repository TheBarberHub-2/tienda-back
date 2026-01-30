package com.fpmislata.daw.tienda.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.fpmislata.daw.tienda.domain.mapper.SolicitudMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudProductoMapper;
import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.SolicitudProductoService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class SolicitudProductoServiceImpl implements SolicitudProductoService {

        private final SolicitudRepository solicitudRepository;
        private final UsuarioService usuarioService;
        private final CategoriaService categoriaService;

        public SolicitudProductoServiceImpl(SolicitudRepository solicitudRepository, UsuarioService usuarioService,
                        CategoriaService categoriaService) {
                this.solicitudRepository = solicitudRepository;
                this.usuarioService = usuarioService;
                this.categoriaService = categoriaService;
        }

        @Override
        public SolicitudProductoDto crearSolicitudProducto(SolicitudProductoDto dto) {

                long usuarioId = dto.solicitud().usuario().id();

                UsuarioDto usuario = usuarioService.getById(usuarioId);
                if (!usuario.rol().equals(Rol.Peluqueria)) {
                        throw new BusinessException("Solo las peluquerías pueden solicitar la creación de productos.");
                }

                categoriaService.findById(dto.categoria().id())
                                .orElseThrow(() -> new BusinessException("La categoría indicada no existe."));

                List<SolicitudDto> solicitudesUsuario = solicitudRepository.findByUsuario(usuarioId)
                                .stream()
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .map(SolicitudMapper.getInstance()::fromModelToDto)
                                .toList();

                boolean existeSolicitudMismoProducto = solicitudesUsuario.stream()
                                .filter(s -> s.tipo().equals(TipoSolicitud.Producto)
                                                && s.estado().equals(EstadoSolicitud.Pendiente))
                                .flatMap(s -> s.solicitudesProducto().stream())
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
                                LocalDateTime.now(),
                                null,
                                null);

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

                SolicitudProductoEntity solicitudProductoEntity = SolicitudProductoMapper.getInstance()
                                .fromModelToEntity(SolicitudProductoMapper.getInstance()
                                                .fromDtoToModel(solicitudProductoDto));

                return SolicitudProductoMapper.getInstance()
                                .fromModelToDto(SolicitudProductoMapper.getInstance()
                                                .fromEntityToModel(solicitudProductoEntity));
        }

}
