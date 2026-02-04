package com.fpmislata.daw.tienda.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.model.Peluqueria;
import com.fpmislata.daw.tienda.domain.model.PeluqueriaHorario;
import com.fpmislata.daw.tienda.domain.model.Producto;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.ProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;
import com.fpmislata.daw.tienda.domain.service.dto.ProductoDto;

public class PeluqueriaMapper {

    private static PeluqueriaMapper INSTANCE;

    private PeluqueriaMapper() {
    }

    public static PeluqueriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaEntity fromModelToEntity(Peluqueria peluqueria) {
        if (peluqueria == null) {
            return null;
        }
        List<ProductoEntity> productos = new ArrayList<>();
        if (peluqueria.getProductos() != null && !peluqueria.getProductos().isEmpty()) {
            productos = peluqueria.getProductos().stream()
                    .map(ProductoMapper.getInstance()::fromModelToEntity)
                    .toList();
        }
        List<PeluqueriaHorarioEntity> horarios = new ArrayList<>();
        if (peluqueria.getHorarios() != null && !peluqueria.getHorarios().isEmpty()) {
            horarios = peluqueria.getHorarios().stream()
                    .map(PeluqueriaHorarioMapper.getInstance()::fromModelToEntity)
                    .toList();
        }
        return new PeluqueriaEntity(
                peluqueria.getId(),
                UsuarioMapper.getInstance().fromModelToEntity(peluqueria.getUsuario()),
                peluqueria.getMunicipio(),
                peluqueria.getDireccion(),
                peluqueria.getTelefono(),
                productos,
                horarios);
    }

    public Peluqueria fromEntityToModel(PeluqueriaEntity peluqueriaEntity) {
        if (peluqueriaEntity == null) {
            return null;
        }
        List<Producto> productos = new ArrayList<>();
        if (peluqueriaEntity.productos() != null && !peluqueriaEntity.productos().isEmpty()) {
            productos = peluqueriaEntity.productos().stream()
                    .map(ProductoMapper.getInstance()::fromEntityToModel)
                    .toList();
        }
        List<PeluqueriaHorario> horarios = new ArrayList<>();
        if (peluqueriaEntity.horarios() != null && !peluqueriaEntity.horarios().isEmpty()) {
            horarios = peluqueriaEntity.horarios().stream()
                    .map(PeluqueriaHorarioMapper.getInstance()::fromEntityToModel)
                    .toList();
        }
        return new Peluqueria(
                peluqueriaEntity.id(),
                UsuarioMapper.getInstance().fromEntityToModel(peluqueriaEntity.usuario()),
                peluqueriaEntity.municipio(),
                peluqueriaEntity.direccion(),
                peluqueriaEntity.telefono(),
                productos,
                horarios);
    }

    public Peluqueria fromDtoToModel(PeluqueriaDto peluqueriaDto) {
        if (peluqueriaDto == null) {
            return null;
        }
        List<Producto> productos = new ArrayList<>();
        if (peluqueriaDto.productos() != null && !peluqueriaDto.productos().isEmpty()) {
            productos = peluqueriaDto.productos().stream()
                    .map(ProductoMapper.getInstance()::fromDtoToModel)
                    .toList();
        }
        List<PeluqueriaHorario> horarios = new ArrayList<>();
        if (peluqueriaDto.horarios() != null && !peluqueriaDto.horarios().isEmpty()) {
            horarios = peluqueriaDto.horarios().stream()
                    .map(PeluqueriaHorarioMapper.getInstance()::fromDtoToModel)
                    .toList();
        }
        return new Peluqueria(
                peluqueriaDto.id(),
                UsuarioMapper.getInstance().fromDtoToModel(peluqueriaDto.usuario()),
                peluqueriaDto.municipio(),
                peluqueriaDto.direccion(),
                peluqueriaDto.telefono(),
                productos,
                horarios);
    }

    public PeluqueriaDto fromModelToDto(Peluqueria peluqueria) {
        if (peluqueria == null) {
            return null;
        }
        List<ProductoDto> productos = new ArrayList<>();
        if (peluqueria.getProductos() != null && !peluqueria.getProductos().isEmpty()) {
            productos = peluqueria.getProductos().stream()
                    .map(ProductoMapper.getInstance()::fromModelToDto)
                    .toList();
        }
        List<PeluqueriaHorarioDto> horarios = new ArrayList<>();
        if (peluqueria.getHorarios() != null && !peluqueria.getHorarios().isEmpty()) {
            horarios = peluqueria.getHorarios().stream()
                    .map(PeluqueriaHorarioMapper.getInstance()::fromModelToDto)
                    .toList();
        }
        return new PeluqueriaDto(
                peluqueria.getId(),
                UsuarioMapper.getInstance().fromModelToDto(peluqueria.getUsuario()),
                peluqueria.getMunicipio(),
                peluqueria.getDireccion(),
                peluqueria.getTelefono(),
                productos,
                horarios);
    }
}
