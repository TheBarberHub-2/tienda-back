package com.fpmislata.daw.tienda.domain.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.mapper.PeluqueriaMapper;
import com.fpmislata.daw.tienda.domain.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.domain.mapper.ReservaMapper;
import com.fpmislata.daw.tienda.domain.mapper.UsuarioMapper;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaProductoEntity;
import com.fpmislata.daw.tienda.domain.service.EmailService;
import com.fpmislata.daw.tienda.domain.service.ReservaService;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaDto;
import com.fpmislata.daw.tienda.domain.service.dto.ReservaProductoDto;
import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaProductoRepository reservaProductoRepository;
    private final ProductoRepository productoRepository;
    private final PeluqueriaHorarioRepository peluqueriaHorarioRepository;
    private final EmailService emailService;

    public ReservaServiceImpl(
            ReservaRepository reservaRepository,
            ReservaProductoRepository reservaProductoRepository,
            ProductoRepository productoRepository,
            PeluqueriaHorarioRepository horarioRepository, EmailService emailService) {

        this.reservaRepository = reservaRepository;
        this.reservaProductoRepository = reservaProductoRepository;
        this.productoRepository = productoRepository;
        this.peluqueriaHorarioRepository = horarioRepository;
        this.emailService = emailService;
    }

    @Override
    public ReservaDto crearReserva(ReservaDto dto) {
        validarFecha(dto.fechaReserva());
        validarMinutos(dto.horaInicio());

        List<Long> productosIds = dto.productos().stream()
                .map(p -> p.producto().id())
                .toList();

        List<ProductoEntity> productos = productoRepository.findByIds(productosIds);

        validarProductosPeluqueria(productos, dto.peluqueria().id());
        validarCategorias(productos);

        int duracionTotal = productos.stream().mapToInt(ProductoEntity::duracion).sum();
        double precioTotal = productos.stream()
                .map(ProductoEntity::precio)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .doubleValue();

        LocalTime horaFinal = dto.horaInicio().plusMinutes(duracionTotal);

        validarHorario(dto.peluqueria().id(), dto.fechaReserva(), dto.horaInicio(), horaFinal);

        validarSolapamientos(dto.peluqueria().id(), dto.fechaReserva(), dto.horaInicio(), horaFinal);

        ReservaEntity entity = new ReservaEntity(
                null,
                UsuarioMapper.getInstance()
                        .fromModelToEntity(UsuarioMapper.getInstance().fromDtoToModel(dto.cliente())),
                PeluqueriaMapper.getInstance()
                        .fromModelToEntity(PeluqueriaMapper.getInstance().fromDtoToModel(dto.peluqueria())),
                (byte) dto.diaSemana().getValue(),
                dto.fechaReserva(),
                dto.horaInicio(),
                horaFinal,
                precioTotal,
                EstadoReserva.Pendiente,
                LocalDateTime.now(),
                null,
                List.of());

        ReservaEntity saved = reservaRepository.save(entity);

        List<ReservaProductoDto> productosDto = new ArrayList<>();

        for (ReservaProductoDto rpDto : dto.productos()) {
            ReservaProductoEntity rpEntity = new ReservaProductoEntity(
                    null,
                    saved,
                    ProductoMapper.getInstance()
                            .fromModelToEntity(ProductoMapper.getInstance().fromDtoToModel(rpDto.producto())));

            ReservaProductoEntity savedProducto = reservaProductoRepository.save(rpEntity);

            productosDto.add(new ReservaProductoDto(
                    savedProducto.id(),
                    null,
                    rpDto.producto()));
        }

        return new ReservaDto(
                saved.id(),
                dto.cliente(),
                dto.peluqueria(),
                dto.diaSemana(),
                dto.fechaReserva(),
                dto.horaInicio(),
                horaFinal,
                precioTotal,
                EstadoReserva.Pendiente,
                saved.createdAt(),
                saved.updatedAt(),
                productosDto);
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha.isBefore(LocalDate.now())) {
            throw new BusinessException("No se puede reservar en fechas pasadas");
        }

        if (fecha.isEqual(LocalDate.now())) {
            throw new BusinessException("No se puede reservar para el mismo día");
        }
    }

    private void validarMinutos(LocalTime hora) {
        if (hora.getMinute() % 5 != 0) {
            throw new BusinessException("La hora no es correcta");
        }
    }

    private void validarProductosPeluqueria(List<ProductoEntity> productos, Long peluqueriaId) {
        boolean samePeluqueria = productos.stream()
                .allMatch(p -> p.peluqueria().id().equals(peluqueriaId));

        if (!samePeluqueria) {
            throw new BusinessException("Todos los productos deben pertenecer a la misma peluquería");
        }
    }

    private void validarCategorias(List<ProductoEntity> productos) {
        long cortes = productos.stream().filter(p -> p.categoria().id() == 1).count();
        long barbas = productos.stream().filter(p -> p.categoria().id() == 2).count();
        long afeitado = productos.stream().filter(p -> p.categoria().id() == 4).count();

        if (cortes > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de pelo'");
        }

        if (barbas > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de barba'");
        }

        if (afeitado > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Afeitado clásico'");
        }
    }

    private void validarHorario(Long peluqueriaId, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        int diaSemana = fecha.getDayOfWeek().getValue();

        List<PeluqueriaHorarioEntity> horarios = peluqueriaHorarioRepository.findByPeluqueriaAndDia(peluqueriaId,
                diaSemana);

        boolean valid = horarios.stream()
                .anyMatch(h -> !inicio.isBefore(h.horaApertura()) && !fin.isAfter(h.horaCierre()));

        if (!valid) {
            throw new BusinessException("La reserva no encaja en el horario de la peluqueria");
        }
    }

    private void validarSolapamientos(Long peluqueriaId, LocalDate fecha, LocalTime inicio, LocalTime fin) {
        List<ReservaEntity> reservas = reservaRepository.findByPeluqueriaAndFecha(peluqueriaId, fecha).stream()
                .filter(r -> r.estado().equals(EstadoReserva.Pendiente))
                .toList();

        for (ReservaEntity r : reservas) {
            LocalTime rInicio = r.horaInicio();
            LocalTime rFin = r.horaFinal();

            LocalTime margenInicio = rInicio.minusMinutes(5);
            LocalTime margenFin = rFin.plusMinutes(5);

            boolean overlap = inicio.isBefore(margenFin) &&
                    fin.isAfter(margenInicio);

            if (overlap) {
                throw new BusinessException("La reserva solapa con otra existente o invade el margen de seguridad");
            }
        }
    }

    @Override
    public List<ReservaDto> listarReservasCliente(long clienteId) {
        List<ReservaEntity> reservas = reservaRepository.findByCliente(clienteId);

        return reservas.stream()
                .map(ReservaMapper.getInstance()::fromEntityToModel)
                .map(ReservaMapper.getInstance()::fromModelToDto)
                .toList();
    }

    @Override
    public List<ReservaDto> listarReservasClientePorEstado(long clienteId, String estado) {
        if (estado == null || estado.isBlank()) {
            throw new BusinessException("El estado no puede estar vacío");
        }

        String normalizado = estado.trim().toLowerCase();

        String enumName = normalizado.substring(0, 1).toUpperCase() + normalizado.substring(1);

        EstadoReserva estadoReserva;

        try {
            estadoReserva = EstadoReserva.fromString(enumName);
        } catch (Exception e) {
            throw new BusinessException("El estado " + estado + " no es válido");
        }

        List<ReservaEntity> reservas = reservaRepository.findByClienteAndEstado(clienteId, estadoReserva);

        return reservas.stream()
                .map(ReservaMapper.getInstance()::fromEntityToModel)
                .map(ReservaMapper.getInstance()::fromModelToDto)
                .toList();
    }

    @Override
    public List<ReservaDto> listarReservasPeluqueria(long peluqueriaId) {
        List<ReservaEntity> reservas = reservaRepository.findByPeluqueria(peluqueriaId);

        return reservas.stream()
                .map(ReservaMapper.getInstance()::fromEntityToModel)
                .map(ReservaMapper.getInstance()::fromModelToDto)
                .toList();
    }

    @Override
    public List<ReservaDto> listarReservasPeluqueriaPorEstado(long peluqueriaId, String estado) {
        if (estado == null || estado.isBlank()) {
            throw new BusinessException("El estado no puede estar vacío");
        }

        String normalizado = estado.trim().toLowerCase();

        String enumName = normalizado.substring(0, 1).toUpperCase() + normalizado.substring(1);

        EstadoReserva estadoReserva;

        try {
            estadoReserva = EstadoReserva.fromString(enumName);
        } catch (Exception e) {
            throw new BusinessException("El estado " + estado + " no es válido");
        }

        List<ReservaEntity> reservas = reservaRepository.findByPeluqueriaAndEstado(peluqueriaId, estadoReserva);

        return reservas.stream()
                .map(ReservaMapper.getInstance()::fromEntityToModel)
                .map(ReservaMapper.getInstance()::fromModelToDto)
                .toList();
    }

    @Override
    public ReservaDto cancelarReservaPorCliente(long reservaId, long clienteId) {
        ReservaEntity reserva = reservaRepository.findByIdAndClienteId(reservaId, clienteId)
                .orElseThrow(() -> new BusinessException("La reserva no pertenece al cliente"));

        if (reserva.estado() == EstadoReserva.Cancelada || reserva.estado() == EstadoReserva.Completada) {
            throw new BusinessException("La reserva ya no puede cancelarse");
        }

        LocalDateTime fechaHoraReserva = LocalDateTime.of(
                reserva.fechaReserva(),
                reserva.horaInicio());

        if (LocalDateTime.now().plusHours(24).isAfter(fechaHoraReserva)) {
            throw new BusinessException("Solo se puede cancelar con 24 horas de antelación");
        }

        ReservaEntity updated = new ReservaEntity(
                reserva.id(),
                reserva.cliente(),
                reserva.peluqueria(),
                reserva.diaSemana(),
                reserva.fechaReserva(),
                reserva.horaInicio(),
                reserva.horaFinal(),
                reserva.precioTotal(),
                EstadoReserva.Cancelada,
                reserva.createdAt(),
                LocalDateTime.now(),
                reserva.productos());

        reservaRepository.save(updated);

        emailService.enviarCancelacionPeluqueria(reserva);

        return ReservaMapper.getInstance().fromModelToDto(ReservaMapper.getInstance().fromEntityToModel(updated));
    }

    @Override
    public ReservaDto cancelarReservaPorPeluqueria(long reservaId, long peluqueriaId) {

        ReservaEntity reserva = reservaRepository.findByIdAndPeluqueriaId(reservaId, peluqueriaId)
                .orElseThrow(() -> new BusinessException("La reserva no pertenece a esta peluquería"));

        if (reserva.estado() == EstadoReserva.Cancelada || reserva.estado() == EstadoReserva.Completada) {
            throw new BusinessException("La reserva ya no puede cancelarse");
        }

        ReservaEntity updated = new ReservaEntity(
                reserva.id(),
                reserva.cliente(),
                reserva.peluqueria(),
                reserva.diaSemana(),
                reserva.fechaReserva(),
                reserva.horaInicio(),
                reserva.horaFinal(),
                reserva.precioTotal(),
                EstadoReserva.Cancelada,
                reserva.createdAt(),
                LocalDateTime.now(),
                reserva.productos());

        reservaRepository.save(updated);

        emailService.enviarCancelacionCliente(reserva);

        return ReservaMapper.getInstance()
                .fromModelToDto(ReservaMapper.getInstance().fromEntityToModel(updated));
    }

    @Override
    public ReservaDto confirmarReserva(long reservaId, long peluqueriaId) {
        ReservaEntity reserva = reservaRepository.findByIdAndPeluqueriaId(reservaId, peluqueriaId)
                .orElseThrow(() -> new BusinessException("La reserva no pertenece a esta peluquería"));

        if (reserva.estado() == EstadoReserva.Cancelada || reserva.estado() == EstadoReserva.Completada) {
            throw new BusinessException("La reserva ya no puede completarse");
        }

        LocalDateTime fechaHoraReserva = LocalDateTime.of(
                reserva.fechaReserva(),
                reserva.horaFinal());

        if (LocalDateTime.now().isBefore(fechaHoraReserva)) {
            throw new BusinessException("No puedes completar una reserva no acabada");
        }

        ReservaEntity updated = new ReservaEntity(
                reserva.id(),
                reserva.cliente(),
                reserva.peluqueria(),
                reserva.diaSemana(),
                reserva.fechaReserva(),
                reserva.horaInicio(),
                reserva.horaFinal(),
                reserva.precioTotal(),
                EstadoReserva.Completada,
                reserva.createdAt(),
                LocalDateTime.now(),
                reserva.productos());

        reservaRepository.save(updated);

        return ReservaMapper.getInstance()
                .fromModelToDto(ReservaMapper.getInstance().fromEntityToModel(updated));
    }
}
