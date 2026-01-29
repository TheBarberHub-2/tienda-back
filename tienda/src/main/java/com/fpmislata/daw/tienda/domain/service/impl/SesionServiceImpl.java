package com.fpmislata.daw.tienda.domain.service.impl;

import com.fpmislata.daw.tienda.domain.mapper.SesionMapper;
import com.fpmislata.daw.tienda.domain.repository.SesionRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SesionEntity;
import com.fpmislata.daw.tienda.domain.service.SesionService;
import com.fpmislata.daw.tienda.domain.service.dto.SesionDto;
import com.fpmislata.daw.tienda.exception.ResourceNotFoundException;

public class SesionServiceImpl implements SesionService {
    private final SesionRepository sesionRepository;

    public SesionServiceImpl(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    @Override
    public SesionDto getByToken(String token) {
        return sesionRepository.findByToken(token).map(SesionMapper.getInstance()::fromEntityToModel)
                .map(SesionMapper.getInstance()::fromModelToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no iniciada"));
    }

    @Override
    public SesionDto create(SesionDto sesionDto) {

        SesionEntity sesionEntity = SesionMapper.getInstance()
                .fromModelToEntity(SesionMapper.getInstance().fromDtoToModel(sesionDto));

        return SesionMapper.getInstance()
                .fromModelToDto(SesionMapper.getInstance().fromEntityToModel(sesionRepository.save(sesionEntity)));

    }

    @Override
    public void deleteByToken(String token) {
        sesionRepository.deleteByToken(token);
    }
}
