package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Usuario;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;

public class UsuarioMapper {

    private static UsuarioMapper INSTANCE;

    private UsuarioMapper() {
    }

    public static UsuarioMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsuarioMapper();
        }
        return INSTANCE;
    }

    public UsuarioEntity fromModelToEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol());
    }

    public Usuario fromEntityToModel(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }
        return new Usuario(
                usuarioEntity.id(),
                usuarioEntity.email(),
                usuarioEntity.nombre(),
                usuarioEntity.rol());
    }

    public Usuario fromDtoToModel(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        return new Usuario(
                usuarioDto.id(),
                usuarioDto.email(),
                usuarioDto.nombre(),
                usuarioDto.rol());
    }

    public UsuarioDto fromModelToDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDto(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol());
    }
}
