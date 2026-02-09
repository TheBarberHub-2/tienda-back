package com.fpmislata.daw.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fpmislata.daw.tienda.controller.mapper.ReservaMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.ReservaInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.ReservaResponse;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.ReservaService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaProductoDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.domain.validation.spring_validator.DtoValidator;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

        private final ReservaService reservaService;
        private final UsuarioService usuarioService;
        private final PeluqueriaService peluqueriaService;
        private final ProductoService productoService;
        private final AuthService authService;

        public ReservaController(
                        ReservaService reservaService,
                        UsuarioService usuarioService,
                        PeluqueriaService peluqueriaService,
                        ProductoService productoService,
                        AuthService authService) {

                this.reservaService = reservaService;
                this.usuarioService = usuarioService;
                this.peluqueriaService = peluqueriaService;
                this.productoService = productoService;
                this.authService = authService;
        }

        @RequireRole(roles = { Rol.Admin, Rol.Cliente })
        @PostMapping("/crear")
        public ResponseEntity<ReservaResponse> crearReserva(@RequestBody ReservaInsertRequest request) {

                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");

                UsuarioDto usuarioDto = usuarioService.getById(request.clienteId());
                UsuarioDto logged = authService.getByToken(token);

                if (!usuarioDto.id().equals(logged.id())) {
                        throw new BusinessException("Solo puedes hacer reservas a tu nombre");
                }
                PeluqueriaDto peluqueriaDto = peluqueriaService.getById(request.peluqueriaId());
                List<Long> ids = request.productoIds();
                List<ProductoDto> productoDtos = productoService.findByIds(ids);

                if (productoDtos.size() != ids.size()) {
                        throw new BusinessException("Algunos productos no existen");
                }

                List<ReservaProductoDto> reservaProductoDtos = productoDtos.stream()
                                .map(p -> new ReservaProductoDto(
                                                null,
                                                null,
                                                p))
                                .toList();

                ReservaDto reservaDto = ReservaMapper.getInstance().fromRequestToDto(request, usuarioDto, peluqueriaDto,
                                reservaProductoDtos);

                DtoValidator.validate(reservaDto);

                ReservaDto creada = reservaService.crearReserva(reservaDto);

                ReservaResponse response = ReservaMapper.getInstance().fromDtoToResponse(creada);

                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @RequireRole(roles = { Rol.Admin, Rol.Cliente })
        @GetMapping("/cliente/{clienteId}")
        public ResponseEntity<List<ReservaResponse>> listarReservasCliente(@PathVariable long clienteId) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");

                UsuarioDto usuarioDto = usuarioService.getById(clienteId);
                UsuarioDto logged = authService.getByToken(token);

                if (!usuarioDto.id().equals(logged.id())) {
                        throw new BusinessException("Solo puedes ver tus propias reservas");
                }

                List<ReservaDto> reservas = reservaService.listarReservasCliente(clienteId);

                List<ReservaResponse> response = reservas.stream()
                                .map(ReservaMapper.getInstance()::fromDtoToResponse)
                                .toList();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin, Rol.Cliente })
        @GetMapping("/cliente/{clienteId}/{estado}")
        public ResponseEntity<List<ReservaResponse>> listarReservasClienteEstado(@PathVariable long clienteId,
                        @PathVariable String estado) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");

                UsuarioDto usuarioDto = usuarioService.getById(clienteId);
                UsuarioDto logged = authService.getByToken(token);

                if (!usuarioDto.id().equals(logged.id())) {
                        throw new BusinessException("Solo puedes ver tus propias reservas");
                }

                List<ReservaDto> reservas = reservaService.listarReservasClientePorEstado(clienteId, estado);

                List<ReservaResponse> response = reservas.stream()
                                .map(ReservaMapper.getInstance()::fromDtoToResponse)
                                .toList();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @GetMapping("/peluqueria/{peluqueriaId}")
        public ResponseEntity<List<ReservaResponse>> listarReservasPeluqueria(@PathVariable long peluqueriaId) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");

                PeluqueriaDto peluqueriaDto = peluqueriaService.getById(peluqueriaId);
                UsuarioDto usuarioDto = peluqueriaDto.usuario();
                UsuarioDto logged = authService.getByToken(token);

                if (!usuarioDto.id().equals(logged.id())) {
                        throw new BusinessException("Solo puedes ver las reservas de tu peluquería");
                }

                List<ReservaDto> reservas = reservaService.listarReservasPeluqueria(peluqueriaId);

                List<ReservaResponse> response = reservas.stream()
                                .map(ReservaMapper.getInstance()::fromDtoToResponse)
                                .toList();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @GetMapping("/peluqueria/{peluqueriaId}/{estado}")
        public ResponseEntity<List<ReservaResponse>> listarReservasPeluqueriaEstado(@PathVariable long peluqueriaId,
                        @PathVariable String estado) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");

                PeluqueriaDto peluqueriaDto = peluqueriaService.getById(peluqueriaId);
                UsuarioDto usuarioDto = peluqueriaDto.usuario();
                UsuarioDto logged = authService.getByToken(token);

                if (!usuarioDto.id().equals(logged.id())) {
                        throw new BusinessException("Solo puedes ver las reservas de tu peluquería");
                }

                List<ReservaDto> reservas = reservaService.listarReservasPeluqueriaPorEstado(peluqueriaId, estado);

                List<ReservaResponse> response = reservas.stream()
                                .map(ReservaMapper.getInstance()::fromDtoToResponse)
                                .toList();

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Admin, Rol.Cliente })
        @PutMapping("/cancelar/cliente/{reservaId}")
        public ResponseEntity<ReservaResponse> cancelarReservaCliente(@PathVariable long reservaId) {
                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");
                UsuarioDto logged = authService.getByToken(token);

                ReservaDto result = reservaService.cancelarReservaPorCliente(reservaId, logged.id());

                ReservaResponse response = ReservaMapper.getInstance().fromDtoToResponse(result);

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @PutMapping("/cancelar/peluqueria/{reservaId}")
        public ResponseEntity<ReservaResponse> cancelarReservaPeluqueria(@PathVariable long reservaId) {

                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");
                UsuarioDto logged = authService.getByToken(token);

                PeluqueriaDto peluqueriaDto = peluqueriaService.findByUsuario(logged.id());

                ReservaDto result = reservaService.cancelarReservaPorPeluqueria(reservaId, peluqueriaDto.id());

                ReservaResponse response = ReservaMapper.getInstance().fromDtoToResponse(result);

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Peluqueria })
        @PutMapping("/confirmar/peluqueria/{reservaId}")
        public ResponseEntity<ReservaResponse> confirmarReservaPeluqueria(@PathVariable long reservaId) {

                HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder
                                .getRequestAttributes())
                                .getRequest();

                String token = httpRequest.getHeader("token");
                UsuarioDto logged = authService.getByToken(token);

                PeluqueriaDto peluqueriaDto = peluqueriaService.findByUsuario(logged.id());

                ReservaDto result = reservaService.confirmarReserva(reservaId, peluqueriaDto.id());

                ReservaResponse response = ReservaMapper.getInstance().fromDtoToResponse(result);

                return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
