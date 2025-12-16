package com.fpmislata.daw.tienda.domain.validation.validator;

import java.util.Arrays;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.validation.RequireRole;
import com.fpmislata.daw.tienda.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RoleAspect {

    @Autowired
    private AuthService authService;

    @Before("@annotation(requireRole)")
    public void checkRole(RequireRole requireRole) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String token = request.getHeader("token");
        UsuarioDto u = authService.getByToken(token);

        boolean permitido = Arrays.stream(requireRole.roles())
                .anyMatch(r -> r == u.rol());

        if (!permitido) {
            throw new BusinessException("Permiso denegado");
        }
    }
}