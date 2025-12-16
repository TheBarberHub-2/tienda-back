package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.UsuarioRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.UsuarioJpaDao;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.UsuarioJpaEntity;
import com.fpmislata.daw.tienda.persistence.repository.mapper.UsuarioMapper;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaDao usuarioJpaDao;

    public UsuarioRepositoryImpl(UsuarioJpaDao usuarioJpaDao) {
        this.usuarioJpaDao = usuarioJpaDao;
    }

    @Override
    public Page<UsuarioEntity> findAll(int page, int size) {
        List<UsuarioEntity> usuarios = usuarioJpaDao.findAll(page, size).stream()
                .map(UsuarioMapper.getInstance()::fromJpaToEntity)
                .toList();
        long totalElements = usuarioJpaDao.count();
        return new Page<>(usuarios, page, size, totalElements);
    }

    @Override
    public Optional<UsuarioEntity> findById(long id) {
        return usuarioJpaDao.findById(id)
                .map(UsuarioMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public UsuarioEntity save(UsuarioEntity usuarioEntity) {
        UsuarioJpaEntity jpaEntity = UsuarioMapper.getInstance().fromEntityToJpa(usuarioEntity);
        if (usuarioEntity.id() == null) {
            return UsuarioMapper.getInstance().fromJpaToEntity(usuarioJpaDao.insert(jpaEntity));
        }
        return UsuarioMapper.getInstance().fromJpaToEntity(usuarioJpaDao.update(jpaEntity));
    }

    @Override
    public void deleteById(long id) {
        usuarioJpaDao.delete(id);
    }

    @Override
    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioJpaDao.findByEmail(email)
                .map(UsuarioMapper.getInstance()::fromJpaToEntity);
    }

}
