package com.fpmislata.daw.tienda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

import com.fpmislata.daw.tienda.domain.repository.CategoriaRepository;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaHorarioRepository;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.ReservaRepository;
import com.fpmislata.daw.tienda.domain.repository.SesionRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudPeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.UsuarioRepository;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.CarritoService;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.EmailService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaHorarioService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.ReservaService;
import com.fpmislata.daw.tienda.domain.service.SesionService;
import com.fpmislata.daw.tienda.domain.service.SolicitudPeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.SolicitudProductoService;
import com.fpmislata.daw.tienda.domain.service.SolicitudService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.impl.AuthServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.CarritoServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.CategoriaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.EmailServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.PeluqueriaHorarioServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.PeluqueriaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.ProductoServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.ReservaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.SesionServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.SolicitudPeluqueriaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.SolicitudProductoServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.SolicitudServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.UsuarioServiceImpl;
import com.fpmislata.daw.tienda.persistence.PersistenceConfig;
import com.fpmislata.daw.tienda.persistence.dao.jpa.CategoriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaHorarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ReservaProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SesionJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudPeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.UsuarioJpaDao;
import com.fpmislata.daw.tienda.persistence.repository.CategoriaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.PeluqueriaHorarioRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.PeluqueriaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.ProductoRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.ReservaProductoRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.ReservaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.SesionRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.SolicitudPeluqueriaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.SolicitudProductoRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.SolicitudRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.UsuarioRepositoryImpl;

@Configuration
@Import(PersistenceConfig.class)
public class SpringConfig {

    @Bean
    public UsuarioRepository usuarioRepository(UsuarioJpaDao usuarioJpaDao) {
        return new UsuarioRepositoryImpl(usuarioJpaDao);
    }

    @Bean
    public UsuarioService usuarioService(UsuarioRepository usuarioRepository) {
        return new UsuarioServiceImpl(usuarioRepository);
    }

    @Bean
    public PeluqueriaRepository peluqueriaRepository(PeluqueriaJpaDao peluqueriaJpaDao) {
        return new PeluqueriaRepositoryImpl(peluqueriaJpaDao);
    }

    @Bean
    public PeluqueriaService peluqueriaService(PeluqueriaRepository peluqueriaRepository,
            UsuarioService usuarioService) {
        return new PeluqueriaServiceImpl(peluqueriaRepository, usuarioService);
    }

    @Bean
    public CategoriaRepository categoriaRepository(CategoriaJpaDao categoriaJpaDao) {
        return new CategoriaRepositoryImpl(categoriaJpaDao);
    }

    @Bean
    public CategoriaService categoriaService(CategoriaRepository categoriaRepository) {
        return new CategoriaServiceImpl(categoriaRepository);
    }

    @Bean
    public ProductoRepository productoRepository(ProductoJpaDao productoJpaDao) {
        return new ProductoRepositoryImpl(productoJpaDao);
    }

    @Bean
    public ProductoService productoService(ProductoRepository productoRepository) {
        return new ProductoServiceImpl(productoRepository);
    }

    @Bean
    public SesionRepository sesionRepository(SesionJpaDao sesionJpaDao) {
        return new SesionRepositoryImpl(sesionJpaDao);
    }

    @Bean
    public SesionService sesionService(SesionRepository sesionRepository) {
        return new SesionServiceImpl(sesionRepository);
    }

    @Bean
    public AuthService authService(UsuarioService usuarioService, SesionService sesionService) {
        return new AuthServiceImpl(usuarioService, sesionService);
    }

    @Bean
    public PeluqueriaHorarioRepository peluqueriaHorarioRepository(PeluqueriaHorarioJpaDao peluqueriaHorarioJpaDao) {
        return new PeluqueriaHorarioRepositoryImpl(peluqueriaHorarioJpaDao);
    }

    @Bean
    public PeluqueriaHorarioService peluqueriaHorarioService(PeluqueriaHorarioRepository peluqueriaHorarioRepository) {
        return new PeluqueriaHorarioServiceImpl(peluqueriaHorarioRepository);
    }

    @Bean
    public SolicitudRepository solicitudRepository(SolicitudJpaDao solicitudJpaDao) {
        return new SolicitudRepositoryImpl(solicitudJpaDao);
    }

    @Bean
    public SolicitudPeluqueriaRepository solicitudPeluqueriaRepository(
            SolicitudPeluqueriaJpaDao solicitudPeluqueriaJpaDao) {
        return new SolicitudPeluqueriaRepositoryImpl(solicitudPeluqueriaJpaDao);
    }

    @Bean
    public SolicitudProductoRepository solicitudProductoRepository(SolicitudProductoJpaDao solicitudProductoJpaDao) {
        return new SolicitudProductoRepositoryImpl(solicitudProductoJpaDao);
    }

    @Bean
    public SolicitudService solicitudService(SolicitudRepository solicitudRepository,
            SolicitudProductoRepository solicitudProductoRepository,
            SolicitudPeluqueriaRepository solicitudPeluqueriaRepository,
            PeluqueriaService peluqueriaService,
            ProductoService productoService, UsuarioService usuarioService, AuthService authService) {
        return new SolicitudServiceImpl(solicitudRepository, solicitudProductoRepository,
                solicitudPeluqueriaRepository, peluqueriaService, productoService, usuarioService, authService);
    }

    @Bean
    public SolicitudPeluqueriaService solicitudPeluqueriaService(
            SolicitudRepository solicitudRepository,
            SolicitudPeluqueriaRepository solicitudPeluqueriaRepository, AuthService authService) {
        return new SolicitudPeluqueriaServiceImpl(solicitudRepository, solicitudPeluqueriaRepository,
                authService);
    }

    @Bean
    public SolicitudProductoService solicitudProductoService(
            SolicitudProductoRepository solicitudProductoRepository,
            SolicitudRepository solicitudRepository,
            AuthService authService,
            CategoriaService categoriaService) {
        return new SolicitudProductoServiceImpl(solicitudProductoRepository, solicitudRepository,
                authService, categoriaService);
    }

    @Bean
    public ReservaRepository reservaRepository(ReservaJpaDao reservaJpaDao) {
        return new ReservaRepositoryImpl(reservaJpaDao);
    }

    @Bean
    public ReservaService reservaService(ReservaRepository reservaRepository,
            ReservaProductoRepository reservaProductoRepository, ProductoRepository productoRepository,
            PeluqueriaHorarioRepository peluqueriaHorarioRepository, EmailService emailService) {
        return new ReservaServiceImpl(reservaRepository, reservaProductoRepository, productoRepository,
                peluqueriaHorarioRepository, emailService);
    }

    @Bean
    public ReservaProductoRepository reservaProductoRepository(ReservaProductoJpaDao reservaProductoJpaDao) {
        return new ReservaProductoRepositoryImpl(reservaProductoJpaDao);
    }

    @Bean
    public CarritoService carritoService(ProductoRepository productoRepository, PeluqueriaService peluqueriaService,
            PeluqueriaHorarioRepository peluqueriaHorarioRepository, ReservaRepository reservaRepository) {
        return new CarritoServiceImpl(productoRepository, peluqueriaService, peluqueriaHorarioRepository,
                reservaRepository);
    }

    @Bean
    public EmailService emailService(JavaMailSender mailSender) {
        return new EmailServiceImpl(mailSender);
    }
}
