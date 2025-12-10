package com.fpmislata.daw.tienda.domain.service.impl;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.mapper.PeluqueriaMapper;
import com.fpmislata.daw.tienda.domain.model.Page;
import com.fpmislata.daw.tienda.domain.repository.PeluqueriaRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.PeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.service.PeluqueriaService;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.exception.BusinessException;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

public class PeluqueriaServiceImpl implements PeluqueriaService {

    private final PeluqueriaRepository peluqueriaRepository;

    public PeluqueriaServiceImpl(PeluqueriaRepository peluqueriaRepository) {
        this.peluqueriaRepository = peluqueriaRepository;
    }

    @Override
    public Page<PeluqueriaDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        Page<PeluqueriaEntity> peluqueriaPage = peluqueriaRepository.findAll(page, size);

        List<PeluqueriaDto> peluqueriaDtos = peluqueriaPage.data().stream()
                .map(PeluqueriaMapper.getInstance()::fromEntityToModel)
                .map(PeluqueriaMapper.getInstance()::fromModelToDto)
                .toList();

        return new Page<>(peluqueriaDtos, peluqueriaPage.pageNumber(), peluqueriaPage.pageSize(),
                peluqueriaPage.totalElements());
    }

    @Override
    public PeluqueriaDto getById(long id) {
        return peluqueriaRepository.findById(id).map(PeluqueriaMapper.getInstance()::fromEntityToModel)
                .map(PeluqueriaMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Peluqueria with id " + id + " not found"));
    }

    @Override
    public Optional<PeluqueriaDto> findById(long id) {
        return peluqueriaRepository.findById(id).map(PeluqueriaMapper.getInstance()::fromEntityToModel)
                .map(PeluqueriaMapper.getInstance()::fromModelToDto);
    }

    @Override
    @Transactional
    public PeluqueriaDto create(PeluqueriaDto peluqueriaDto) {
        if (peluqueriaRepository.findByUsuario(peluqueriaDto.usuario().id()).isPresent()) {
            throw new BusinessException("Ya existe una peluquería con este usuario.");
        }

        PeluqueriaEntity peluqueriaEntity = PeluqueriaMapper.getInstance().fromModelToEntity(
                PeluqueriaMapper.getInstance().fromDtoToModel(peluqueriaDto));

        return PeluqueriaMapper.getInstance().fromModelToDto(
                PeluqueriaMapper.getInstance().fromEntityToModel(peluqueriaRepository.save(peluqueriaEntity)));
    }

    @Override
    @Transactional
    public PeluqueriaDto update(PeluqueriaDto peluqueriaDto) {
        peluqueriaRepository.findById(peluqueriaDto.id())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Peluqueria with id " + peluqueriaDto.id() + " not found"));

        PeluqueriaEntity peluqueriaEntity = PeluqueriaMapper.getInstance().fromModelToEntity(
                PeluqueriaMapper.getInstance().fromDtoToModel(peluqueriaDto));

        return PeluqueriaMapper.getInstance().fromModelToDto(
                PeluqueriaMapper.getInstance().fromEntityToModel(peluqueriaRepository.save(peluqueriaEntity)));
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<PeluqueriaDto> peluqueriaDto = findById(id);

        if (peluqueriaDto.isEmpty()) {
            throw new ResourceNotFoundException("Peluqueria with id " + id + " not found");
        }

        peluqueriaRepository.deleteById(id);
    }
}
