package com.fpmislata.daw.tienda.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.enums.DiaSemana;
import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class Reserva {

    private Long id;
    private Usuario cliente;
    private Peluqueria peluqueria;
    private DiaSemana diaSemana;
    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private LocalTime horaFinal;
    private Double precioTotal;
    private EstadoReserva estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReservaProducto> productos;

    public Reserva(Long id,
            Usuario cliente,
            Peluqueria peluqueria,
            DiaSemana diaSemana,
            LocalDate fechaReserva,
            LocalTime horaInicio,
            LocalTime horaFinal,
            Double precioTotal,
            EstadoReserva estado,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<ReservaProducto> productos) {

        this.id = id;
        this.cliente = cliente;
        this.peluqueria = peluqueria;
        this.diaSemana = diaSemana;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productos = (productos == null) ? new ArrayList<>() : new ArrayList<>(productos);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Peluqueria getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(Peluqueria peluqueria) {
        this.peluqueria = peluqueria;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ReservaProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<ReservaProducto> productos) {
        this.productos = productos;
    }

    public void addProducto(ReservaProducto producto) {
        if (this.productos.contains(producto)) {
            throw new BusinessException("El producto ya está asociado a esta reserva");
        }
        this.productos.add(producto);
    }
}