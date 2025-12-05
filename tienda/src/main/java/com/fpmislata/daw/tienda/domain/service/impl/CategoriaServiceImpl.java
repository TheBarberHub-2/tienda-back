package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.exception.ResourceNotFoundException;
import com.fpmislata.daw.tienda.domain.mapper.CategoriaMapper;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.CategoriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.CategoriaEntity;
import com.fpmislata.daw.tienda.domain.service.CategoriaService;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;

import jakarta.transaction.Transactional;

public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Page<CategoriaDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<CategoriaEntity> categoriaPage = categoriaRepository.findAll(page, size);

        List<CategoriaDto> categoriaDtos = categoriaPage.data().stream()
                .map(CategoriaMapper.getInstance()::fromEntityToModel)
                .map(CategoriaMapper.getInstance()::fromModelToDto)
                .toList();

        return new Page<>(categoriaDtos, categoriaPage.pageNumber(), categoriaPage.pageSize(),
                categoriaPage.totalElements());
    }

    @Override
    public CategoriaDto getById(long id) {
        return categoriaRepository.findById(id).map(CategoriaMapper.getInstance()::fromEntityToModel)
                .map(CategoriaMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria with id " + id + " not found"));
    }

    @Override
    public Optional<CategoriaDto> findById(long id) {
        return categoriaRepository.findById(id).map(CategoriaMapper.getInstance()::fromEntityToModel)
                .map(CategoriaMapper.getInstance()::fromModelToDto);
    }

    @Override
    @Transactional
    public CategoriaDto create(CategoriaDto categoriaDto) {
        CategoriaEntity categoriaEntity = CategoriaMapper.getInstance().fromModelToEntity(
                CategoriaMapper.getInstance().fromDtoToModel(categoriaDto));

        return CategoriaMapper.getInstance().fromModelToDto(
                CategoriaMapper.getInstance().fromEntityToModel(categoriaRepository.save(categoriaEntity)));
    }

    @Override
    @Transactional
    public CategoriaDto update(CategoriaDto categoriaDto) {
        categoriaRepository.findById(categoriaDto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria with id " + categoriaDto.id() + " not found"));

        CategoriaEntity categoriaEntity = CategoriaMapper.getInstance().fromModelToEntity(
                CategoriaMapper.getInstance().fromDtoToModel(categoriaDto));

        return CategoriaMapper.getInstance().fromModelToDto(
                CategoriaMapper.getInstance().fromEntityToModel(categoriaRepository.save(categoriaEntity)));
    }

    @Override
    public void delete(long id) {
        Optional<CategoriaDto> categoriaDto = findById(id);

        if (categoriaDto.isEmpty()) {
            throw new ResourceNotFoundException("Categoria with id " + id + " not found");
        }

        categoriaRepository.deleteById(id);
    }
}
