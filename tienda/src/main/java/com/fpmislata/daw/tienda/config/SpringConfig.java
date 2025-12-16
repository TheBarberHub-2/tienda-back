package com.fpmislata.daw.tienda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fpmislata.daw.tienda.domain.repository.CategoriaRepository;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.ProductoRepository;
import com.fpmislata.daw.tienda.domain.repository.SesionRepository;
import com.fpmislata.daw.tienda.domain.repository.UsuarioRepository;
import com.fpmislata.daw.tienda.domain.service.AuthService;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.ProductoService;
import com.fpmislata.daw.tienda.domain.service.SesionService;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.impl.AuthServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.CategoriaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.PeluqueriaServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.ProductoServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.SesionServiceImpl;
import com.fpmislata.daw.tienda.domain.service.impl.UsuarioServiceImpl;
import com.fpmislata.daw.tienda.persistence.PersistenceConfig;
import com.fpmislata.daw.tienda.persistence.dao.jpa.CategoriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SesionJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.UsuarioJpaDao;
import com.fpmislata.daw.tienda.persistence.repository.CategoriaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.PeluqueriaRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.ProductoRepositoryImpl;
import com.fpmislata.daw.tienda.persistence.repository.SesionRepositoryImpl;
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
    public PeluqueriaService peluqueriaService(PeluqueriaRepository peluqueriaRepository) {
        return new PeluqueriaServiceImpl(peluqueriaRepository);
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
}
