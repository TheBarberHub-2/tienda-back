package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.UsuarioMapper;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.UsuarioRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.UsuarioEntity;
import com.fpmislata.daw.tienda.domain.service.UsuarioService;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<UsuarioDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<UsuarioEntity> usuarioPage = usuarioRepository.findAll(page, size);

        List<UsuarioDto> usuarioDtos = usuarioPage.data().stream()
                .map(UsuarioMapper.getInstance()::fromEntityToModel)
                .map(UsuarioMapper.getInstance()::fromModelToDto)
                .toList();

        return new Page<>(usuarioDtos, usuarioPage.pageNumber(), usuarioPage.pageSize(), usuarioPage.totalElements());
    }

    @Override
    public UsuarioDto getById(long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper.getInstance()::fromEntityToModel)
                .map(UsuarioMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario with id " + id + " not found"));
    }

    @Override
    public Optional<UsuarioDto> findById(long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper.getInstance()::fromEntityToModel)
                .map(UsuarioMapper.getInstance()::fromModelToDto);
    }

    @Override
    @Transactional
    public UsuarioDto create(UsuarioDto usuarioDto) {
        if (usuarioRepository.findByEmail(usuarioDto.email()).isPresent()) {
            throw new BusinessException("Ya existe un usuario con este correo.");
        }

        UsuarioEntity usuarioEntity = UsuarioMapper.getInstance().fromModelToEntity(
                UsuarioMapper.getInstance().fromDtoToModel(usuarioDto));

        return UsuarioMapper.getInstance().fromModelToDto(
                UsuarioMapper.getInstance().fromEntityToModel(usuarioRepository.save(usuarioEntity)));
    }

    @Override
    @Transactional
    public UsuarioDto update(UsuarioDto usuarioDto) {
        usuarioRepository.findById(usuarioDto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario with id " + usuarioDto.id() + " not found"));

        UsuarioEntity usuarioEntity = UsuarioMapper.getInstance().fromModelToEntity(
                UsuarioMapper.getInstance().fromDtoToModel(usuarioDto));

        return UsuarioMapper.getInstance().fromModelToDto(
                UsuarioMapper.getInstance().fromEntityToModel(usuarioRepository.save(usuarioEntity)));
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<UsuarioDto> usuarioDto = findById(id);

        if (usuarioDto.isEmpty()) {
            throw new ResourceNotFoundException("Usuario with id " + id + " not found");
        }

        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioDto getByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioMapper.getInstance()::fromEntityToModel)
                .map(UsuarioMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

}
