package com.fpmislata.daw.tienda.domain.validation.validator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.domain.validation.RequireSamePeluqueria;
import com.fpmislata.daw.tienda.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SamePeluqueriaAspect {

    @Autowired
    private AuthService authService;

    @Autowired
    private PeluqueriaService peluqueriaService;

    @Before("@annotation(requireSamePeluqueria)")
    public void checkSamePeluqueria(JoinPoint joinPoint, RequireSamePeluqueria requireSamePeluqueria) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return;
        }

        String token = request.getHeader("token");

        UsuarioDto usuario = authService.getByToken(token);

        PeluqueriaDto peluqueria = peluqueriaService.findByUsuario(usuario.id());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(requireSamePeluqueria.paramName())) {
                Long peluqueriaId = Long.valueOf(args[i].toString());

                if (!peluqueria.id().equals(peluqueriaId)) {
                    throw new BusinessException("Acceso denegado");
                }
            }
        }
    }
}
