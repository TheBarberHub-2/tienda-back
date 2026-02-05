package com.fpmislata.daw.tienda.controller;

import java.util.List;
import java.util.Map;

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

import com.fpmislata.daw.tienda.controller.mapper.SolicitudMapper;
import com.fpmislata.daw.tienda.controller.webModel.request.SolicitudPeluqueriaRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.SolicitudProductoRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudPeluqueriaDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudProductoDetailResponse;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.SolicitudPeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.SolicitudProductoService;
import com.fpmislata.daw.tienda.domain.service.SolicitudService;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

        private final SolicitudService solicitudService;

        private final SolicitudPeluqueriaService solicitudPeluqueriaService;

        private final SolicitudProductoService solicitudProductoService;

        private final CategoriaService categoriaService;

        public SolicitudController(SolicitudService solicitudService,
                        SolicitudPeluqueriaService solicitudPeluqueriaService,
                        SolicitudProductoService solicitudProductoService, CategoriaService categoriaService) {
                this.solicitudService = solicitudService;
                this.solicitudPeluqueriaService = solicitudPeluqueriaService;
                this.solicitudProductoService = solicitudProductoService;
                this.categoriaService = categoriaService;
        }

        @RequireRole(roles = { Rol.Admin })
        @GetMapping("/pendientes")
        public ResponseEntity<Map<String, Object>> getSolicitudesPendientes() {
                List<SolicitudDto> solicitudes = solicitudService.getSolicitudesPendientes();

                List<Long> idsPeluqueria = solicitudes.stream()
                                .filter(s -> s.tipo() == TipoSolicitud.Peluqueria)
                                .map(SolicitudDto::id)
                                .toList();

                List<Long> idsProducto = solicitudes.stream()
                                .filter(s -> s.tipo() == TipoSolicitud.Producto)
                                .map(SolicitudDto::id)
                                .toList();

                List<SolicitudPeluqueriaDetailResponse> peluquerias = idsPeluqueria.stream()
                                .map(id -> solicitudPeluqueriaService.getById(id))
                                .map(SolicitudMapper.getInstance()::fromPeluqueriaToResponse)
                                .toList();

                List<SolicitudProductoDetailResponse> productos = idsProducto.stream()
                                .map(id -> solicitudProductoService.getById(id))
                                .map(SolicitudMapper.getInstance()::fromProductoToResponse)
                                .toList();

                Map<String, Object> response = Map.of(
                                "peluquerias", peluquerias,
                                "productos", productos);

                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Cliente })
        @GetMapping("/aprobadas")
        public ResponseEntity<List<SolicitudDetailResponse>> getSolicitudesAprobadasPeluqueria() {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                .getRequest();

                String token = request.getHeader("token");

                List<SolicitudDto> solicitudes = solicitudService.getSolicitudesAprobadasByPeluqueria(token);
                List<SolicitudDetailResponse> response = solicitudes.stream()
                                .map(SolicitudMapper.getInstance()::fromSolicitudToDetail)
                                .toList();
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @RequireRole(roles = { Rol.Cliente })
        @PostMapping("/create/peluquerias")
        public ResponseEntity<SolicitudPeluqueriaDetailResponse> crearSolicitudPeluqueria(
                        @RequestBody SolicitudPeluqueriaRequest solicitudPeluqueriaRequest) {

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                .getRequest();

                String token = request.getHeader("token");

                SolicitudPeluqueriaDetailResponse response = SolicitudMapper.getInstance()
                                .fromPeluqueriaToResponse(solicitudPeluqueriaService.crearSolicitudAltaPeluqueria(token,
                                                SolicitudMapper.getInstance()
                                                                .fromRequestToPeluqueria(solicitudPeluqueriaRequest)));

                return ResponseEntity.ok(response);

        }

        @RequireRole(roles = { Rol.Peluqueria })
        @PostMapping("/create/producto")
        public ResponseEntity<SolicitudProductoDetailResponse> crearSolicitudProducto(
                        @RequestBody SolicitudProductoRequest solicitudProductoRequest) {

                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                .getRequest();

                String token = request.getHeader("token");

                CategoriaDto categoriaDto = categoriaService.getById(solicitudProductoRequest.categoriaId());

                SolicitudProductoDetailResponse response = SolicitudMapper.getInstance()
                                .fromProductoToResponse(solicitudProductoService.crearSolicitudProducto(token,
                                                SolicitudMapper.getInstance().fromRequestToProducto(
                                                                solicitudProductoRequest, categoriaDto)));

                return ResponseEntity.ok(response);

        }

        @RequireRole(roles = { Rol.Admin })
        @PutMapping("/aprobar/{solicitudId}")
        public ResponseEntity<SolicitudDetailResponse> aprobarSolicitud(@PathVariable long solicitudId) {
                SolicitudDto solicitudDto = solicitudService.aprobarSolicitud(solicitudId);

                SolicitudDetailResponse response = SolicitudMapper.getInstance()
                                .fromSolicitudToDetail(solicitudDto);

                return ResponseEntity.ok(response);
        }

        @RequireRole(roles = { Rol.Admin })
        @PutMapping("/rechazar/{solicitudId}")
        public ResponseEntity<SolicitudDetailResponse> rechazarSolicitud(@PathVariable long solicitudId) {
                SolicitudDto solicitudDto = solicitudService.rechazarSolicitud(solicitudId);

                SolicitudDetailResponse response = SolicitudMapper.getInstance()
                                .fromSolicitudToDetail(solicitudDto);

                return ResponseEntity.ok(response);
        }

        @RequireRole(roles = { Rol.Cliente })
        @PutMapping("/confirmar/peluqueria/{solicitudId}")
        public ResponseEntity<SolicitudDetailResponse> confirmarSolicitudPeluqueria(@PathVariable long solicitudId) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                                .getRequest();

                String token = request.getHeader("token");
                SolicitudDto solicitudDto = solicitudService.confirmarSolicitudPeluqueria(token, solicitudId);

                SolicitudDetailResponse response = SolicitudMapper.getInstance()
                                .fromSolicitudToDetail(solicitudDto);

                return ResponseEntity.ok(response);
        }
}
