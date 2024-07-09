package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.AcademicRank;
import com.tutor.auth0r.repository.AcademicRankRepository;
import com.tutor.auth0r.service.AcademicRankService;
import com.tutor.auth0r.service.dto.AcademicRankDTO;
import com.tutor.auth0r.service.mapper.AcademicRankMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.AcademicRank}.
 */
@Service
@Transactional
public class AcademicRankServiceImpl implements AcademicRankService {

    private static final Logger log = LoggerFactory.getLogger(AcademicRankServiceImpl.class);

    private final AcademicRankRepository academicRankRepository;

    private final AcademicRankMapper academicRankMapper;

    public AcademicRankServiceImpl(AcademicRankRepository academicRankRepository, AcademicRankMapper academicRankMapper) {
        this.academicRankRepository = academicRankRepository;
        this.academicRankMapper = academicRankMapper;
    }

    @Override
    public AcademicRankDTO save(AcademicRankDTO academicRankDTO) {
        log.debug("Request to save AcademicRank : {}", academicRankDTO);
        AcademicRank academicRank = academicRankMapper.toEntity(academicRankDTO);
        academicRank = academicRankRepository.save(academicRank);
        return academicRankMapper.toDto(academicRank);
    }

    @Override
    public AcademicRankDTO update(AcademicRankDTO academicRankDTO) {
        log.debug("Request to update AcademicRank : {}", academicRankDTO);
        AcademicRank academicRank = academicRankMapper.toEntity(academicRankDTO);
        academicRank = academicRankRepository.save(academicRank);
        return academicRankMapper.toDto(academicRank);
    }

    @Override
    public Optional<AcademicRankDTO> partialUpdate(AcademicRankDTO academicRankDTO) {
        log.debug("Request to partially update AcademicRank : {}", academicRankDTO);

        return academicRankRepository
            .findById(academicRankDTO.getId())
            .map(existingAcademicRank -> {
                academicRankMapper.partialUpdate(existingAcademicRank, academicRankDTO);

                return existingAcademicRank;
            })
            .map(academicRankRepository::save)
            .map(academicRankMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicRankDTO> findAll() {
        log.debug("Request to get all AcademicRanks");
        return academicRankRepository.findAll().stream().map(academicRankMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicRankDTO> findOne(Long id) {
        log.debug("Request to get AcademicRank : {}", id);
        return academicRankRepository.findById(id).map(academicRankMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicRank : {}", id);
        academicRankRepository.deleteById(id);
    }
}
