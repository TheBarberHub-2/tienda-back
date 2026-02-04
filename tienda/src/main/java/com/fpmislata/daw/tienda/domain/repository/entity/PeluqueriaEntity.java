package com.fpmislata.daw.tienda.domain.repository.entity;

import java.util.List;

public record PeluqueriaEntity(
        Long id,
        UsuarioEntity usuario,
        String municipio,
        String direccion,
        String telefono,
        List<ProductoEntity> productos,
        List<PeluqueriaHorarioEntity> horarios) {
    public PeluqueriaEntity(
            Long id,
            UsuarioEntity usuario,
            String municipio,
            String direccion,
            String telefono,
            List<ProductoEntity> productos,
            List<PeluqueriaHorarioEntity> horarios) {
        this.id = id;
        this.usuario = usuario;
        this.municipio = municipio;
        this.direccion = direccion;
        this.telefono = telefono;
        this.productos = productos == null ? List.of() : List.copyOf(productos);
        this.horarios = horarios == null ? List.of() : List.copyOf(horarios);
    }
}
