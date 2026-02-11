package com.fpmislata.daw.tienda.persistence.dao.jpa.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.enums.EstadoReserva;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class ReservaJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private UsuarioJpaEntity cliente;

    @ManyToOne
    @JoinColumn(name = "peluqueria_id", nullable = false)
    private PeluqueriaJpaEntity peluqueria;

    @Column(name = "dia_semana", nullable = false)
    private byte diaSemana;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_final")
    private LocalTime horaFinal;

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoReserva estado;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "iban", nullable = false)
    private String iban;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<ReservaProductoJpaEntity> productos = new ArrayList<>();

    public ReservaJpaEntity() {
    }

    public ReservaJpaEntity(Long id, UsuarioJpaEntity cliente, PeluqueriaJpaEntity peluqueria, byte diaSemana,
            LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFinal, Double precioTotal,
            EstadoReserva estado, LocalDateTime createdAt, LocalDateTime updatedAt, String iban,
            List<ReservaProductoJpaEntity> productos) {
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
        this.iban = iban;
        this.productos = productos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioJpaEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioJpaEntity cliente) {
        this.cliente = cliente;
    }

    public PeluqueriaJpaEntity getPeluqueria() {
        return peluqueria;
    }

    public void setPeluqueria(PeluqueriaJpaEntity peluqueria) {
        this.peluqueria = peluqueria;
    }

    public byte getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(byte diaSemana) {
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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public List<ReservaProductoJpaEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ReservaProductoJpaEntity> productos) {
        this.productos = productos;
    }
}