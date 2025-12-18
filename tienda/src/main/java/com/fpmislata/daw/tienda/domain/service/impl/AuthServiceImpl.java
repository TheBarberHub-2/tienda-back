package com.fpmislata.daw.tienda.domain.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.SesionService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.Rol;
import com.fpmislata.daw.tienda.exception.BusinessException;

public class AuthServiceImpl implements AuthService {
    private final UsuarioService usuarioService;
    private final SesionService sesionService;

    public AuthServiceImpl(UsuarioService usuarioService, SesionService sesionService) {
        this.usuarioService = usuarioService;
        this.sesionService = sesionService;
    }

    @Override
    public String logIn(String email, String contrasenya) {

        UsuarioDto usuario = usuarioService.getByEmail(email);
        if (!usuario.contrasenya().equals(contrasenya)) {
            throw new BusinessException("Contraseña Incorrecta");
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime date = LocalDateTime.now();

        SesionDto sesion = new SesionDto(null, usuario, token, date);

        sesionService.create(sesion);

        return token;

    }

    @Override
    public void logOut(String token) {
        sesionService.deleteByToken(token);
    }

    @Override
    public UsuarioDto getByToken(String token) {
        SesionDto sesion = sesionService.getByToken(token);
        UsuarioDto usuario = sesion.usuario();
        return usuario;
    }

    @Override
    public Rol getRolByToken(String token) {
        UsuarioDto usuario = this.getByToken(token);
        Rol rol = usuario.rol();
        return rol;
    }
}
