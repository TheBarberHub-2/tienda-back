package com.fpmislata.daw.tienda.domain.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fpmislata.daw.tienda.domain.mapper.CarritoMapper;
import com.fpmislata.daw.tienda.domain.mapper.ProductoMapper;
import com.fpmislata.daw.tienda.domain.model.Carrito;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.domain.service.CarritoService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoDto;
import com.fpmislata.daw.tienda.domain.service.dto.CarritoInputDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesDto;
import com.fpmislata.daw.tienda.domain.service.dto.SlotsDisponiblesInputDto;
import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class CarritoServiceImpl implements CarritoService {

    private final ProductoRepository productoRepository;
    private final PeluqueriaService peluqueriaService;
    private final PeluqueriaHorarioRepository peluqueriaHorarioRepository;
    private final ReservaRepository reservaRepository;

    public CarritoServiceImpl(ProductoRepository productoRepository, PeluqueriaService peluqueriaService,
            PeluqueriaHorarioRepository peluqueriaHorarioRepository, ReservaRepository reservaRepository) {
        this.productoRepository = productoRepository;
        this.peluqueriaService = peluqueriaService;
        this.peluqueriaHorarioRepository = peluqueriaHorarioRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public CarritoDto calcularCarrito(CarritoInputDto dto) {
        PeluqueriaDto peluqueriaDto = peluqueriaService.getById(dto.peluqueriaId());

        List<Long> productosIds = dto.productoIds();

        List<ProductoEntity> productoEntities = productoRepository.findByIds(productosIds);

        if (productoEntities.size() != productosIds.size()) {
            throw new BusinessException("Algunos productos no existen");
        }

        boolean allSamePeluqueria = productoEntities.stream()
                .allMatch(p -> p.peluqueria().id().equals(peluqueriaDto.id()));

        if (!allSamePeluqueria) {
            throw new BusinessException("Todos los productos deben pertenecer a la misma peluquería");
        }

        validarCategorias(productoEntities);

        List<Producto> productos = productoEntities.stream()
                .map(ProductoMapper.getInstance()::fromEntityToModel)
                .toList();

        int duracionTotal = productos.stream()
                .mapToInt(Producto::getDuracion)
                .sum();

        BigDecimal precioTotal = productos.stream()
                .map(Producto::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Carrito carrito = new Carrito(
                dto.peluqueriaId(),
                productos,
                duracionTotal,
                precioTotal);

        return CarritoMapper.getInstance().fromModelToDto(carrito);
    }

    private void validarCategorias(List<ProductoEntity> productos) {
        Map<Long, Long> categoriaCount = productos.stream()
                .collect(Collectors.groupingBy(p -> p.categoria().id(), Collectors.counting()));

        if (categoriaCount.getOrDefault(1L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de pelo'");
        }

        if (categoriaCount.getOrDefault(2L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Corte de barba'");
        }

        if (categoriaCount.getOrDefault(4L, 0L) > 1) {
            throw new BusinessException("Solo puede seleccionar un producto de categoría 'Afeitado clásico'");
        }
    }

    @Override
    public SlotsDisponiblesDto obtenerSlotsDisponibles(SlotsDisponiblesInputDto dto) {
        validarFecha(dto.fecha());

        int diaSemana = dto.fecha().getDayOfWeek().getValue();

        List<PeluqueriaHorarioEntity> horarios = peluqueriaHorarioRepository.findByPeluqueriaAndDia(dto.peluqueriaId(),
                diaSemana);

        if (horarios.isEmpty()) {
            throw new BusinessException("No hay horarios disponibles para la peluquería y día seleccionados");
        }

        List<ReservaEntity> reservas = reservaRepository.findByPeluqueriaAndFecha(dto.peluqueriaId(), dto.fecha())
                .stream()
                .filter(r -> r.estado().equals(EstadoReserva.Pendiente))
                .toList();

        List<String> horasDisponibles = generarSlots(horarios, reservas, dto.duracionTotal());

        return new SlotsDisponiblesDto(dto.peluqueriaId(), dto.fecha().toString(), horasDisponibles);
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha.isBefore(LocalDate.now())) {
            throw new BusinessException("No se puede reservar en fechas pasadas");
        }

        if (fecha.isEqual(LocalDate.now())) {
            throw new BusinessException("No se puede reservar para el mismo día");
        }
    }

    private List<String> generarSlots(List<PeluqueriaHorarioEntity> horarios, List<ReservaEntity> reservas,
            int duracion) {
        List<String> disponibles = new ArrayList<>();

        for (PeluqueriaHorarioEntity horario : horarios) {
            LocalTime inicioTurno = horario.horaApertura();
            LocalTime finTurno = horario.horaCierre();

            for (LocalTime slot = inicioTurno; !slot.plusMinutes(duracion).isAfter(finTurno); slot = slot
                    .plusMinutes(5)) {
                LocalTime slotFin = slot.plusMinutes(duracion);

                if (solapa(slot, slotFin, reservas)) {
                    continue;
                }

                if (!respetaMargen(slot, slotFin, reservas)) {
                    continue;
                }

                disponibles.add(slot.toString());
            }
        }

        return disponibles;
    }

    private boolean solapa(LocalTime inicio, LocalTime fin, List<ReservaEntity> reservas) {
        for (ReservaEntity reserva : reservas) {
            LocalTime reservaInicio = reserva.horaInicio();
            LocalTime reservaFin = reserva.horaFinal();

            boolean overlap = inicio.isBefore(reservaFin) && fin.isAfter(reservaInicio);
            if (overlap) {
                return true;
            }
        }
        return false;
    }

    private boolean respetaMargen(LocalTime inicio, LocalTime fin, List<ReservaEntity> reservas) {
        for (ReservaEntity reserva : reservas) {
            LocalTime reservaInicio = reserva.horaInicio();
            LocalTime reservaFin = reserva.horaFinal();

            LocalTime margenInicio = reservaInicio.minusMinutes(5);
            LocalTime margenFin = reservaFin.plusMinutes(5);

            boolean overlap = inicio.isBefore(margenFin) && fin.isAfter(margenInicio);
            if (overlap) {
                return false;
            }
        }
        return true;
    }
}
