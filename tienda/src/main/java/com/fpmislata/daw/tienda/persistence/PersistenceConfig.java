package com.fpmislata.daw.tienda.persistence;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fpmislata.daw.tienda.persistence.dao.jpa.CategoriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.PeluqueriaJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.ProductoJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SesionJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.UsuarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.impl.CategoriaJpaDaoImpl;
import com.fpmislata.daw.tienda.persistence.dao.jpa.impl.PeluqueriaJpaDaoImpl;
import com.fpmislata.daw.tienda.persistence.dao.jpa.impl.ProductoJpaDaoImpl;
import com.fpmislata.daw.tienda.persistence.dao.jpa.impl.SesionJpaDaoImpl;
import com.fpmislata.daw.tienda.persistence.dao.jpa.impl.UsuarioJpaDaoImpl;

@Configuration
@EntityScan(basePackages = "com.fpmislata.daw.tienda.persistence.dao.jpa.entity")
public class PersistenceConfig {

    @Bean
    public UsuarioJpaDao usuarioJpaDao() {
        return new UsuarioJpaDaoImpl();
    }

    @Bean
    public PeluqueriaJpaDao peluqueriaJpaDao() {
        return new PeluqueriaJpaDaoImpl();
    }

    @Bean
    public CategoriaJpaDao categoriaJpaDao() {
        return new CategoriaJpaDaoImpl();
    }

    @Bean
    public ProductoJpaDao productoJpaDao() {
        return new ProductoJpaDaoImpl();
    }

    @Bean
    public SesionJpaDao sesionJpaDao() {
        return new SesionJpaDaoImpl();
    }
}