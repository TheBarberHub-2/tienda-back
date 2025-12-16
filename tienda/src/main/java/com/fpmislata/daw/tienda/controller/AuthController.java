package com.fpmislata.daw.tienda.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fpmislata.daw.tienda.controller.webModel.request.LogInRequest;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.enums.Rol;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> logIn(@RequestBody LogInRequest logInRequest) {
        String email = logInRequest.email();
        String contrasenya = logInRequest.contrasenya();

        String token = authService.logIn(email, contrasenya);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @RequireRole(roles = { Rol.Admin, Rol.Peluqueria, Rol.Cliente })
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logOut() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String token = request.getHeader("token");

        authService.logOut(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
