package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Peluqueria;
import com.fpmislata.daw.tienda.domain.model.PeluqueriaHorario;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaHorarioEntity;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaHorarioDto;
import com.fpmislata.daw.tienda.enums.DiaSemana;

public class PeluqueriaHorarioMapper {

    private static PeluqueriaHorarioMapper INSTANCE;

    private PeluqueriaHorarioMapper() {
    }

    public static PeluqueriaHorarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaHorarioMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaHorarioEntity fromModelToEntity(PeluqueriaHorario peluqueriaHorario) {
        if (peluqueriaHorario == null) {
            return null;
        }

        Peluqueria peluqueria = peluqueriaHorario.getPeluqueria();
        PeluqueriaEntity peluqueriaEntity = null;
        if (peluqueria != null) {
            peluqueriaEntity = new PeluqueriaEntity(
                    peluqueria.getId(),
                    UsuarioMapper.getInstance().fromModelToEntity(peluqueria.getUsuario()),
                    peluqueria.getMunicipio(),
                    peluqueria.getDireccion(),
                    peluqueria.getTelefono(),
                    null,
                    null);
        }

        return new PeluqueriaHorarioEntity(
                peluqueriaHorario.getId(),
                peluqueriaEntity,
                peluqueriaHorario.getDiaSemana().getValue(),
                peluqueriaHorario.getHoraApertura(),
                peluqueriaHorario.getHoraCierre());
    }

    public PeluqueriaHorario fromEntityToModel(PeluqueriaHorarioEntity peluqueriaHorarioEntity) {
        if (peluqueriaHorarioEntity == null) {
            return null;
        }

        PeluqueriaEntity peluqueriaEntity = peluqueriaHorarioEntity.peluqueria();
        Peluqueria peluqueria = null;
        if (peluqueriaEntity != null) {
            peluqueria = new Peluqueria(
                    peluqueriaEntity.id(),
                    UsuarioMapper.getInstance().fromEntityToModel(peluqueriaEntity.usuario()),
                    peluqueriaEntity.municipio(),
                    peluqueriaEntity.direccion(),
                    peluqueriaEntity.telefono(),
                    null,
                    null);
        }

        return new PeluqueriaHorario(
                peluqueriaHorarioEntity.id(),
                peluqueria,
                DiaSemana.fromValue(peluqueriaHorarioEntity.diaSemana()),
                peluqueriaHorarioEntity.horaApertura(),
                peluqueriaHorarioEntity.horaCierre());
    }

    public PeluqueriaHorario fromDtoToModel(PeluqueriaHorarioDto peluqueriaHorarioDto) {
        if (peluqueriaHorarioDto == null) {
            return null;
        }

        PeluqueriaDto peluqueriaDto = peluqueriaHorarioDto.peluqueria();
        Peluqueria peluqueria = null;
        if (peluqueriaDto != null) {
            peluqueria = new Peluqueria(
                    peluqueriaDto.id(),
                    UsuarioMapper.getInstance().fromDtoToModel(peluqueriaDto.usuario()),
                    peluqueriaDto.municipio(),
                    peluqueriaDto.direccion(),
                    peluqueriaDto.telefono(),
                    null,
                    null);
        }

        return new PeluqueriaHorario(
                peluqueriaHorarioDto.id(),
                peluqueria,
                peluqueriaHorarioDto.diaSemana(),
                peluqueriaHorarioDto.horaApertura(),
                peluqueriaHorarioDto.horaCierre());
    }

    public PeluqueriaHorarioDto fromModelToDto(PeluqueriaHorario peluqueriaHorario) {
        if (peluqueriaHorario == null) {
            return null;
        }

        Peluqueria peluqueria = peluqueriaHorario.getPeluqueria();
        PeluqueriaDto peluqueriaDto = null;

        if (peluqueria != null) {
            peluqueriaDto = new PeluqueriaDto(
                    peluqueria.getId(),
                    UsuarioMapper.getInstance().fromModelToDto(peluqueria.getUsuario()),
                    peluqueria.getMunicipio(),
                    peluqueria.getDireccion(),
                    peluqueria.getTelefono(),
                    null,
                    null);
        }

        return new PeluqueriaHorarioDto(
                peluqueriaHorario.getId(),
                peluqueriaDto,
                peluqueriaHorario.getDiaSemana(),
                peluqueriaHorario.getHoraApertura(),
                peluqueriaHorario.getHoraCierre());
    }
}
