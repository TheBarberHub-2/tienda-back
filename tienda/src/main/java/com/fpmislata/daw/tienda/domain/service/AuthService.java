package com.fpmislata.daw.tienda.domain.service;

import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.enums.Rol;

public interface AuthService {
    String logIn(String email, String contrasenya);

    void logOut(String token);

    UsuarioDto getByToken(String token);

    Rol getRolByToken(String token);
}
