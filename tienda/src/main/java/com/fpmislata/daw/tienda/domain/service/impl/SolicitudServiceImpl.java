package com.fpmislata.daw.tienda.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.fpmislata.daw.tienda.controller.webModel.request.AutorizacionRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.DestinoRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.OrigenPagoTarjetaRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.PagoRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.PagoTarjetaRequest;
import com.fpmislata.daw.tienda.domain.mapper.PeluqueriaMapper;
import com.fpmislata.daw.tienda.domain.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudPeluqueriaMapper;
import com.fpmislata.daw.tienda.domain.mapper.SolicitudProductoMapper;
import com.fpmislata.daw.tienda.domain.mapper.UsuarioMapper;
import com.fpmislata.daw.tienda.domain.model.Peluqueria;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.model.Solicitud;
import com.fpmislata.daw.tienda.domain.model.SolicitudPeluqueria;
import com.fpmislata.daw.tienda.domain.model.SolicitudProducto;
import com.fpmislata.daw.tienda.domain.model.Usuario;
import com.fpmislata.daw.tienda.domain.repository.SolicitudPeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.BancoService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.SolicitudService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class SolicitudServiceImpl implements SolicitudService {

        private final SolicitudRepository solicitudRepository;
        private final SolicitudProductoRepository solicitudProductoRepository;
        private final SolicitudPeluqueriaRepository solicitudPeluqueriaRepository;
        private final PeluqueriaService peluqueriaService;
        private final ProductoService productoService;
        private final UsuarioService usuarioService;
        private final AuthService authService;
        private final BancoService bancoService;

        public SolicitudServiceImpl(SolicitudRepository solicitudRepository,
                        SolicitudProductoRepository solicitudProductoRepository,
                        SolicitudPeluqueriaRepository solicitudPeluqueriaRepository,
                        PeluqueriaService peluqueriaService,
                        ProductoService productoService,
                        UsuarioService usuarioService,
                        AuthService authService,
                        BancoService bancoService) {
                this.solicitudRepository = solicitudRepository;
                this.solicitudProductoRepository = solicitudProductoRepository;
                this.solicitudPeluqueriaRepository = solicitudPeluqueriaRepository;
                this.peluqueriaService = peluqueriaService;
                this.productoService = productoService;
                this.usuarioService = usuarioService;
                this.authService = authService;
                this.bancoService = bancoService;
        }

        @Value("${banco.thebarberhub.login}")
        private String login;

        @Value("${banco.thebarberhub.api_token}")
        private String apiToken;

        @Value("${banco.thebarberhub.iban}")
        private String iban;

        @Override
        public SolicitudDto aprobarSolicitud(long solicitudId) {

                Solicitud solicitud = solicitudRepository.findById(solicitudId)
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada."));

                if (!solicitud.getEstado().equals(EstadoSolicitud.Pendiente)) {
                        throw new BusinessException("Solo se pueden aprobar solicitudes en estado pendiente");
                }

                solicitud.setEstado(EstadoSolicitud.Aprobada);

                if (solicitud.getTipo().equals(TipoSolicitud.Producto)) {
                        SolicitudProducto solicitudProducto = solicitudProductoRepository.findBySolicitud(solicitudId)
                                        .map(SolicitudProductoMapper.getInstance()::fromEntityToModel)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "No se encontró la solicitud"));

                        Peluqueria peluqueria = PeluqueriaMapper.getInstance()
                                        .fromDtoToModel(peluqueriaService
                                                        .findByUsuario(solicitud.getUsuario().getId()));

                        Producto producto = new Producto(
                                        null,
                                        solicitudProducto.getCategoria(),
                                        peluqueria,
                                        solicitudProducto.getNombre(),
                                        solicitudProducto.getPrecio(),
                                        solicitudProducto.getDuracion());

                        productoService.create(ProductoMapper.getInstance().fromModelToDto(producto));

                        solicitud.setEstado(EstadoSolicitud.Confirmada);
                }

                SolicitudEntity solicitudEntity = solicitudRepository
                                .save(SolicitudMapper.getInstance().fromModelToEntity(solicitud));

                return SolicitudMapper.getInstance()
                                .fromModelToDto(SolicitudMapper.getInstance().fromEntityToModel(solicitudEntity));
        }

        @Override
        public SolicitudDto rechazarSolicitud(long solicitudId) {
                Solicitud solicitud = solicitudRepository.findById(solicitudId)
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada."));

                if (!solicitud.getEstado().equals(EstadoSolicitud.Pendiente)) {
                        throw new BusinessException("Solo se pueden rechazar solicitudes en estado pendiente");
                }

                solicitud.setEstado(EstadoSolicitud.Rechazada);

                SolicitudEntity solicitudEntity = solicitudRepository
                                .save(SolicitudMapper.getInstance().fromModelToEntity(solicitud));

                return SolicitudMapper.getInstance()
                                .fromModelToDto(SolicitudMapper.getInstance().fromEntityToModel(solicitudEntity));
        }

        @Override
        public SolicitudDto confirmarSolicitudPeluqueria(String token, long solicitudId,
                        OrigenPagoTarjetaRequest origen) {
                Solicitud solicitud = solicitudRepository.findById(solicitudId)
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada."));

                UsuarioDto usuarioDto = authService.getByToken(token);

                if (!solicitud.getUsuario().getId().equals(usuarioDto.id())) {
                        throw new BusinessException("No tienes permiso para confirmar esta solicitud");
                }

                if (!solicitud.getTipo().equals(TipoSolicitud.Peluqueria)) {
                        throw new BusinessException("Esta solicitud no es de tipo peluquería");
                }

                if (!solicitud.getEstado().equals(EstadoSolicitud.Aprobada)) {
                        throw new BusinessException("Solo se pueden confirmar solicitudes en estado aprobada");
                }

                SolicitudPeluqueria solicitudPeluqueria = solicitudPeluqueriaRepository.findById(solicitudId)
                                .map(SolicitudPeluqueriaMapper.getInstance()::fromEntityToModel)
                                .orElseThrow(() -> new BusinessException("No se encontró la solicitud"));

                bancoService.pagoTarjeta(new PagoTarjetaRequest(
                                new AutorizacionRequest(login, apiToken),
                                origen,
                                new DestinoRequest(iban),
                                new PagoRequest(BigDecimal.valueOf(50), "Suscripción a TheBarberHub")));

                solicitud.setEstado(EstadoSolicitud.Confirmada);

                Usuario usuario = UsuarioMapper.getInstance()
                                .fromDtoToModel(usuarioService.getById(solicitud.getUsuario().getId()));

                usuarioService.updateRol(usuario.getId(), Rol.Peluqueria);

                Peluqueria peluqueria = new Peluqueria(
                                null,
                                usuario,
                                solicitudPeluqueria.getMunicipio(),
                                solicitudPeluqueria.getDireccion(),
                                solicitudPeluqueria.getTelefono(),
                                bancoService.getIbanByNumeroTarjeta(origen.numeroTarjeta()),
                                null,
                                null);

                peluqueriaService.create(PeluqueriaMapper.getInstance().fromModelToDto(peluqueria));

                SolicitudEntity solicitudEntity = solicitudRepository
                                .save(SolicitudMapper.getInstance().fromModelToEntity(solicitud));

                return SolicitudMapper.getInstance()
                                .fromModelToDto(SolicitudMapper.getInstance().fromEntityToModel(solicitudEntity));
        }

        @Override
        public List<SolicitudDto> getSolicitudesPendientes() {
                List<SolicitudEntity> solicitudesEntities = solicitudRepository
                                .findPendientes();

                return solicitudesEntities.stream()
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .map(SolicitudMapper.getInstance()::fromModelToDto)
                                .toList();
        }

        @Override
        public List<SolicitudDto> getSolicitudesAprobadasByPeluqueria(String token) {
                UsuarioDto usuarioDto = authService.getByToken(token);

                List<SolicitudEntity> solicitudEntities = solicitudRepository
                                .findAprobadas()
                                .stream()
                                .filter(s -> s.tipo().equals(TipoSolicitud.Peluqueria))
                                .filter(s -> s.usuario().id() == usuarioDto.id())
                                .toList();

                return solicitudEntities.stream()
                                .map(SolicitudMapper.getInstance()::fromEntityToModel)
                                .map(SolicitudMapper.getInstance()::fromModelToDto)
                                .toList();

        }

}